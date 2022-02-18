package ru.atm.model;

import ru.atm.exception.NoRequiredBanknotesException;
import ru.atm.exception.NotEnoughCashException;

import java.util.*;

/**
 * Банкомат
 * <p>
 * Тип хранения и выдачи наличных - словарь "номинал купюры - количество купюр"
 */
public class Atm implements HasCash<Map<Integer, Integer>> {

    // Словарь "номинал купюры - ячейка для их хранения"
    private final Map<Integer, HasCash<Integer>> cells;

    public Atm(Map<Integer, HasCash<Integer>> cells) {

        if (cells == null || cells.isEmpty()) {
            throw new IllegalArgumentException("Parameter \"cells\" cannot be null or empty!");
        }
        if (cells.containsKey(null)) {
            throw new IllegalArgumentException("Cell denomination cannot be null!");
        }
        if (cells.entrySet().stream().anyMatch(entry -> entry.getKey() < 1)) {
            throw new IllegalArgumentException("Cell denomination must be greater than zero!");
        }
        if (cells.containsValue(null)) {
            throw new IllegalArgumentException("Cell cannot be null!");
        }

        this.cells = new TreeMap<>(Collections.reverseOrder());
        this.cells.putAll(cells);
    }

    @Override
    public Map<Integer, Integer> getCash(int sum) {

        if (sum > getBalance()) {
            throw new NotEnoughCashException("Not enough cash in ATM!");
        }

        var result = new TreeMap<Integer, Integer>(Collections.reverseOrder());
        int currentSum = sum;
        for (Integer denomination : cells.keySet()) {
            var cell = cells.get(denomination);
            var banknotesQty = cell.getCash(currentSum / denomination);
            if (banknotesQty > 0) {
                result.put(denomination, banknotesQty);
                currentSum -= denomination * banknotesQty;
            }
        }

        if (currentSum > 0) {
            result.forEach((denomination, banknotesQty) -> cells.get(denomination).putCash(banknotesQty));
            throw new NoRequiredBanknotesException("Cannot get required sum by ATM's current banknotes!");
        }

        return result;
    }

    @Override
    public Map<Integer, Integer> putCash(Map<Integer, Integer> cash) {

        if (cash == null || cash.isEmpty()) {
            throw new IllegalArgumentException("Parameter \"cash\" cannot be null or empty!");
        }
        if (cash.containsKey(null)) {
            throw new IllegalArgumentException("Banknote denomination cannot be null!");
        }
        if (cash.containsValue(null)) {
            throw new IllegalArgumentException("Banknote amount cannot be null!");
        }
        if (cash.entrySet().stream().anyMatch(entry -> entry.getValue() < 0)) {
            throw new IllegalArgumentException("Banknote amount must be a positive integer!");
        }

        var result = new HashMap<Integer, Integer>();
        cash.forEach((denomination, qty) -> {
            var cellOpt = Optional.ofNullable(cells.get(denomination));
            if (cellOpt.isPresent()) {
                cellOpt.get().putCash(qty);
            } else {
                result.put(denomination, qty);
            }
        });

        return result;
    }

    @Override
    public int getBalance() {
        return cells.entrySet().stream()
                .mapToInt(entry -> entry.getKey() * entry.getValue().getBalance())
                .sum();
    }
}

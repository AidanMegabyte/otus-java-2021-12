package ru.atm.model;

import ru.atm.exception.AtmException;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Банкомат
 */
public class Atm implements HasCash {

    // Словарь "номинал купюры - ячейка для их хранения"
    private final Map<BanknoteDenomination, HasSameDenominationBanknotes> cells;

    public Atm(Map<BanknoteDenomination, HasSameDenominationBanknotes> cells) {

        if (cells == null || cells.isEmpty()) {
            throw new IllegalArgumentException("Parameter \"cells\" cannot be null or empty!");
        }
        if (cells.containsKey(null)) {
            throw new IllegalArgumentException("Cell denomination cannot be null!");
        }
        if (cells.containsValue(null)) {
            throw new IllegalArgumentException("Cell cannot be null!");
        }

        // Ячейки храним по убыванию номинала для удобства
        this.cells = new TreeMap<>(Comparator.comparingInt(BanknoteDenomination::getDenomination).reversed());
        this.cells.putAll(cells);
    }

    @Override
    public Map<Integer, Integer> getCash(int qty) {

        if (qty > getBalance()) {
            throw new AtmException("Not enough cash in ATM!");
        }

        var result = new HashMap<Integer, Integer>();
        int currentSum = qty;
        for (BanknoteDenomination banknoteDenomination : cells.keySet()) {
            var cell = cells.get(banknoteDenomination);
            var denomination = banknoteDenomination.getDenomination();
            var banknotesQty = cell.getBanknotes(currentSum / denomination);
            if (banknotesQty > 0) {
                result.put(denomination, banknotesQty);
                currentSum -= denomination * banknotesQty;
            }
        }

        if (currentSum > 0) {
            putCash(result);
            throw new AtmException("Cannot get required sum by ATM's current banknotes!");
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
        if (cash.entrySet().stream().anyMatch(entry -> entry.getValue() < 1)) {
            throw new IllegalArgumentException("Banknote amount must be greater than zero!");
        }

        var result = new HashMap<Integer, Integer>();
        cash.forEach((denomination, qty) -> {
            // Если номинал купюр не поддерживается, то не вносим их и возвращаем обратно
            var banknoteDenominationOptional =
                    BanknoteDenomination.getByDenomination(denomination);
            if (banknoteDenominationOptional != null) {
                cells.get(banknoteDenominationOptional).putBanknotes(qty);
            } else {
                result.put(denomination, qty);
            }
        });

        return result;
    }

    @Override
    public int getBalance() {
        return cells.entrySet().stream()
                .mapToInt(entry -> entry.getKey().getDenomination() * entry.getValue().getBanknotesQty())
                .sum();
    }
}

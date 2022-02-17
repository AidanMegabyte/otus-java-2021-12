package ru.atm.model;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

/**
 * Банкомат
 * <p>
 * Тип хранения и выдачи наличных - словарь "номинал купюры - количество купюр"
 */
public class Atm implements HasCash<Map<Integer, Integer>> {

    // Словарь "номинал купюры - ячейка для их хранения"
    private Map<Integer, HasCash<Integer>> cells = new TreeMap<>(Collections.reverseOrder());

    public void addCell(HasCash<Integer> cell, int denomination) {

    }

    @Override
    public Map<Integer, Integer> getCash(int sum) {
        return null;
    }

    @Override
    public void putCash(Map<Integer, Integer> cash) {

    }

    @Override
    public int getBalance() {
        return 0;
    }
}

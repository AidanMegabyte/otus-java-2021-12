package ru.atm.model;

import java.util.Map;

/**
 * Интерфейс для работы с наличными
 */
public interface HasCash {

    /**
     * Снятие наличных
     *
     * @param sum требуемая сумма
     * @return словарь купюр "номинал - количество купюр"
     */
    Map<Integer, Integer> getCash(int sum);

    /**
     * Внесение наличных
     *
     * @param cash вносимые купюры в виде словаря "номинал - количество купюр"
     * @return словарь с непринятыми купюрами, аналогичный по структуре вносимым купюрам
     */
    Map<Integer, Integer> putCash(Map<Integer, Integer> cash);

    /**
     * Баланс
     *
     * @return суммарное количество наличных
     */
    int getBalance();
}

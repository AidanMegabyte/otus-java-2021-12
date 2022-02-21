package ru.atm.model;

/**
 * Интерфейс для работы с купюрами одного номинала
 */
public interface HasSameDenominationBanknotes {

    /**
     * Получение банкнот из ячейки
     *
     * @param qty требуемое количество банкнот
     * @return требуемое количество банкнот или все, что есть, если в ячейке банкнот меньше, чем нужно
     */
    int getBanknotes(int qty);

    /**
     * Внесение банкнот в ячейку
     *
     * @param qty количество вносимых банкнот
     */
    void putBanknotes(int qty);

    /**
     * Количество банкнот в ячейке
     *
     * @return количество банкнот в ячейке
     */
    int getBanknotesQty();
}

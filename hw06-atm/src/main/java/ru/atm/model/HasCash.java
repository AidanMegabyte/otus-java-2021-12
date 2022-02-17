package ru.atm.model;

/**
 * Интерфейс для работы с наличными
 *
 * @param <CashType> тип хранения и выдачи наличных
 */
public interface HasCash<CashType> {

    CashType getCash(int sum);

    void putCash(CashType cash);

    int getBalance();
}

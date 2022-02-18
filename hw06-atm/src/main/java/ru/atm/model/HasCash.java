package ru.atm.model;

/**
 * Интерфейс для работы с наличными
 * <p>
 * При внесении наличных часть их может быть возвращена (причины зависят от конкретной реализации)
 *
 * @param <CashType> формат хранения и выдачи наличных (определяется конкретной реализацией)
 */
public interface HasCash<CashType> {

    CashType getCash(int qty);

    CashType putCash(CashType cash);

    int getBalance();
}

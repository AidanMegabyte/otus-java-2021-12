package ru.atm.model;

/**
 * Ячейка банкомата, хранящая купюры одного номинала
 * <p>
 * Тип хранения и выдачи наличных - количество купюр
 */
public class AtmCell implements HasCash<Integer> {

    @Override
    public Integer getCash(int sum) {
        return null;
    }

    @Override
    public void putCash(Integer cash) {

    }

    @Override
    public int getBalance() {
        return 0;
    }
}

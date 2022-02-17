package ru.atm.model;

/**
 * Ячейка банкомата, хранящая купюры одного номинала
 * <p>
 * Тип хранения и выдачи наличных - количество купюр
 */
public class AtmCell implements HasCash<Integer> {

    private int banknotesQty = 0;

    @Override
    public Integer getCash(int sum) {
        if (sum < 0) {
            throw new IllegalArgumentException("Parameter \"sum\" must be a positive integer!");
        }
        var result = Math.min(sum, banknotesQty);
        banknotesQty -= result;
        return result;
    }

    @Override
    public void putCash(Integer cash) {
        if (cash < 0) {
            throw new IllegalArgumentException("Parameter \"cash\" must be a positive integer!");
        }
        banknotesQty += cash;
    }

    @Override
    public int getBalance() {
        return banknotesQty;
    }
}

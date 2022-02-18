package ru.atm.model;

/**
 * Ячейка банкомата, хранящая купюры одного номинала
 * <p>
 * Формат хранения и выдачи наличных - количество купюр
 */
public class AtmCell implements HasCash<Integer> {

    private int banknotesQty = 0;

    @Override
    public Integer getCash(int qty) {

        if (qty < 0) {
            throw new IllegalArgumentException("Parameter \"sum\" must be a positive integer!");
        }

        var result = Math.min(qty, banknotesQty);
        banknotesQty -= result;

        return result;
    }

    @Override
    public Integer putCash(Integer cash) {

        if (cash == null) {
            throw new IllegalArgumentException("Parameter \"cash\" cannot be null!");
        }
        if (cash < 0) {
            throw new IllegalArgumentException("Parameter \"cash\" must be a positive integer!");
        }

        banknotesQty += cash;
        // Здесь возврата непринятых купюр не предполагается
        return 0;
    }

    @Override
    public int getBalance() {
        return banknotesQty;
    }
}

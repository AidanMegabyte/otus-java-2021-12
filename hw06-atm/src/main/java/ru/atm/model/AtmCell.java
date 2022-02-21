package ru.atm.model;

/**
 * Ячейка банкомата, хранящая купюры одного номинала
 */
public class AtmCell implements HasSameDenominationBanknotes {

    private int banknotesQty = 0;

    @Override
    public int getBanknotes(int qty) {

        if (qty < 0) {
            throw new IllegalArgumentException("Parameter \"qty\" must be a positive integer!");
        }

        var result = Math.min(qty, banknotesQty);
        banknotesQty -= result;

        return result;
    }

    @Override
    public void putBanknotes(int qty) {
        if (qty < 0) {
            throw new IllegalArgumentException("Parameter \"qty\" must be a positive integer!");
        }
        banknotesQty += qty;
    }

    @Override
    public int getBanknotesQty() {
        return banknotesQty;
    }
}

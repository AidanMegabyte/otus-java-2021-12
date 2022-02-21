package ru.atm.model;

/**
 * Номиналы банкнот
 */
public enum BanknoteDenomination {

    ONE_HUNDRED(100),
    TWO_HUNDREDS(200),
    FIVE_HUNDREDS(500),
    ONE_THOUSAND(1000),
    TWO_THOUSANDS(2000),
    FIVE_THOUSANDS(5000);

    private final int denomination;

    BanknoteDenomination(int denomination) {
        this.denomination = denomination;
    }

    public int getDenomination() {
        return denomination;
    }

    public static BanknoteDenomination getByDenomination(int denomination) {
        for (BanknoteDenomination banknoteDenomination : BanknoteDenomination.values()) {
            if (banknoteDenomination.getDenomination() == denomination) {
                return banknoteDenomination;
            }
        }
        return null;
    }
}

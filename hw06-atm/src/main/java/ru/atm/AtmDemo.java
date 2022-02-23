package ru.atm;

import ru.atm.model.Atm;
import ru.atm.model.AtmCell;
import ru.atm.model.BanknoteDenomination;
import ru.atm.model.HasSameDenominationBanknotes;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class AtmDemo {

    public static void main(String... args) {

        var atmCells = new HashMap<BanknoteDenomination, HasSameDenominationBanknotes>();
        atmCells.put(BanknoteDenomination.ONE_HUNDRED, new AtmCell());
        atmCells.put(BanknoteDenomination.TWO_HUNDREDS, new AtmCell());
        atmCells.put(BanknoteDenomination.FIVE_HUNDREDS, new AtmCell());
        atmCells.put(BanknoteDenomination.ONE_THOUSAND, new AtmCell());
        atmCells.put(BanknoteDenomination.TWO_THOUSANDS, new AtmCell());
        atmCells.put(BanknoteDenomination.FIVE_THOUSANDS, new AtmCell());

        var atm = new Atm(atmCells);

        var cashToPut = new HashMap<Integer, Integer>();
        cashToPut.put(100, 10);
        cashToPut.put(200, 20);
        cashToPut.put(500, 30);
        cashToPut.put(1000, 40);
        cashToPut.put(2000, 50);
        cashToPut.put(5000, 60);
        cashToPut.put(777, 70);

        var returnedCash = atm.putCash(cashToPut);
        System.out.printf("Current ATM balance is %d%n", atm.getBalance());
        printCash(returnedCash, "Cash returned: ");

        var cashToGet = atm.getCash(18800);
        printCash(cashToGet, "Cash received: ");
        System.out.printf("Current ATM balance is %d%n", atm.getBalance());
    }

    private static void printCash(Map<Integer, Integer> cash, String prefix) {
        System.out.printf(
                "%s%s%n",
                prefix,
                cash.entrySet().stream()
                        .map(entry -> String.format("%d x %d", entry.getKey(), entry.getValue()))
                        .collect(Collectors.joining(", "))
        );
    }
}

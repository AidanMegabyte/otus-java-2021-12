package ru.atm;

import ru.atm.model.Atm;
import ru.atm.model.AtmCell;

import java.util.HashMap;
import java.util.stream.Collectors;

public class AtmDemo {

    public static void main(String... args) {

        var atm = new Atm();

        atm.addCell(new AtmCell(), 100);
        atm.addCell(new AtmCell(), 200);
        atm.addCell(new AtmCell(), 500);
        atm.addCell(new AtmCell(), 1000);
        atm.addCell(new AtmCell(), 2000);
        atm.addCell(new AtmCell(), 5000);

        var cashToPut = new HashMap<Integer, Integer>();
        cashToPut.put(100, 10);
        cashToPut.put(200, 20);
        cashToPut.put(500, 30);
        cashToPut.put(1000, 40);
        cashToPut.put(2000, 50);
        cashToPut.put(5000, 60);

        atm.putCash(cashToPut);
        System.out.printf("Current ATM balance is %d%n", atm.getBalance());

        var cashToGet = atm.getCash(18800);
        System.out.printf(
                "Cash have been got: %s%n",
                cashToGet.entrySet().stream()
                        .map(entry -> String.format("%dx%d", entry.getKey(), entry.getValue()))
                        .collect(Collectors.joining(", "))
        );
        System.out.printf("Current ATM balance is %d%n", atm.getBalance());
    }
}

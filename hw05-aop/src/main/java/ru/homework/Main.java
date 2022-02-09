package ru.homework;

import ru.homework.logger.Logging;

public class Main {

    public static void main(String... args) {
        preStart(args);
        start(args);
    }

    private static void preStart(String... args) {
    }

    private static void start(String... args) {
        var logger = new Logging();
        logger.log();
        logger.log(100500);
        logger.log(100500, 777);
        logger.log(14.88);
        logger.log2(100500, 777, "abyrvalg");
        logger.log2(100500, 777, "abyrvalg", true);
        logger.log2(1488);
    }
}

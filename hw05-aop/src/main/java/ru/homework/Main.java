package ru.homework;

import ru.homework.logger.Logging;

/*
java -javaagent:hw05-aop.jar -jar hw05-aop.jar
*/
public class Main {

    public static void main(String... args) {

        var logger = new Logging();
        System.out.println();

        logger.log();
        System.out.println();

        logger.log(100500);
        System.out.println();

        logger.log(100500, 777);
        System.out.println();

        logger.log(14.88);
        System.out.println();

        logger.log2(777, "abyrvalg");
        System.out.println();

        logger.log2(100500, 777L, "abyrvalg");
        System.out.println();

        logger.log2(100500, 777, "abyrvalg", true);
        System.out.println();

        logger.log2(1488);
        System.out.println();
    }
}

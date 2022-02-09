package ru.homework;

import ru.homework.annotations.Log;
import ru.homework.logger.Logging;
import ru.homework.util.ReflectionUtils;

import java.io.IOException;

public class Main {

    public static void main(String... args) throws IOException {
        prepare(args);
        start(args);
    }

    /**
     * Выполнение необходимых действий перед запуском приложения
     *
     * @param args аргументы командной строки
     * @throws IOException
     */
    private static void prepare(String... args) throws IOException {
        var class2MethodsMap = ReflectionUtils.findAnnotatedMethods(Log.class);
        class2MethodsMap.forEach((className, methods) ->
                System.out.println(className + ": " + String.join(", ", methods)));
    }

    /**
     * Запуск приложения
     *
     * @param args аргументы командной строки
     */
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

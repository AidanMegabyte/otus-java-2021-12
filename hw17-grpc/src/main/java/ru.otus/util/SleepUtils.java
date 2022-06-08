package ru.otus.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SleepUtils {

    private static final Logger log = LoggerFactory.getLogger(SleepUtils.class);

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            log.error("Ошибка при усыплении потока!", e);
        }
    }
}

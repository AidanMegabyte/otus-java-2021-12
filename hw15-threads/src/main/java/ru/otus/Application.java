package ru.otus;

import ru.otus.thread.NumberThread;
import ru.otus.thread.NumberThreadSharedData;

public class Application {

    private static final String THREAD_1_NAME = "Thread #1";
    private static final String THREAD_2_NAME = "Thread #2";

    private static final NumberThreadSharedData SHARED_DATA = new NumberThreadSharedData(THREAD_2_NAME);

    public static void main(String[] args) {
        new NumberThread(THREAD_1_NAME, SHARED_DATA).start();
        new NumberThread(THREAD_2_NAME, SHARED_DATA).start();
    }
}

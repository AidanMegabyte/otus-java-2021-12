package ru.otus.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class NumberThread extends Thread {

    private static final Logger logger = LoggerFactory.getLogger(NumberThread.class);

    private final NumberThreadSharedData sharedData;

    public NumberThread(NumberThreadSharedData sharedData) {
        this.sharedData = sharedData;
    }

    public NumberThread(String name, NumberThreadSharedData sharedData) {
        super(name);
        this.sharedData = sharedData;
    }

    public NumberThread(ThreadGroup group, String name, NumberThreadSharedData sharedData) {
        super(group, name);
        this.sharedData = sharedData;
    }

    @Override
    public void run() {

        var start = 1;
        var end = 10;
        var current = start;
        var increment = -1;

        while (!isInterrupted()) {
            synchronized (sharedData) {
                try {
                    while (Objects.equals(getName(), sharedData.getLastThreadName())) {
                        sharedData.wait();
                    }
                    logger.info("{}: {}", Thread.currentThread().getName(), current);
                    sharedData.setLastThreadName(getName());
                    sharedData.notifyAll();
                    if (current == start || current == end) {
                        increment = -increment;
                    }
                    current += increment;
                    sleep(1000);
                } catch (InterruptedException e) {
                    logger.error(e.getMessage(), e);
                    interrupt();
                }
            }
        }
    }
}

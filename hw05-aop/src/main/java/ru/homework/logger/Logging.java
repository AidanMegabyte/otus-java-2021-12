package ru.homework.logger;

import ru.homework.annotations.Log;

public class Logging {

    @Log
    public void log() {
        System.out.println("log params: no params");
    }

    @Log
    public void log(int param) {
        System.out.println("log params: 1 int");
    }

    @Log
    public void log(int param1, long param2) {
        System.out.println("log params: 1 int, 1 long");
    }

    public void log(double param) {
        System.out.println("log params: 1 double");
    }

    @Log
    public void log2(int param1, long param2, String param3) {
        System.out.println("log2 params: 1 int, 1 long, 1 string");
    }

    @Log
    public void log2(int param1, long param2, String param3, boolean param4) {
        System.out.println("log2 params: 1 int, 1 long, 1 string, 1 boolean");
    }

    public void log2(int param) {
        System.out.println("log2 params: 1 int");
    }
}

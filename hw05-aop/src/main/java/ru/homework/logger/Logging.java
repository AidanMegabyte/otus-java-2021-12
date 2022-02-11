package ru.homework.logger;

import ru.homework.annotations.Log;

public class Logging {

    @Log
    public void log() {
        System.out.println("log: no params");
    }

    @Log
    public void log(int param) {
        System.out.printf("log: int (%s)%n", param);
    }

    @Log
    public void log(int param1, long param2) {
        System.out.printf("log: int (%s), long (%s)%n", param1, param2);
    }

    public void log(double param) {
        System.out.printf("log: double (%s)%n", param);
    }

    @Log
    public void log2(int param1, String param2) {
        System.out.printf("log2: int (%s), String (%s)%n", param1, param2);
    }

    @Log
    public void log2(Integer param1, Long param2, String param3) {
        System.out.printf("log2: Integer (%s), Long (%s), String (%s)%n", param1, param2, param3);
    }

    @Log
    public void log2(Integer param1, long param2, String param3, boolean param4) {
        System.out.printf(
                "log2: Integer (%s), long (%s), String (%s), boolean (%s)%n",
                param1,
                param2,
                param3,
                param4
        );
    }

    public void log2(int param) {
        System.out.printf("log2: int (%s)%n", param);
    }
}

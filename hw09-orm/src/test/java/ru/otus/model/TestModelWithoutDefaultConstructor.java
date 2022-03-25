package ru.otus.model;

public class TestModelWithoutDefaultConstructor {

    private int a;

    private boolean b;

    private String c;

    private double d;

    public TestModelWithoutDefaultConstructor(int a, boolean b, String c, double d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public boolean isB() {
        return b;
    }

    public void setB(boolean b) {
        this.b = b;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public double getD() {
        return d;
    }

    public void setD(double d) {
        this.d = d;
    }
}

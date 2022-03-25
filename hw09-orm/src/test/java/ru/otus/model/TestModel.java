package ru.otus.model;

import ru.otus.jdbc.annotation.Id;

import java.util.Objects;

public class TestModel {

    @Id
    private int a;

    private boolean b;

    private String c;

    private double d;

    public TestModel() {
    }

    public TestModel(int a, boolean b, String c, double d) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestModel testModel = (TestModel) o;
        return a == testModel.a && b == testModel.b && Double.compare(testModel.d, d) == 0 && Objects.equals(c, testModel.c);
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b, c, d);
    }
}

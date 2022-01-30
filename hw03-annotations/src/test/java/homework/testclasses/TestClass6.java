package homework.testclasses;

import homework.annotations.AfterEach;
import homework.annotations.BeforeEach;
import homework.annotations.Test;

public class TestClass6 {

    @BeforeEach
    public void beforeEach1() {
        System.out.println("Before 1");
    }

    @BeforeEach
    public void beforeEach2() {
        throw new NullPointerException("Before 2");
    }

    @BeforeEach
    public void beforeEach3() {
        System.out.println("Before 3 (will not be printed");
    }

    @Test
    public void test() {
        System.out.println("Test (will not be printed");
    }

    @AfterEach
    public void afterEach1() {
        System.out.println("After 1");
    }

    @AfterEach
    public void afterEach2() {
        throw new RuntimeException("After 2");
    }

    @AfterEach
    public void afterEach3() {
        System.out.println("After 3");
    }
}

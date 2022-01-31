package homework.testclasses;

import homework.annotations.AfterEach;
import homework.annotations.BeforeEach;
import homework.annotations.Test;

public class TestClass4 {

    @BeforeEach
    public void setUp1() {
        System.out.println("Before 1");
    }

    @BeforeEach
    public void setUp2() {
        System.out.println("Before 2");
    }

    @AfterEach
    public void tearDown1() {
        System.out.println("After 1");
    }

    @AfterEach
    public void tearDown2() {
        System.out.println("After 2");
    }

    @Test
    @BeforeEach
    public void test1() {
        System.out.println("Test 1");
    }

    @Test
    @AfterEach
    public void test2() {
        System.out.println("Test 2");
    }

    @Test
    @BeforeEach
    @AfterEach
    public void test3() {
        System.out.println("Test 3");
    }
}

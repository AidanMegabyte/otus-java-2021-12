package homework;

import homework.annotations.After;
import homework.annotations.Before;
import homework.annotations.Test;

public class TestClass4 {

    @Before
    public void setUp1() {
        System.out.println("Before 1");
    }

    @Before
    public void setUp2() {
        System.out.println("Before 2");
    }

    @After
    public void tearDown1() {
        System.out.println("After 1");
    }

    @After
    public void tearDown2() {
        System.out.println("After 2");
    }

    @Test
    @Before
    public void test1() {
        System.out.println("Test 1");
    }

    @Test
    @After
    public void test2() {
        System.out.println("Test 2");
    }

    @Test
    @Before
    @After
    public void test3() {
        System.out.println("Test 3");
    }
}

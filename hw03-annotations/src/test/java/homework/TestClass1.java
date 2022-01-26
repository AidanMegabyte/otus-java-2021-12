package homework;

import homework.annotations.After;
import homework.annotations.Before;
import homework.annotations.Test;

public class TestClass1 {

    @Before
    public void setUp1() {
        System.out.println("Before 1");
    }

    @Before
    public void setUp2() {
        System.out.println("Before 2");
    }

    @Before
    protected void setUp3() {
        System.out.println("Before 3 (will not be printed)");
    }

    @Before
    private void setUp4() {
        System.out.println("Before 4 (will not be printed)");
    }

    @After
    public void tearDown1() {
        System.out.println("After 1");
    }

    @After
    public void tearDown2() {
        System.out.println("After 2");
    }

    @After
    protected void tearDown3() {
        System.out.println("After 3 (will not be printed)");
    }

    @After
    private void tearDown4() {
        System.out.println("After 4 (will not be printed)");
    }

    @Test
    public void test1() {
        System.out.println("Test 1");
    }

    @Test
    public void test2() {
        System.out.println("Test 2");
    }

    @Test
    protected void test3() {
        System.out.println("Test 3 (will not be printed)");
    }

    @Test
    private void test4() {
        System.out.println("Test 4 (will not be printed)");
    }
}

package homework.testclasses;

import homework.annotations.AfterEach;
import homework.annotations.BeforeEach;
import homework.annotations.Test;

public class TestClass1 {

    @BeforeEach
    public void setUp1() {
        System.out.println("Before 1");
    }

    @BeforeEach
    public void setUp2() {
        System.out.println("Before 2");
    }

    @BeforeEach
    protected void setUp3() {
        System.out.println("Before 3 (will not be printed)");
    }

    @BeforeEach
    private void setUp4() {
        System.out.println("Before 4 (will not be printed)");
    }

    @AfterEach
    public void tearDown1() {
        System.out.println("After 1");
    }

    @AfterEach
    public void tearDown2() {
        System.out.println("After 2");
    }

    @AfterEach
    protected void tearDown3() {
        System.out.println("After 3 (will not be printed)");
    }

    @AfterEach
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

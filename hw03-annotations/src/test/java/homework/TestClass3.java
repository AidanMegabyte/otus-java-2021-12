package homework;

import homework.annotations.After;
import homework.annotations.Before;

public class TestClass3 {

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

    public void test() {
        System.out.println("Yatasuka Nakomode");
    }
}

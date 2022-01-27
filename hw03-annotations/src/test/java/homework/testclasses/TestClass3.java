package homework.testclasses;

import homework.annotations.AfterEach;
import homework.annotations.BeforeEach;

public class TestClass3 {

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

    public void test() {
        System.out.println("Yatasuka Nakomode");
    }
}

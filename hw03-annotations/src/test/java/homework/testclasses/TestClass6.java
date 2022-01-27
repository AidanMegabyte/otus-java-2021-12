package homework.testclasses;

import homework.annotations.Test;

public class TestClass6 {

    static int count = 0;

    public TestClass6() {
        count++;
    }

    @Test
    public void test1() {
        System.out.println(count);
    }

    @Test
    public void test2() {
        System.out.println(count);
    }

    @Test
    public void test3() {
        System.out.println(count);
    }
}

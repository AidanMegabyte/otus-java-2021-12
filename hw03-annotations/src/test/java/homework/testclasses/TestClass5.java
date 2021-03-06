package homework.testclasses;

import homework.annotations.AfterEach;
import homework.annotations.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TestClass5 {

    @Test
    public void test1() {
        assertThat(1).isEqualTo(2);
    }

    @Test
    public void test2() {
        throw new NullPointerException();
    }

    @AfterEach
    public void afterEach() {
        System.out.println("After");
    }
}

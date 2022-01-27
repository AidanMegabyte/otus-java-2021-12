package homework.testclasses;

import homework.annotations.DisplayName;
import homework.annotations.Test;

@DisplayName("Ахалай")
public class TestClass2 {

    @Test
    @DisplayName("Махалай")
    public void test1() {
        System.out.println("Абырвалг");
    }
}

package ru.calculator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("Summator")
public class CalcTests {

    @Test
    @DisplayName("Проверяем, что сумматор считает правильно")
    public void calcTest() {

        long counter = 100_000_000;
        var summator = new Summator();

        int prevValue = 99999999;
        int prevPrevValue = 99999998;
        int sumLastThreeValues = 299999994;
        int someValue = 655761157;
        int sum = 887459712;

        for (var idx = 0; idx < counter; idx++) {
            var data = new Data(idx);
            summator.calc(data);
        }

        assertThat(summator.getPrevValue()).isEqualTo(prevValue);
        assertThat(summator.getPrevPrevValue()).isEqualTo(prevPrevValue);
        assertThat(summator.getSumLastThreeValues()).isEqualTo(sumLastThreeValues);
        assertThat(summator.getSomeValue()).isEqualTo(someValue);
        assertThat(summator.getSum()).isEqualTo(sum);
    }
}

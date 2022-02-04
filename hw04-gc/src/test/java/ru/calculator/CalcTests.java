package ru.calculator;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("CalcDemo")
public class CalcTests {

    // Исходный поток вывода в консоль
    private final PrintStream originalOut = System.out;

    // Тестовый поток вывода в консоль
    private ByteArrayOutputStream testOut;

    @BeforeEach
    public void setUp() {
        // Перед каждым тестом подменяем поток вывода в консоль на тестовый, для последующей его проверки
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    }

    @AfterEach
    public void tearDown() {
        // После каждого теста восстанавливаем поток вывода в консоль
        System.setOut(originalOut);
    }

    @Test
    @DisplayName("Проверяем, что логика классов CalcDemo, Summator и Data не сломалась")
    public void calcTest() {
        // Ожидаемые результаты
        var actualSubstrings = Arrays.asList(
                "99999999",
                "99999998",
                "299999994",
                "655761157",
                "887459712",
                "current idx:0",
                "current idx:10000000",
                "current idx:20000000",
                "current idx:30000000",
                "current idx:40000000",
                "current idx:50000000",
                "current idx:60000000",
                "current idx:70000000",
                "current idx:80000000",
                "current idx:90000000"
        );
        CalcDemo.main();
        var outputStr = testOut.toString();
        actualSubstrings.forEach(str -> assertThat(str).isSubstringOf(outputStr));
    }
}

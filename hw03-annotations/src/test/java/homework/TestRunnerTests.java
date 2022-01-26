package homework;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("Тесты для утилиты запуска тестов")
public class TestRunnerTests {

    private final PrintStream originalOut = System.out;

    private ByteArrayOutputStream outContent;

    @BeforeEach
    public void setUp() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    @DisplayName("Корректность нахождения методов")
    public void testValidMethodSearching() throws IOException {
        new TestRunner().runTestSuite(TestClass1.class);
        String testClass1OutputExpected = getFixtureAsString("fixtures/TestClass1.txt");
        String testClass1OutputActual = outContent.toString().trim().replace("\r", "");
        assertThat(testClass1OutputActual).isEqualTo(testClass1OutputExpected);
    }

    @Test
    @DisplayName("Отображаемые имена для классов и методов")
    public void testClassAndMethodsNames() throws IOException {
        new TestRunner().runTestSuite(TestClass2.class);
        String testClass1OutputExpected = getFixtureAsString("fixtures/TestClass2.txt");
        String testClass1OutputActual = outContent.toString().trim().replace("\r", "");
        assertThat(testClass1OutputActual).isEqualTo(testClass1OutputExpected);
    }

    private String getFixtureAsString(String fileName) throws IOException {
        InputStream fixtureInputStream = Objects.requireNonNull(
                getClass().getClassLoader().getResourceAsStream(fileName)
        );
        return IOUtils.toString(fixtureInputStream, StandardCharsets.UTF_8);
    }
}

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
    @DisplayName("Корректность нахождения методов, помеченных аннотациями")
    public void testValidMethodSearching() throws IOException {
        test(TestClass1.class, "fixtures/TestClass1.txt");
    }

    @Test
    @DisplayName("Отображаемые имена для классов и методов")
    public void testClassAndMethodsNames() throws IOException {
        test(TestClass2.class, "fixtures/TestClass2.txt");
    }

    @Test
    @DisplayName("Отсутствие тестов")
    public void testNoTests() throws IOException {
        test(TestClass3.class, "fixtures/TestClass3.txt");
    }

    private void test(Class<?> testClass, String fixturePath) throws IOException {
        new TestRunner().runTestSuite(testClass);
        String outputExpected = getFixtureAsString(fixturePath);
        String outputActual = outContent.toString().trim().replace("\r", "");
        assertThat(outputActual).isEqualTo(outputExpected);
    }

    private String getFixtureAsString(String fileName) throws IOException {
        InputStream fixtureInputStream = Objects.requireNonNull(
                getClass().getClassLoader().getResourceAsStream(fileName)
        );
        return IOUtils.toString(fixtureInputStream, StandardCharsets.UTF_8);
    }
}

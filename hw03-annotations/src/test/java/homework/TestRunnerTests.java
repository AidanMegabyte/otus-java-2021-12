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
    private final PrintStream originalErr = System.err;

    private ByteArrayOutputStream outContent;
    private ByteArrayOutputStream errContent;

    @BeforeEach
    public void setUp() {
        outContent = new ByteArrayOutputStream();
        errContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    @DisplayName("Корректность нахождения методов, помеченных аннотациями")
    public void testValidMethodSearching() throws IOException {
        test(TestClass1.class, "fixtures/TestClass1.txt", null);
    }

    @Test
    @DisplayName("Отображаемые имена для классов и методов")
    public void testClassAndMethodsNames() throws IOException {
        test(TestClass2.class, "fixtures/TestClass2.txt", null);
    }

    @Test
    @DisplayName("Отсутствие тестов")
    public void testNoTests() throws IOException {
        test(TestClass3.class, "fixtures/TestClass3.txt", null);
    }

    @Test
    @DisplayName("Несколько аннотаций на методе")
    public void testMultipleAnnotations() throws IOException {
        test(TestClass4.class, "fixtures/TestClass4.txt", null);
    }

    @Test
    @DisplayName("Падения тестов")
    public void testFailures() throws IOException {
        test(TestClass5.class, "fixtures/TestClass5.out.txt", "fixtures/TestClass5.err.txt");
    }

    private void test(Class<?> testClass, String fixtureOutPath, String fixtureErrPath) throws IOException {
        new TestRunner().runTestSuite(testClass);
        assertOutput(outContent, fixtureOutPath);
        if (fixtureErrPath != null) {
            assertOutput(errContent, fixtureErrPath);
        }
    }

    private void assertOutput(ByteArrayOutputStream content, String fixturePath) throws IOException {
        InputStream fixtureInputStream = Objects.requireNonNull(
                getClass().getClassLoader().getResourceAsStream(fixturePath)
        );
        String expected = IOUtils.toString(fixtureInputStream, StandardCharsets.UTF_8);
        String actual = content.toString().trim().replace("\r", "");
        assertThat(actual).isEqualTo(expected);
    }
}

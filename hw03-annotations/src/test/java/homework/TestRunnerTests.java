package homework;

import homework.testclasses.*;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("Тесты для утилиты запуска тестов")
public class TestRunnerTests {

    // Исходные потоки вывода (консоль и ошибки)
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    // Тестовые потоки вывода (консоль и ошибки)
    private ByteArrayOutputStream outContent;
    private ByteArrayOutputStream errContent;

    @BeforeEach
    public void setUp() {
        // Перед каждым тестом подменяем потоки вывода (консоль и ошибки) на тестовые, для последующей их проверки
        outContent = new ByteArrayOutputStream();
        errContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    public void tearDown() {
        // После каждого теста восстанавливаем потоки вывода (консоль и ошибки)
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    @DisplayName("Корректность нахождения методов, помеченных аннотациями")
    public void testSearchingMethodsByAnnotations() throws IOException {
        test(TestClass1.class, "fixtures/TestClass1.txt", Collections.emptyList());
    }

    @Test
    @DisplayName("Отображаемые имена для наборов тестовых сценариев и самих сценариев")
    public void testDisplayNames() throws IOException {
        test(TestClass2.class, "fixtures/TestClass2.txt", Collections.emptyList());
    }

    @Test
    @DisplayName("Отсутствие тестовых сценариев в наборе")
    public void testEmptyTestSuite() throws IOException {
        test(TestClass3.class, "fixtures/TestClass3.txt", Collections.emptyList());
    }

    @Test
    @DisplayName("Несколько аннотаций на методах")
    public void testMultipleAnnotationsOnMethods() throws IOException {
        test(TestClass4.class, "fixtures/TestClass4.txt", Collections.emptyList());
    }

    @Test
    @DisplayName("Неудачное завершение тестовых сценариев")
    public void testFailureTests() throws IOException {
        List<Class<? extends Throwable>> errors = List.of(AssertionFailedError.class, NullPointerException.class);
        test(TestClass5.class, "fixtures/TestClass5.txt", errors);
    }

    /**
     * Проверка выполнения набора тестовых сценариев
     *
     * @param testSuiteClass    класс набора тестовых сценариев
     * @param outputFixturePath путь к файлу фикстуры для проверки вывода
     * @param errors            ошибки для проверки их наличия
     * @throws IOException
     */
    private void test(Class<?> testSuiteClass,
                      String outputFixturePath,
                      Collection<Class<? extends Throwable>> errors) throws IOException {
        new TestRunner().runTestSuite(testSuiteClass);
        assertOut(outputFixturePath);
        assertErr(errors);
    }

    /**
     * Проверка вывода в консоль
     *
     * @param fixturePath путь к файлу фикстуры для сравнения
     * @throws IOException
     */
    private void assertOut(String fixturePath) throws IOException {
        String expected = IOUtils.resourceToString(fixturePath, StandardCharsets.UTF_8, getClass().getClassLoader());
        String actual = testOutputStreamAsString(outContent);
        assertThat(actual).isEqualTo(expected);
    }

    /**
     * Проверка наличия ошибок
     *
     * @param errors список ошибок для проверки
     */
    private void assertErr(Collection<Class<? extends Throwable>> errors) {
        String err = testOutputStreamAsString(errContent);
        if (errors.isEmpty()) {
            assertThat(err).isEmpty();
        } else {
            errors.forEach(error -> assertThat(error.getName()).isSubstringOf(err));
        }
    }

    /**
     * Преобразование тестового потока вывода в строку
     *
     * @param testOutputStream тестовый поток вывода
     * @return строковое представление переданного тестового потока вывода
     */
    private String testOutputStreamAsString(ByteArrayOutputStream testOutputStream) {
        return testOutputStream.toString().trim().replace("\r", "");
    }
}

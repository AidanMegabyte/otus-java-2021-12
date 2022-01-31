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
    private ByteArrayOutputStream testOut;
    private ByteArrayOutputStream testErr;

    @BeforeEach
    public void setUp() {
        // Перед каждым тестом подменяем потоки вывода (консоль и ошибки) на тестовые, для последующей их проверки
        testOut = new ByteArrayOutputStream();
        testErr = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
        System.setErr(new PrintStream(testErr));
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
    @DisplayName("Неудачное завершение тестовых сценариев (ошибка в тестовом сценарии)")
    public void testFailureTests() throws IOException {
        List<Class<? extends Throwable>> errors = List.of(AssertionFailedError.class, NullPointerException.class);
        test(TestClass5.class, "fixtures/TestClass5.txt", errors);
    }

    @Test
    @DisplayName("Неудачное завершение тестовых сценариев (ошибка в методах BeforeEach и AfterEach)")
    public void testFailureBeforesAndAfters() throws IOException {
        List<Class<? extends Throwable>> errors = List.of(NullPointerException.class, RuntimeException.class);
        test(TestClass6.class, "fixtures/TestClass6.txt", errors);
    }

    @Test
    @DisplayName("Каждый тест выполняется на отдельном экземпляре класса")
    public void testCreateNewObjectForEachTest() throws IOException {
        test(TestClass7.class, "fixtures/TestClass7.txt", Collections.emptyList());
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
        String rawFixture = IOUtils.resourceToString(fixturePath, StandardCharsets.UTF_8, getClass().getClassLoader());
        String expected = trimAndRemoveEolChars(rawFixture);
        String actual = trimAndRemoveEolChars(testOut.toString());
        assertThat(actual).isEqualTo(expected);
    }

    /**
     * Проверка наличия ошибок
     *
     * @param errors список ошибок для проверки
     */
    private void assertErr(Collection<Class<? extends Throwable>> errors) {
        String err = trimAndRemoveEolChars(testErr.toString());
        if (errors.isEmpty()) {
            assertThat(err).isEmpty();
        } else {
            errors.forEach(error -> assertThat(error.getName()).isSubstringOf(err));
        }
    }

    private String trimAndRemoveEolChars(String str) {
        return str.trim().replaceAll("(\\r|\\n)", "");
    }
}

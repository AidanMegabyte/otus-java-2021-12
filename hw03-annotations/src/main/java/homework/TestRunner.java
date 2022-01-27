package homework;

import homework.annotations.AfterEach;
import homework.annotations.BeforeEach;
import homework.annotations.DisplayName;
import homework.annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestRunner {

    /**
     * Запуск набора тестовых сценариев
     *
     * @param testSuiteClass класс, содержащий набор тестовых сценариев
     */
    public void runTestSuite(Class<?> testSuiteClass) {

        // Название набора тестовых сценариев
        String testSuiteName = getDisplayName(testSuiteClass, testSuiteClass::getSimpleName);

        // Получаем списки методов, помеченных определенными аннотациями
        // Поиск ведется только среди открытых методов класса
        List<Method> methods = Arrays.asList(testSuiteClass.getMethods());
        List<Method> tests = filterMethodsByAnnotation(methods, Test.class);
        List<Method> beforeEaches = filterMethodsByAnnotation(methods, BeforeEach.class);
        List<Method> afterEaches = filterMethodsByAnnotation(methods, AfterEach.class);

        // Запускаем тестовые сценарии по очереди с выводом статуса выполнения
        int passedQty = 0;
        for (Method test : tests) {
            Optional<Throwable> testResult = runTest(testSuiteClass, test, beforeEaches, afterEaches);
            boolean passed = testResult.isEmpty();
            passedQty += passed ? 1 : 0;
            testResult.ifPresent(Throwable::printStackTrace);
            String testName = getDisplayName(test, test::getName);
            String testStatus = passed ? "PASSED" : "FAILED";
            System.out.printf("%s > %s %s\n\n", testSuiteName, testName, testStatus);
        }

        // Выводим суммарную статистику выполнения набора тестовых сценариев
        int totalQty = tests.size();
        int failedQty = totalQty - passedQty;
        System.out.printf("%d test(s) completed, %d passed, %d failed\n\n", totalQty, passedQty, failedQty);
    }

    /**
     * Получение отображаемого имени класса
     *
     * @param clazz           класс
     * @param defaultSupplier supplier на случай, если аннотация @DisplayName отсутствует
     * @return значение аннотации @DisplayName, если она есть, в ином случае - результат defaultSupplier
     */
    private String getDisplayName(AnnotatedElement clazz, Supplier<String> defaultSupplier) {
        if (clazz.isAnnotationPresent(DisplayName.class)) {
            return clazz.getAnnotation(DisplayName.class).value();
        }
        return defaultSupplier.get();
    }

    /**
     * Фильтрация методов по аннотации
     *
     * @param methods    методы
     * @param annotation аннотация
     * @return коллекцию методов, помеченных указанной аннотацией
     * Методы отсортированы по названию в алфавитном порядке
     */
    private List<Method> filterMethodsByAnnotation(Collection<Method> methods,
                                                   Class<? extends Annotation> annotation) {
        return methods.stream()
                .filter(method -> method.isAnnotationPresent(annotation))
                .sorted(Comparator.comparing(Method::getName))
                .collect(Collectors.toList());
    }

    /**
     * Запуск тестового сценария
     *
     * @param testSuiteClass класс набора тестовых сценариев
     * @param test           метод, запускающий тестовый сценарий
     * @param beforeEaches   методы, выполняющиеся перед запуском каждого тестового сценария
     * @param afterEaches    методы, выполняющиеся после запуска каждого тестового сценария
     * @return Optional: пустой при успешном выполнении тестового сценария, или содержащий ошибку в ином случае
     */
    private Optional<Throwable> runTest(Class<?> testSuiteClass,
                                        Method test,
                                        Collection<Method> beforeEaches,
                                        Collection<Method> afterEaches) {

        List<Method> methods =
                Stream.of(beforeEaches, List.of(test), afterEaches)
                        .flatMap(Collection::stream)
                        .toList();

        Throwable error = null;
        try {
            Object instance = testSuiteClass.getConstructor().newInstance();
            for (Method method : methods) {
                method.invoke(instance);
            }
        } catch (Throwable e) {
            error = Optional.ofNullable(e.getCause()).orElse(e);
        }

        return Optional.ofNullable(error);
    }
}

package homework;

import homework.annotations.After;
import homework.annotations.Before;
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
     * Запуск тестовых сценариев из указанного класса
     *
     * @param testSuiteClass класс, содержащий тестовые сценарии
     */
    public void runTestSuite(Class<?> testSuiteClass) {

        // Название списка тестовых сценариев
        String testSuiteName = getDisplayName(testSuiteClass, testSuiteClass::getSimpleName);

        // Получаем списки методов, помеченных определенными аннотациями
        // Поиск ведется только среди открытых методов класса
        List<Method> methods = Arrays.asList(testSuiteClass.getMethods());
        Collection<Method> testMethods = filterMethodsByAnnotation(methods, Test.class);
        Collection<Method> beforeMethods = filterMethodsByAnnotation(methods, Before.class);
        Collection<Method> afterMethods = filterMethodsByAnnotation(methods, After.class);

        // Запускаем тестовые сценарии по очереди с выводом статуса выполнения
        int passedQty = 0;
        for (Method testMethod : testMethods) {
            Optional<Throwable> testResult = runTest(testSuiteClass, testMethod, beforeMethods, afterMethods);
            passedQty += testResult.isEmpty() ? 1 : 0;
            testResult.ifPresent(ex -> printError(ex.getCause(), testSuiteClass.getName()));
            String testName = getDisplayName(testMethod, testMethod::getName);
            System.out.printf("%s > %s %s\n\n", testSuiteName, testName, (testResult.isEmpty() ? "PASSED" : "FAILED"));
        }

        // Выводим суммарную статистику выполнения тестовых сценариев
        int totalQty = testMethods.size();
        int failedQty = totalQty - passedQty;
        System.out.printf("%d test(s) completed, %d passed, %d failed\n\n", totalQty, passedQty, failedQty);
    }

    /**
     * Получение отображаемого имени
     *
     * @param element         класс списка тестовых сценариев или тестовый сценарий
     * @param defaultSupplier supplier на случай, если аннотация @DisplayName отсутствует
     * @return значение аннотации @DisplayName, если таковая есть, в ином случае - название класса/метода
     */
    private String getDisplayName(AnnotatedElement element, Supplier<String> defaultSupplier) {
        if (element.isAnnotationPresent(DisplayName.class)) {
            return element.getAnnotation(DisplayName.class).value();
        }
        return defaultSupplier.get();
    }

    /**
     * Фильтрация методов класса по аннотации
     *
     * @param methods    методы класса
     * @param annotation аннотация
     * @return коллекцию методов класса, помеченных указанной аннотацией
     * Методы отсортированы по названию в алфавитном порядке
     */
    private Collection<Method> filterMethodsByAnnotation(Collection<Method> methods,
                                                         Class<? extends Annotation> annotation) {
        return methods.stream()
                .filter(method -> method.isAnnotationPresent(annotation))
                .sorted(Comparator.comparing(Method::getName))
                .collect(Collectors.toList());
    }

    /**
     * Запуск тестового сценария
     *
     * @param testSuiteClass класс списка тестовых сценариев
     * @param testMethod     метод, помеченный аннотацией @Test
     * @param beforeMethods  методы, помеченные аннотацией @Before
     * @param afterMethods   методы, помеченные аннотацией @After
     * @return Optional: пустой при успешном выполнении тестового сценария, или содержащий ошибку в ином случае
     */
    private Optional<Throwable> runTest(Class<?> testSuiteClass,
                                        Method testMethod,
                                        Collection<Method> beforeMethods,
                                        Collection<Method> afterMethods) {

        List<Method> methods =
                Stream.of(beforeMethods, List.of(testMethod), afterMethods)
                        .flatMap(Collection::stream)
                        .toList();

        Throwable error = null;
        try {
            Object instance = testSuiteClass.getConstructor().newInstance();
            for (Method method : methods) {
                method.invoke(instance);
            }
        } catch (Throwable e) {
            error = e;
        }

        return Optional.ofNullable(error);
    }

    /**
     * Печать ошибки
     *
     * @param ex                 исключение
     * @param testSuiteClassName название класса с тестовыми сценариями
     */
    private void printError(Throwable ex, String testSuiteClassName) {

        StringBuilder sb = new StringBuilder();

        String exClassName = ex.getClass().getName();
        String message = Optional.ofNullable(ex.getMessage()).orElse("");
        String colon = message.equals("") ? "" : ":";
        sb.append(String.format("%s%s%s\n", exClassName, colon, message));

        Optional<StackTraceElement> stackTraceElementOpt = Arrays.stream(ex.getStackTrace())
                .filter(stackTraceElement -> stackTraceElement.getClassName().equals(testSuiteClassName))
                .findFirst();
        stackTraceElementOpt.ifPresent(stackTraceElement -> {
            String methodName = stackTraceElement.getMethodName();
            String fileName = stackTraceElement.getFileName();
            int lineNumber = stackTraceElement.getLineNumber();
            String at = String.format("    at %s.%s(%s:%d)\n", testSuiteClassName, methodName, fileName, lineNumber);
            sb.append(at);
        });
        System.err.print(sb);
    }
}

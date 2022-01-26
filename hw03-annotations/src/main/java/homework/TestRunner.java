package homework;

import homework.annotations.After;
import homework.annotations.Before;
import homework.annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestRunner {

    /**
     * Запуск тестовых сценариев из указанного класса
     *
     * @param testClass класс, содержащий тестовые сценарии
     */
    public static void runTestClass(Class<?> testClass) {

        String className = testClass.getSimpleName();

        // Получаем списки методов, помеченных определенными аннотациями
        List<Method> beforeMethods = findMethods(testClass, Before.class);
        List<Method> testMethods = findMethods(testClass, Test.class);
        List<Method> afterMethods = findMethods(testClass, After.class);

        // Запускаем тесты по очереди с выводом статуса выполнения
        int passedQty = 0;
        int failedQty = 0;
        for (Method method : testMethods) {
            String methodName = method.getName();
            boolean passed = true;
            try {
                runTest(testClass, beforeMethods, method, afterMethods);
                passedQty++;
            } catch (Throwable e) {
                passed = false;
                failedQty++;
            } finally {
                System.out.printf("%s > %s %s\n", className, methodName, (passed ? "PASSED" : "FAILED"));
            }
        }

        // Считаем и выводим суммарную статистику выполнения тестов
        int totalQty = passedQty + failedQty;
        System.out.printf("%d tests completed, %d passed, %d failed\n", totalQty, passedQty, failedQty);
    }

    /**
     * Поиск открытых методов класса, помеченных аннотацией
     *
     * @param testClass       класс, в котором осуществляется поиск
     * @param annotationClass аннотация, которой должны быть помечены открытые методы класса
     * @return список открытых методов переданного класса, которые помечены переданной аннотацией
     */
    private static List<Method> findMethods(Class<?> testClass, Class<? extends Annotation> annotationClass) {
        return Arrays.stream(testClass.getMethods())
                .filter(method -> method.isAnnotationPresent(annotationClass))
                .sorted(Comparator.comparing(Method::getName))
                .collect(Collectors.toList());
    }

    /**
     * Запуск выполнения теста
     *
     * @param testClass     класс с тестовыми сценариями
     * @param beforeMethods методы с аннотацией Before
     * @param testMethod    метод запуска тестового сценария
     * @param afterMethods  методы с аннотацией After
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private static void runTest(Class<?> testClass, List<Method> beforeMethods, Method testMethod, List<Method> afterMethods) throws Throwable {
        Object classInstance = testClass.getConstructor().newInstance();
        List<Method> methods = Stream.of(beforeMethods, List.of(testMethod), afterMethods)
                .flatMap(Collection::stream)
                .toList();
        for (Method method : methods) {
            method.invoke(classInstance);
        }
    }
}

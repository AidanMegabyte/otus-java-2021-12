package ru.homework.util;

import com.google.common.reflect.ClassPath;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

public class ReflectionUtils {

    /**
     * Поиск методов, помеченных аннотацией, среди открытых методов всех классов
     *
     * @param annotation аннотация, которой должны быть помечены методы класса
     * @return словарь названий классов и названий их методов, которые помечены указанной аннотацией
     * @throws IOException
     */
    public static Map<String, ? extends Collection<String>> findAnnotatedMethods(Class<? extends Annotation> annotation) throws IOException {
        return ClassPath.from(ClassLoader.getSystemClassLoader())
                .getAllClasses()
                .stream()
                .map(ClassPath.ClassInfo::load)
                .flatMap(clazz -> Arrays.stream(clazz.getMethods()))
                .filter(method -> method.isAnnotationPresent(annotation))
                .collect(Collectors.groupingBy(
                        method -> method.getDeclaringClass().getName(),
                        Collectors.mapping(Method::getName, Collectors.toSet())
                ));
    }
}

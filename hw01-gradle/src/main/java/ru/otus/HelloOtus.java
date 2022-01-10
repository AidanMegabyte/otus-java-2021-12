package ru.otus;

import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;

public class HelloOtus {

    public static void main(String... args) {
        List<String> words = Arrays.asList("махалай", "-", "ахалай");
        StringBuilder sb = new StringBuilder();
        Lists.reverse(words).forEach(sb::append);
        System.out.println(sb);
    }
}

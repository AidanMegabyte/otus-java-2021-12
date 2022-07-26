package ru.otus.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.service.UserService;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {

    private final List<String> users = Arrays.asList("Вагон Героев", "Тояма Токанава", "Бздашек Западловский");

    @Override
    public String getRandomUser() {
        var idx = new Random().nextInt(users.size());
        return users.get(idx);
    }
}

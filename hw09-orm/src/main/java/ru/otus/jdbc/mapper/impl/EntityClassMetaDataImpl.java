package ru.otus.jdbc.mapper.impl;

import ru.otus.exception.ClassMetaDataException;
import ru.otus.jdbc.annotation.Id;
import ru.otus.jdbc.mapper.EntityClassMetaData;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {

    private final String name;

    private final Constructor<T> constructor;

    private final List<Field> fields;

    private final Field idField;

    public EntityClassMetaDataImpl(Class<T> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type cannot be null!");
        }
        try {
            this.name = type.getSimpleName();
            this.constructor = type.getConstructor();
            this.fields = Arrays.asList(type.getDeclaredFields());
            this.idField = this.fields.stream()
                    .filter(field -> field.isAnnotationPresent(Id.class))
                    .findFirst()
                    .orElseThrow(() -> new ClassMetaDataException(String.format("%s - ID field not found!", this.name)));
        } catch (NoSuchMethodException ex) {
            throw new ClassMetaDataException(String.format("%s - default constructor required!", type.getName()));
        }
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Constructor<T> getConstructor() {
        return this.constructor;
    }

    @Override
    public Field getIdField() {
        return this.idField;
    }

    @Override
    public List<Field> getAllFields() {
        return this.fields;
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return this.fields.stream()
                .filter(field -> !Objects.equals(field, this.idField))
                .collect(Collectors.toList());
    }
}

package ru.otus;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.exception.ClassMetaDataException;
import ru.otus.jdbc.mapper.EntityClassMetaData;
import ru.otus.jdbc.mapper.impl.EntityClassMetaDataImpl;
import ru.otus.model.TestModel;
import ru.otus.model.TestModelWithoutDefaultConstructor;
import ru.otus.model.TestModelWithoutId;

import java.lang.reflect.Field;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class EntityClassMetaDataImplTests {

    @Test
    @DisplayName("Проверяем корректность извлечения информации о конструкторе и полях")
    public void testConstructorAndFieldsDataValidity() {

        EntityClassMetaData<TestModel> entityClassMetaData = new EntityClassMetaDataImpl<>(TestModel.class);
        var name = entityClassMetaData.getName();
        var constructorParameterCount = entityClassMetaData.getConstructor().getParameterCount();
        var idFieldName = entityClassMetaData.getIdField().getName();
        var allFieldNames = entityClassMetaData.getAllFields().stream()
                .map(Field::getName)
                .sorted()
                .collect(Collectors.joining());
        var fieldNamesWithoutId = allFieldNames.replace(idFieldName, "");

        assertThat(name).isEqualTo(TestModel.class.getName());
        assertThat(constructorParameterCount).isZero();
        assertThat(idFieldName).isEqualTo("a");
        assertThat(allFieldNames).isEqualTo("abcd");
        assertThat(fieldNamesWithoutId).isEqualTo("bcd");
    }

    @Test
    @DisplayName("Проверяем обработку отсутствия Class'а")
    public void testNoClassError() {
        assertThatThrownBy(() -> new EntityClassMetaDataImpl<>(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Проверяем обработку отсутствия ID")
    public void testNoIdError() {
        assertThatThrownBy(() -> new EntityClassMetaDataImpl<>(TestModelWithoutId.class))
                .isInstanceOf(ClassMetaDataException.class);
    }

    @Test
    @DisplayName("Проверяем обработку отсутствия конструктора по умолчанию")
    public void testNoDefaultConstructorError() {
        assertThatThrownBy(() -> new EntityClassMetaDataImpl<>(TestModelWithoutDefaultConstructor.class))
                .isInstanceOf(ClassMetaDataException.class);
    }
}

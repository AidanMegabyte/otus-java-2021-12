package ru.otus.dataprocessor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import ru.otus.model.Measurement;

import java.io.IOException;
import java.util.List;

public class ResourcesFileLoader implements Loader {

    private final String fileName;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public ResourcesFileLoader(String fileName) {
        if (fileName == null) {
            throw new IllegalArgumentException("File name cannot be null!");
        }
        this.fileName = fileName;
        var module = new SimpleModule();
        module.addDeserializer(Measurement.class, new MeasurementDeserializer());
        objectMapper.registerModule(module);
    }

    @Override
    public List<Measurement> load() {
        //читает файл, парсит и возвращает результат
        try (var is = this.getClass().getClassLoader().getResourceAsStream(fileName)) {
            return objectMapper.readValue(is, new TypeReference<>() {});
        } catch (IOException e) {
            throw new FileProcessException(e);
        }
    }
}

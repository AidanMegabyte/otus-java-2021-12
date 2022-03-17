package ru.otus.dataprocessor;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

public class FileSerializer implements Serializer {

    private final String fileName;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public FileSerializer(String fileName) {
        if (fileName == null) {
            throw new IllegalArgumentException("File name cannot be null!");
        }
        this.fileName = fileName;
    }

    @Override
    public void serialize(Map<String, Double> data) {
        //формирует результирующий json и сохраняет его в файл
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null!");
        }
        try (var os = new FileOutputStream(fileName)) {
            objectMapper.writeValue(os, data);
        } catch (IOException e) {
            throw new FileProcessException(e);
        }
    }
}

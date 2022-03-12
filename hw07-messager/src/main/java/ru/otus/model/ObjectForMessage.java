package ru.otus.model;

import java.util.Collections;
import java.util.List;

public class ObjectForMessage {

    private List<String> data;

    public ObjectForMessage() {
        this.data = Collections.emptyList();
    }

    public ObjectForMessage(ObjectForMessage other) {
        if (other == null) {
            throw new IllegalArgumentException("Object for message cannot be null!");
        }
        setData(other.getData());
    }

    public List<String> getData() {
        return List.copyOf(data);
    }

    public void setData(List<String> data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null!");
        }
        this.data = List.copyOf(data);
    }

    @Override
    public String toString() {
        return "ObjectForMessage{" +
                "data=" + data +
                '}';
    }
}

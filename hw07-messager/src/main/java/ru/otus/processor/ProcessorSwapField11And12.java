package ru.otus.processor;

import ru.otus.model.Message;

public class ProcessorSwapField11And12 implements Processor {

    @Override
    public Message process(Message message) {
        if (message == null) {
            throw new IllegalArgumentException("Message cannot be null!");
        }
        return message.toBuilder()
                .field11(message.getField12())
                .field12(message.getField11())
                .build();
    }
}

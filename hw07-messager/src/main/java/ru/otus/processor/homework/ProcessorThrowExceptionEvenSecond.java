package ru.otus.processor.homework;

import ru.otus.model.Message;
import ru.otus.processor.Processor;

import java.time.LocalDateTime;
import java.util.function.Supplier;

public class ProcessorThrowExceptionEvenSecond implements Processor {

    private final Supplier<LocalDateTime> localDateTimeSupplier;

    public ProcessorThrowExceptionEvenSecond(Supplier<LocalDateTime> localDateTimeSupplier) {
        if (localDateTimeSupplier == null) {
            throw new IllegalArgumentException("Local datetime supplier cannot be null!");
        }
        this.localDateTimeSupplier = localDateTimeSupplier;
    }

    @Override
    public Message process(Message message) {
        if (localDateTimeSupplier.get().getSecond() % 2 == 0) {
            throw new EvenSecondException("The current second is even!");
        }
        return message;
    }
}

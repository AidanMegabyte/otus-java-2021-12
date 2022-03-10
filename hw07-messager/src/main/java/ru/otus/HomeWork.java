package ru.otus;

import ru.otus.handler.ComplexProcessor;
import ru.otus.listener.ListenerPrinterConsole;
import ru.otus.listener.homework.HistoryListener;
import ru.otus.model.Message;
import ru.otus.processor.homework.ProcessorSwapField11And12;
import ru.otus.processor.homework.ProcessorThrowExceptionEvenSecond;

import java.time.LocalDateTime;
import java.util.List;

public class HomeWork {

    public static void main(String[] args) {

        var localDateTime = LocalDateTime.now().withSecond(1);
        var processors = List.of(
                new ProcessorSwapField11And12(),
                new ProcessorThrowExceptionEvenSecond(() -> localDateTime)
        );

        var complexProcessor = new ComplexProcessor(processors, ex -> {
        });

        var listenerPrinter = new ListenerPrinterConsole();
        var historyListener = new HistoryListener();
        complexProcessor.addListener(listenerPrinter);
        complexProcessor.addListener(historyListener);

        var message = new Message.Builder(1L)
                .field1("field1")
                .field2("field2")
                .field3("field3")
                .field6("field6")
                .field10("field10")
                .field11("field11")
                .field12("field12")
                .build();

        var result = complexProcessor.handle(message);
        System.out.println("result:" + result);

        complexProcessor.removeListener(listenerPrinter);
        complexProcessor.removeListener(historyListener);
    }
}

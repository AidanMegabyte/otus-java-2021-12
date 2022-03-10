package ru.otus.processor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.model.Message;
import ru.otus.processor.homework.ProcessorSwapField11And12;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@DisplayName("Тесты процессора, который поменяет местами значения field11 и field12")
public class ProcessorSwapField11And12Test {

    private final Processor processor = new ProcessorSwapField11And12();

    @Test
    @DisplayName("Проверяем обмен местами значений field11 и field12")
    public void swapTest() {
        var message1 = new Message.Builder(1L).field11("field11").field12("field12").build();
        var message2 = processor.process(message1);
        assertThat(message1.getField11()).isEqualTo(message2.getField12());
        assertThat(message1.getField12()).isEqualTo(message2.getField11());
    }

    @Test
    @DisplayName("Проверяем обработку отсутствия сообщения")
    public void nullMessageTest() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> processor.process(null));
    }
}

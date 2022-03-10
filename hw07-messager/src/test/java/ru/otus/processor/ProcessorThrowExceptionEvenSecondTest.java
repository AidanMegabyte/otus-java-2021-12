package ru.otus.processor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.model.Message;
import ru.otus.processor.homework.EvenSecondException;
import ru.otus.processor.homework.ProcessorThrowExceptionEvenSecond;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@DisplayName("Тесты процессора, который выбрасывает исключение в четную секунду")
public class ProcessorThrowExceptionEvenSecondTest {

    @Test
    @DisplayName("Проверяем выброс исключения в четную секунду")
    public void evenSecondTest() {
        var localDateTime = LocalDateTime.now().withSecond(2);
        var processor = new ProcessorThrowExceptionEvenSecond(() -> localDateTime);
        assertThatExceptionOfType(EvenSecondException.class).isThrownBy(() -> processor.process(null));
    }

    @Test
    @DisplayName("Проверяем возврат сообщения в нечетную секунду")
    public void oddSecondTest() {
        var message = new Message.Builder(1L).field1("abyrvalg").build();
        var localDateTime = LocalDateTime.now().withSecond(1);
        var processor = new ProcessorThrowExceptionEvenSecond(() -> localDateTime);
        assertThat(processor.process(message)).isEqualTo(message);
        assertThat(processor.process(message).toString()).isEqualTo(message.toString());
    }

    @Test
    @DisplayName("Проверяем обработку отсутствия провайдера даты-времени")
    public void nullLocalDateTimeSupplierTest() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> new ProcessorThrowExceptionEvenSecond(null));
    }
}

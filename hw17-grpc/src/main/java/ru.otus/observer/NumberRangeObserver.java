package ru.otus.observer;

import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.protobuf.generated.NumberResponse;

public class NumberRangeObserver implements StreamObserver<NumberResponse> {

    private static final Logger log = LoggerFactory.getLogger(NumberRangeObserver.class);

    private long receivedNumber;

    @Override
    public void onNext(NumberResponse value) {
        setReceivedNumber(value.getNumber());
    }

    @Override
    public void onError(Throwable t) {
        log.error("Ошибка при чтении диапазона чисел!", t);
    }

    @Override
    public void onCompleted() {
        log.info("Чтение диапазона чисел завершено!");
    }

    public synchronized long getReceivedNumber() {
        return receivedNumber;
    }

    public synchronized void setReceivedNumber(long receivedNumber) {
        this.receivedNumber = receivedNumber;
    }
}

package ru.otus;

import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.observer.NumberRangeObserver;
import ru.otus.protobuf.generated.NumberRangeRequest;
import ru.otus.protobuf.generated.NumberRangeServiceGrpc;
import ru.otus.util.SleepUtils;

public class Client {

    private static final Logger log = LoggerFactory.getLogger(Client.class);

    private static final String SERVER_HOST = "localhost";

    private static final int SERVER_PORT = 8190;

    private static final long RANGE_START = 0;

    private static final long RANGE_END = 30;

    private static final int SECONDS_TO_WATCH = 50;

    public static void main(String[] args) {

        log.info("Клиент запускается...");

        var channel = ManagedChannelBuilder.forAddress(SERVER_HOST, SERVER_PORT)
                .usePlaintext()
                .build();
        var stub = NumberRangeServiceGrpc.newStub(channel);

        log.info("Клиент запущен!");

        var observer = new NumberRangeObserver();
        stub.generate(
                NumberRangeRequest.newBuilder().setStart(RANGE_START).setEnd(RANGE_END).build(),
                observer
        );

        var currentValue = 0L;
        var lastReceivedNumber = RANGE_START - 1;
        for (var i = 0; i < SECONDS_TO_WATCH; i++) {
            currentValue++;
            var receivedNumber = observer.getReceivedNumber();
            if (receivedNumber != lastReceivedNumber) {
                lastReceivedNumber = receivedNumber;
                currentValue += receivedNumber;
            }
            log.info("lastReceivedNumber = {}, currentValue = {}", lastReceivedNumber, currentValue);
            SleepUtils.sleep(1000);
        }

        channel.shutdown();
        log.info("Клиент остановлен!");
    }
}

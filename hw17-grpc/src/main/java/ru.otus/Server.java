package ru.otus;

import io.grpc.ServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.service.NumberRangeServiceImpl;

import java.io.IOException;

public class Server {

    private static final Logger log = LoggerFactory.getLogger(Server.class);

    public static final int SERVER_PORT = 8190;

    public static void main(String[] args) throws IOException, InterruptedException {

        log.info("Сервер запускается...");

        var remoteNumberRangeServer = new NumberRangeServiceImpl();
        var server = ServerBuilder
                .forPort(SERVER_PORT)
                .addService(remoteNumberRangeServer)
                .build();

        server.start();
        log.info("Сервер запущен!");

        server.awaitTermination();
        log.info("Сервер остановлен!");
    }
}

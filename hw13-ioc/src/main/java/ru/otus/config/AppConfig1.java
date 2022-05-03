package ru.otus.config;

import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;
import ru.otus.services.IOService;
import ru.otus.services.IOServiceStreams;
import ru.otus.services.PlayerService;
import ru.otus.services.PlayerServiceImpl;

@AppComponentsContainerConfig(order = 1)
public class AppConfig1 {

    @AppComponent(order = 0, name = "ioService")
    public IOService ioService() {
        return new IOServiceStreams(System.out, System.in);
    }

    @AppComponent(order = 1, name = "playerService")
    public PlayerService playerService(IOService ioService) {
        return new PlayerServiceImpl(ioService);
    }
}

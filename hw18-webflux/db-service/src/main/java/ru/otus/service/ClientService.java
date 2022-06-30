package ru.otus.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.model.ClientDto;

import javax.annotation.Nonnull;
import java.util.List;

public interface ClientService {

    Flux<ClientDto> findAllClients();

    Mono<ClientDto> createClient(@Nonnull String name, @Nonnull String street, @Nonnull List<String> phoneNumbers);
}

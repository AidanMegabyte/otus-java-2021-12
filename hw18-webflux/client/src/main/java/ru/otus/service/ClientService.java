package ru.otus.service;

import ru.otus.model.Client;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Optional;

public interface ClientService {

    Iterable<Client> findAllClients();

    Optional<Client> getClient(long id);

    Client saveClient(@Nonnull String name,
                      @Nonnull String street,
                      @Nonnull Collection<String> phoneNumbers);
}

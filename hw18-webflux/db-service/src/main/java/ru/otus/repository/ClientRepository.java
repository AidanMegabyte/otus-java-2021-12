package ru.otus.repository;

import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import ru.otus.model.Client;

public interface ClientRepository extends ReactiveSortingRepository<Client, Long> {
}

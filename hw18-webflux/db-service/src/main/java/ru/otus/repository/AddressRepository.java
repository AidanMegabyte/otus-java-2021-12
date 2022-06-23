package ru.otus.repository;

import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import reactor.core.publisher.Mono;
import ru.otus.model.Address;

public interface AddressRepository extends ReactiveSortingRepository<Address, Long> {

    Mono<Address> findFirstByClientId(long clientId);
}

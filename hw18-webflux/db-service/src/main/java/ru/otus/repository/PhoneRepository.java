package ru.otus.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import reactor.core.publisher.Flux;
import ru.otus.model.Phone;

public interface PhoneRepository extends ReactiveSortingRepository<Phone, Long> {

    Flux<Phone> findAllByClientId(long clientId, Sort sort);
}

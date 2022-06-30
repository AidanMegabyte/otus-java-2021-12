package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.model.Address;
import ru.otus.model.Client;
import ru.otus.model.ClientDto;
import ru.otus.model.Phone;
import ru.otus.repository.AddressRepository;
import ru.otus.repository.ClientRepository;
import ru.otus.repository.PhoneRepository;

import javax.annotation.Nonnull;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    private final AddressRepository addressRepository;

    private final PhoneRepository phoneRepository;

    @Override
    public Flux<ClientDto> findAllClients() {
        var sort = Sort.by(Sort.Direction.ASC, "id");
        return clientRepository.findAll(sort).flatMap(client -> {

            var result = new ClientDto();

            result.setId(client.getId());
            result.setName(client.getName());

            var addressMono = addressRepository.findFirstByClientId(client.getId());
            var phonesMono = phoneRepository.findAllByClientId(client.getId(), sort).collectList();

            return Mono.zip(addressMono, phonesMono, (address, phones) -> {
                result.setStreet(address.getStreet());
                result.setPhoneNumbers(phones.stream().map(Phone::getNumber).toList());
                return result;
            });
        });
    }

    @Override
    @Transactional
    public Mono<ClientDto> createClient(@Nonnull String name,
                                        @Nonnull String street,
                                        @Nonnull List<String> phoneNumbers) {
        return clientRepository.save(new Client(null, name)).flatMap(client -> {

            var result = new ClientDto();

            result.setId(client.getId());
            result.setName(client.getName());

            var addressMono =
                    addressRepository.save(new Address(null, street, client.getId()));
            var phonesMono = phoneRepository.saveAll(
                    phoneNumbers.stream()
                            .map(phoneNumber -> new Phone(null, phoneNumber, client.getId()))
                            .toList()
            ).collectList();

            return Mono.zip(addressMono, phonesMono, (address, phones) -> {
                result.setStreet(address.getStreet());
                result.setPhoneNumbers(phones.stream().map(Phone::getNumber).toList());
                return result;
            });
        });
    }
}

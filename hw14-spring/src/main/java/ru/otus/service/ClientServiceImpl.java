package ru.otus.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.otus.model.Address;
import ru.otus.model.Client;
import ru.otus.model.Phone;
import ru.otus.repository.ClientRepository;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Iterable<Client> findAllClients() {
        return clientRepository.findAll();
    }

    @Override
    public Optional<Client> getClient(long id) {
        return clientRepository.findById(id);
    }

    @Override
    @Transactional
    public Client saveClient(@Nonnull String name,
                             @Nonnull String street,
                             @Nonnull Collection<String> phoneNumbers) {
        var address = new Address(null, street);
        var phones = phoneNumbers.stream()
                .filter(StringUtils::hasText)
                .distinct()
                .map(phoneNumber -> new Phone(null, phoneNumber))
                .collect(Collectors.toSet());
        return clientRepository.save(new Client(null, name, address, phones));
    }
}

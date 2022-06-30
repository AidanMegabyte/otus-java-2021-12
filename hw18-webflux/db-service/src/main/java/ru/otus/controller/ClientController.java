package ru.otus.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.model.ClientDto;
import ru.otus.service.ClientService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/client")
public class ClientController {

    private final ClientService clientService;

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<ClientDto> getClients() {
        return clientService.findAllClients();
    }

    @PostMapping(value = "/add", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Mono<ClientDto> addClient(@RequestBody ClientDto clientDto) {
        return clientService.createClient(clientDto.getName(), clientDto.getStreet(), clientDto.getPhoneNumbers());
    }
}

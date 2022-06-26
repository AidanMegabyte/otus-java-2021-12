package ru.otus.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.model.ClientDto;

@RestController
public class ClientController {

    private final WebClient client;

    public ClientController(WebClient.Builder builder) {
        client = builder
                .baseUrl("http://localhost:8080")
                .build();
    }

    @GetMapping(produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<ClientDto> getClients() {
        return client.get().uri("/api/client/list")
                .accept(MediaType.APPLICATION_NDJSON)
                .retrieve()
                .bodyToFlux(ClientDto.class);
    }

    @PostMapping(value = "/add", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Mono<ClientDto> addClient(@RequestBody ClientDto clientDto) {
        return client.post().uri("/api/client/add")
                .accept(MediaType.APPLICATION_NDJSON)
                .bodyValue(clientDto)
                .retrieve()
                .bodyToMono(ClientDto.class);
    }
}

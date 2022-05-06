package ru.otus.controller;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.service.ClientService;

import java.util.List;

@Controller
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public String getClients(Model model) {
        model.addAttribute("clients", clientService.findAllClients());
        return "clients";
    }

    @PostMapping("/add")
    public String addClient(AddClientFormData formData) {
        clientService.saveClient(formData.getName(), formData.getStreet(), formData.getPhoneNumbers());
        return "redirect:/";
    }

    @Getter
    @Setter
    private static class AddClientFormData {

        private String name;

        private String street;

        private List<String> phoneNumbers;
    }
}

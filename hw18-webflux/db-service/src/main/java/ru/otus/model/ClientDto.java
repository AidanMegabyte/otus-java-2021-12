package ru.otus.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto {

    private Long id;

    private String name;

    private String street;

    private List<String> phoneNumbers;
}

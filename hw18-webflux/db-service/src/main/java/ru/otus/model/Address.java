package ru.otus.model;

import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.annotation.Nonnull;

@Table("address")
@Getter
@ToString
public class Address implements Cloneable {

    @Id
    @Nonnull
    private final Long id;

    @Column("street")
    @Nonnull
    private final String street;

    @Column("client_id")
    @Nonnull
    private final Long clientId;

    @PersistenceCreator
    public Address(Long id, String street, Long clientId) {
        this.id = id;
        this.street = street;
        this.clientId = clientId;
    }

    @Override
    protected Address clone() {
        return new Address(getId(), getStreet(), getClientId());
    }
}

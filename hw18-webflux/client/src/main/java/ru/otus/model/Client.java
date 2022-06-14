package ru.otus.model;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Table("client")
@Getter
public class Client implements Cloneable, Persistable<Long> {

    @Id
    @Nonnull
    private final Long id;

    @Column("name")
    @Nonnull
    private final String name;

    @MappedCollection(idColumn = "client_id")
    @Nonnull
    private final Address address;

    @MappedCollection(idColumn = "client_id", keyColumn = "id")
    @Nonnull
    private final Set<Phone> phones;

    @Transient
    private final boolean isNew;

    @PersistenceCreator
    public Client(Long id, String name, Address address, Set<Phone> phones) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phones = phones;
        this.isNew = id == null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(id, client.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", address=" + getAddress() +
                ", phones=" + getPhones() +
                '}';
    }

    @Override
    public Client clone() {
        var address = getAddress() == null ? null : getAddress().clone();
        var phones = getPhones() == null ?
                null : getPhones().stream().map(Phone::clone).collect(Collectors.toSet());
        return new Client(getId(), getName(), address, phones);
    }
}

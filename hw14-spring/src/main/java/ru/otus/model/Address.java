package ru.otus.model;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.annotation.Nonnull;

@Table("address")
@Getter
public class Address implements Cloneable, Persistable<Long> {

    @Id
    @Nonnull
    private final Long id;

    @Column("street")
    @Nonnull
    private final String street;

    @Transient
    private final boolean isNew;

    @PersistenceConstructor
    public Address(Long id, String street) {
        this.id = id;
        this.street = street;
        this.isNew = id == null;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + getId() +
                ", street='" + getStreet() + '\'' +
                '}';
    }

    @Override
    protected Address clone() {
        return new Address(getId(), getStreet());
    }
}

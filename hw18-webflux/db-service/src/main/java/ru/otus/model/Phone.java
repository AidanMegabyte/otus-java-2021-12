package ru.otus.model;

import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.annotation.Nonnull;

@Table("phone")
@Getter
@ToString
public class Phone implements Cloneable {

    @Id
    @Nonnull
    private final Long id;

    @Column("number")
    @Nonnull
    private final String number;

    @Column("client_id")
    @Nonnull
    private final Long clientId;

    @PersistenceCreator
    public Phone(Long id, String number, Long clientId) {
        this.id = id;
        this.number = number;
        this.clientId = clientId;
    }

    @Override
    protected Phone clone() {
        return new Phone(getId(), getNumber(), getClientId());
    }
}

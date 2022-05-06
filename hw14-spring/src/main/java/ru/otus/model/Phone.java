package ru.otus.model;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.annotation.Nonnull;

@Table("phone")
@Getter
public class Phone implements Cloneable, Persistable<Long> {

    @Id
    @Nonnull
    private final Long id;

    @Column("number")
    @Nonnull
    private final String number;

    @Transient
    private final boolean isNew;

    @PersistenceConstructor
    public Phone(Long id, String number) {
        this.id = id;
        this.number = number;
        this.isNew = id == null;
    }

    @Override
    public String toString() {
        return "Phone{" +
                "id=" + getId() +
                ", number='" + getNumber() + '\'' +
                '}';
    }

    @Override
    protected Phone clone() {
        return new Phone(getId(), getNumber());
    }
}

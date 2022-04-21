package ru.otus.crm.model;

import javax.persistence.*;

@Entity
@Table(name = "phone")
public class Phone implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "phone_generator")
    @SequenceGenerator(name = "phone_generator", sequenceName = "phone_sequence", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "number")
    private String number;

    public Phone() {
    }

    public Phone(Long id, String number) {
        setId(id);
        setNumber(number);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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

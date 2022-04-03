package ru.otus.crm.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "client")
public class Client implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_generator")
    @SequenceGenerator(name = "client_generator", sequenceName = "client_sequence", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "client_id", nullable = false, updatable = false)
    private final List<Phone> phones = new ArrayList<>();

    public Client() {
    }

    public Client(String name) {
        setName(name);
    }

    public Client(Long id, String name) {
        setId(id);
        setName(name);
    }

    public Client(Long id, String name, Address address, List<Phone> phones) {
        setId(id);
        setName(name);
        setAddress(address);
        setPhones(phones);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones.clear();
        if (phones != null) {
            this.phones.addAll(phones);
        }
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
        var phones = getPhones() == null ? null : getPhones().stream().map(Phone::clone).toList();
        return new Client(getId(), getName(), address, phones);
    }
}

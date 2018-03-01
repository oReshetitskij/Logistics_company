package edu.netcracker.project.logistic.model;

public class Address {
    Long id;
    String name;

    public Address(String name) {
        this.name = name;
    }

    public Address(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Address() {

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

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

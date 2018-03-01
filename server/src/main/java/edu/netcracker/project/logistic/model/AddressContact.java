package edu.netcracker.project.logistic.model;

public class AddressContact {

    private Long address_contact_id;
    private Address address;
    private Contact contact;

    public AddressContact(Long address_contact_id, Address address, Contact contact) {
        this.address_contact_id = address_contact_id;
        this.address = address;
        this.contact = contact;
    }

    public   AddressContact() {


    }

    public Long getAddress_contact_id() {
        return address_contact_id;
    }

    public void setAddress_contact_id(Long address_contact_id) {
        this.address_contact_id = address_contact_id;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }
}

package edu.netcracker.project.logistic.model;

public class Office {
    private Long officeId;
    private String  name;
    private String address;

    public Office(Long officeId, String name, String address) {
        this.officeId = officeId;
        this.name = name;
        this.address = address;
    }

    public Office()
    {


    }

    public Long getOfficeId() {
        return officeId;
    }

    public void setOfficeId(Long officeId) {
        this.officeId = officeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

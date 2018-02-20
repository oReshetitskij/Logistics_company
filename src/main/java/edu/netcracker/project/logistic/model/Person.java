package edu.netcracker.project.logistic.model;

import java.time.LocalDateTime;

public class Person {

    private Long id;
    private String userName;
    private String password;
    private LocalDateTime registrationDate;
    private String email;
    private Contact contact;

    public Person(String userName, String password, LocalDateTime registrationDate, String email) {
        this.userName = userName;
        this.password = password;
        this.registrationDate = registrationDate;
        this.email = email;
    }

    public Person() {

    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", registrationDate=" + registrationDate +
                ", email='" + email + '\'' +
                ", contact=" + contact +
                '}';
    }
}

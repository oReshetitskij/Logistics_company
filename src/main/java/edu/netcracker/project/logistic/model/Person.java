package edu.netcracker.project.logistic.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class Person {
    private Long id;
    private String userName;
    private String password;
    private LocalDateTime registrationDate;
    private Contact contact;

    public Person(String userName, String password, LocalDateTime registrationDate, Contact contact) {
        this.userName = userName;
        this.password = password;
        this.registrationDate = registrationDate;
        this.contact = contact;
    }

    public Person() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @NotNull
    @Size(min = 6, max = 30)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @NotNull
    @Size(min = 6, max = 30)
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

    @NotNull
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
                ", contact=" + contact +
                '}';
    }
}

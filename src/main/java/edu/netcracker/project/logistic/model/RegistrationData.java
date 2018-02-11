package edu.netcracker.project.logistic.model;

import java.util.UUID;



public class RegistrationData {
    UUID registrationDataId;
    String firstName;
    String lastName;
    String username;
    String password;
    String email;
    String phoneNumber;

    public UUID getRegistrationDataId() {
        return registrationDataId;
    }

    public void setRegistrationDataId(UUID registrationDataId) {
        this.registrationDataId = registrationDataId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}

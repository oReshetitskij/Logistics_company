package edu.netcracker.project.logistic.model;

import java.time.LocalDate;

public class User extends Person {

    private Role role;
    private Permission permission;

    public User(Long id, String firstName, String lastName, String nickName, String password, LocalDate registrationDate, String email, String phoneNumber, Role role, Permission permission) {
        super(id, firstName, lastName, nickName, password, registrationDate, email, phoneNumber);
        this.role = role;
        this.permission = permission;
    }
    public User()
    {


    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }
}

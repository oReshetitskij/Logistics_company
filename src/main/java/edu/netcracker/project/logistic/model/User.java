package edu.netcracker.project.logistic.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class User extends Person {
    private List<Role> roles = new ArrayList<>();
    private List<Permission> permissions = new ArrayList<>();

    public User(Long id, String firstName, String lastName, String nickName, String password, LocalDate registrationDate, String email, String phoneNumber, List<Role> roles, List<Permission> permissions) {
        super(id, firstName, lastName, nickName, password, registrationDate, email, phoneNumber);
        this.roles = roles;
        this.permissions = permissions;
    }
    public User()
    {


    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }
}

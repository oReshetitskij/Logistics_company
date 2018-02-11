package edu.netcracker.project.logistic.model;

import java.time.LocalDate;
import java.util.List;

public class User extends Person {
    private List<Long> role_id;
    private List<Long> permission_id;


    public User(Long id, String firstName, String lastName, String nickName, String password, LocalDate registrationDate, String email, String phoneNumber, List<Long> role_id, List<Long> permission_id) {
        super(id, firstName, lastName, nickName, password, registrationDate, email, phoneNumber);
        this.role_id = role_id;
        this.permission_id = permission_id;
    }

    public List<Long> getRole_id() {
        return role_id;
    }

    public void setRole_id(List<Long> role_id) {
        this.role_id = role_id;
    }

    public List<Long> getPermission_id() {
        return permission_id;
    }

    public void setPermission_id(List<Long> permission_id) {
        this.permission_id = permission_id;
    }


}

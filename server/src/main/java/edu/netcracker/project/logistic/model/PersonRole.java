package edu.netcracker.project.logistic.model;

import java.io.Serializable;

public class PersonRole implements Serializable {
    private Long personId;
    private Long roleId;

    public PersonRole() {
    }

    public PersonRole(Long personId, Long roleId) {
        this.personId = personId;
        this.roleId = roleId;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}

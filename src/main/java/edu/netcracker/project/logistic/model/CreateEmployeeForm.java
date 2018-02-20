package edu.netcracker.project.logistic.model;

import javax.validation.constraints.NotNull;

public class CreateEmployeeForm {
    private Person employee;
    private Long roleId;

    @NotNull
    public Person getEmployee() {
        return employee;
    }

    public void setEmployee(Person employee) {
        this.employee = employee;
    }

    @NotNull
    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}

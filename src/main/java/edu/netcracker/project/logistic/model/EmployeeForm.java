package edu.netcracker.project.logistic.model;

import java.util.List;

public class EmployeeForm {
    private Person employee;
    private List<Role> roles;

    public Person getEmployee() {
        return employee;
    }

    public void setEmployee(Person employee) {
        this.employee = employee;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}

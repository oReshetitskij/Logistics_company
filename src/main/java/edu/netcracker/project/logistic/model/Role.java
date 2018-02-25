package edu.netcracker.project.logistic.model;

import java.util.Objects;

public class Role {
    private Long roleId;
    private String roleName;
    private boolean isEmployeeRole;

    public Role() {}

    public Role(Long roleId, String roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public boolean isEmployeeRole() {
        return isEmployeeRole;
    }

    public void setEmployeeRole(boolean employeeRole) {
        isEmployeeRole = employeeRole;
    }

    @Override
    public String toString() {
        return "Role{" +
                "roleId=" + roleId +
                ", roleName='" + roleName + '\'' +
                ", isEmployeeRole=" + isEmployeeRole +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return isEmployeeRole == role.isEmployeeRole &&
                Objects.equals(roleId, role.roleId) &&
                Objects.equals(roleName, role.roleName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleId, roleName, isEmployeeRole);
    }
}

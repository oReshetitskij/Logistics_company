package edu.netcracker.project.logistic.dao;

import edu.netcracker.project.logistic.model.Person;
import edu.netcracker.project.logistic.model.PersonRole;
import edu.netcracker.project.logistic.model.Role;

import java.util.List;
import java.util.Set;

public interface PersonRoleDao extends CrudDao<PersonRole, PersonRole> {
    void deleteMany(List<PersonRole> personRoles);
    void saveMany(List<PersonRole> personRoles);
}

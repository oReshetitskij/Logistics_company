package edu.netcracker.project.logistic.service;

import edu.netcracker.project.logistic.model.Person;
import edu.netcracker.project.logistic.model.Role;

import java.util.Optional;

public interface EmployeeService {
    Person save(Person employee, Role role);
    void delete(Long id);
    Optional<Person> findOne(Long id);
    boolean contains(Long id);
}

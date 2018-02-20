package edu.netcracker.project.logistic.service;

import edu.netcracker.project.logistic.model.Person;
import edu.netcracker.project.logistic.model.Role;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    Person save(Person employee, Role role);
    void delete(Long id);
    Optional<Person> findOne(Long id);
    List<Person> findAll();
    boolean contains(Long id);
}

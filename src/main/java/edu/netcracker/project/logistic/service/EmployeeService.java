package edu.netcracker.project.logistic.service;

import edu.netcracker.project.logistic.model.Contact;
import edu.netcracker.project.logistic.model.Person;
import edu.netcracker.project.logistic.model.Role;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    Person create(Person employee, List<Long> roleIds);
    Person update(Long id, Contact contact, List<Long> roleIds);
    void delete(Long id);
    Optional<Person> findOne(Long id);
    List<Person> findAll();
    boolean contains(Long id);
}

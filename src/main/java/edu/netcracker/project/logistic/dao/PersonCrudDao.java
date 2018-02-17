package edu.netcracker.project.logistic.dao;

import edu.netcracker.project.logistic.model.Person;

import java.util.Optional;
import java.util.Set;

public interface PersonCrudDao extends CrudDao<Person, Long> {
   Optional<Person> findOne(String username);
   Set<String> findDuplicateFields(Person person);
}

package edu.netcracker.project.logistic.dao;

import edu.netcracker.project.logistic.model.Person;

import java.util.Optional;

public interface PersonCrudDao extends CrudDao<Person, Long> {
   Optional<Person> findOne(String username);
}

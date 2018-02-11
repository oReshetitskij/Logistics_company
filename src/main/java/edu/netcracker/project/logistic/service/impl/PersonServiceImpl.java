package edu.netcracker.project.logistic.service.impl;

import edu.netcracker.project.logistic.dao.PersonCrudDao;
import edu.netcracker.project.logistic.model.Person;
import edu.netcracker.project.logistic.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService {


    @Autowired
    PersonCrudDao personCrudDao;

    @Override
    public void savePerson(Person person) {

    personCrudDao.save(person);
    }

    @Override
    public void delete(Long aLong) {
        personCrudDao.delete(aLong);
    }

    @Override
    public Optional<Person> findOne(Long aLong) {
        return personCrudDao.findOne(aLong);
    }

    @Override
    public boolean exists(Long aLong) {
        return personCrudDao.contains(aLong);
    }
}

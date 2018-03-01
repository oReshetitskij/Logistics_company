package edu.netcracker.project.logistic.service.impl;

import edu.netcracker.project.logistic.dao.ContactDao;
import edu.netcracker.project.logistic.dao.PersonCrudDao;
import edu.netcracker.project.logistic.model.Contact;
import edu.netcracker.project.logistic.model.Person;
import edu.netcracker.project.logistic.service.PersonService;
import edu.netcracker.project.logistic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private PersonService personService;
    private PersonCrudDao personCrudDao;
    private ContactDao contactDao;

    @Autowired
    public UserServiceImpl(PersonService personService, PersonCrudDao personCrudDao, ContactDao contactDao) {
        this.personService = personService;
        this.personCrudDao = personCrudDao;
        this.contactDao = contactDao;
    }

    @Override
    public Person update(Person person) {

        Optional<Person> optionalPersonSaved = findOne(person.getId());
        Person personSaved = new Person();


        if (optionalPersonSaved.isPresent()){
            personSaved = optionalPersonSaved.get();
        }

        personSaved.setUserName(person.getUserName());
        Contact contact = person.getContact();
        contact.setContactId(personSaved.getContact().getContactId());
        personSaved.setContact(contact);

        personCrudDao.save(personSaved);
        contactDao.save(contact);

        return personSaved;
    }

    @Override
    public void delete(Long id) {
        personService.delete(id);
    }

    @Override
    public Optional<Person> findOne(Long id) {
        return personService.findOne(id);
    }

    @Override
    public Optional<Person> findOne(String username) {
        return personService.findOne(username);
    }
}

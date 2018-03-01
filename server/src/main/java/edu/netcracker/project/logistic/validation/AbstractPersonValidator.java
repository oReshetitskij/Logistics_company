package edu.netcracker.project.logistic.validation;

import edu.netcracker.project.logistic.dao.ContactDao;
import edu.netcracker.project.logistic.dao.PersonCrudDao;
import edu.netcracker.project.logistic.dao.RoleCrudDao;
import edu.netcracker.project.logistic.model.Contact;
import edu.netcracker.project.logistic.model.Person;
import edu.netcracker.project.logistic.model.Role;
import org.springframework.validation.Errors;

import java.util.*;
import java.util.stream.Collectors;

public abstract class AbstractPersonValidator {
    protected RoleCrudDao roleDao;
    protected PersonCrudDao personDao;
    protected ContactDao contactDao;

    public AbstractPersonValidator(RoleCrudDao roleDao, PersonCrudDao personDao, ContactDao contactDao) {
        this.roleDao = roleDao;
        this.personDao = personDao;
        this.contactDao = contactDao;
    }

    protected void checkPersonData(Person person, Errors errors) {
        Optional<Person> opt = personDao.findOne(person.getUserName());
        if (opt.isPresent() &&
                !opt.get().getId().equals(person.getId())) {
            errors.rejectValue("userName", "Already exists.");
        }
    }

    protected void checkContactData(Person employee, Errors errors) {
        Person person = personDao.findOne(employee.getId()).orElse(employee);
        employee.getContact().setContactId(person.getContact().getContactId());

        Contact contact = employee.getContact();
        List<Contact> duplicates =
                contactDao.findByPhoneNumberOrEmail(contact.getPhoneNumber(), contact.getEmail());
        for (Contact d : duplicates) {
            if (!d.getContactId().equals(contact.getContactId()) && d.getEmail().equals(contact.getEmail())) {
                errors.rejectValue("contact.email", "Already exists.");
            } else if (!d.getContactId().equals(contact.getContactId()) && d.getPhoneNumber().equals(contact.getPhoneNumber())) {
                errors.rejectValue("contact.phoneNumber", "Already exists.");
            }
        }
    }
}

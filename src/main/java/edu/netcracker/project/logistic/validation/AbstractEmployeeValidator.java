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

public abstract class AbstractEmployeeValidator {
    protected RoleCrudDao roleDao;
    protected PersonCrudDao personDao;
    protected ContactDao contactDao;

    public AbstractEmployeeValidator(RoleCrudDao roleDao, PersonCrudDao personDao, ContactDao contactDao) {
        this.roleDao = roleDao;
        this.personDao = personDao;
        this.contactDao = contactDao;
    }

    protected void checkRoleData(List<Long> roleIds, Errors errors) {
        Set<Long> roleIdsSet = new HashSet<>(roleIds);

        Set<Long> employeeRoles =
                roleDao.findEmployeeRoles()
                        .stream()
                        .map(Role::getRoleId)
                        .collect(Collectors.toSet());

        roleIdsSet.removeAll(employeeRoles);

        if (roleIdsSet.size() != 0) {
            errors.rejectValue("roleIds", "Only employee roles can be set.");
        }
    }

    protected void checkPersonData(Person person, Errors errors) {
        Optional<Person> opt = personDao.findOne(person.getUserName());
        if (opt.isPresent() &&
                !opt.get().getId().equals(person.getId())) {
            errors.rejectValue("employee.userName", "Already exists.");
        }
    }

    protected void checkContactData(Contact contact, Errors errors) {
        List<Contact> duplicates =
                contactDao.findByPhoneNumberOrEmail(contact.getPhoneNumber(), contact.getEmail());
        for (Contact d : duplicates) {
            if (!d.getContactId().equals(contact.getContactId()) && d.getEmail().equals(contact.getEmail())) {
                errors.rejectValue("employee.contact.email", "Already exists.");
            } else if (!d.getContactId().equals(contact.getContactId()) && d.getPhoneNumber().equals(contact.getPhoneNumber())) {
                errors.rejectValue("employee.contact.phoneNumber", "Already exists.");
            }
        }
    }
}

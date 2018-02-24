package edu.netcracker.project.logistic.service.impl;

import edu.netcracker.project.logistic.dao.ContactDao;
import edu.netcracker.project.logistic.dao.PersonCrudDao;
import edu.netcracker.project.logistic.dao.PersonRoleDao;
import edu.netcracker.project.logistic.dao.RoleCrudDao;
import edu.netcracker.project.logistic.exception.NonUniqueRecordException;
import edu.netcracker.project.logistic.model.Contact;
import edu.netcracker.project.logistic.model.Person;
import edu.netcracker.project.logistic.model.PersonRole;
import edu.netcracker.project.logistic.model.Role;
import edu.netcracker.project.logistic.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    private ContactDao contactDao;
    private PersonCrudDao personDao;
    private PersonRoleDao personRoleDao;
    private RoleCrudDao roleDao;

    @Autowired
    public EmployeeServiceImpl(ContactDao contactDao, PersonCrudDao personDao,
                               PersonRoleDao personRoleDao, RoleCrudDao roleDao) {
        this.contactDao = contactDao;
        this.personDao = personDao;
        this.personRoleDao = personRoleDao;
        this.roleDao = roleDao;
    }

    @Transactional(rollbackFor = {NonUniqueRecordException.class, DataIntegrityViolationException.class})
    @Override
    public Person create(Person employee, List<Long> roleIds) {
        Set<String> duplicateFields = checkContactData(employee.getContact());
        if (duplicateFields.size() != 0) {
            throw new NonUniqueRecordException(duplicateFields);
        }
        employee.setRegistrationDate(LocalDateTime.now());
        contactDao.save(employee.getContact());
        personDao.save(employee);
        contactDao.save(employee.getContact());
        updateRoles(employee, roleIds, false);
        return employee;
    }

    @Override
    public Person update(Long id, Contact contact, List<Long> roleIds) {
        Set<String> duplicateFields = checkContactData(contact);
        if (duplicateFields.size() != 0) {
            throw new NonUniqueRecordException(duplicateFields);
        }
        Optional<Person> opt = personDao.findOne(id);
        if (!opt.isPresent()) {
            throw new IllegalArgumentException(String.format("Can't find person #%s", id));
        }
        Person emp = opt.get();
        Long contactId = emp.getContact().getContactId();
        contact.setContactId(contactId);
        contactDao.save(contact);
        emp.setContact(contact);
        updateRoles(emp, roleIds, true);
        return emp;
    }

    @Transactional
    @Override
    public void delete(Long employeeId) {
        boolean hasUserRole = false;

        List<Role> roles = roleDao.getByPersonId(employeeId);
        List<PersonRole> employeeRoles = new ArrayList<>();

        for (Role r : roles) {
            if (!r.isEmployeeRole()) {
                hasUserRole = true;
                continue;
            }
            PersonRole pr = new PersonRole();
            pr.setRoleId(r.getRoleId());
            pr.setPersonId(employeeId);
            employeeRoles.add(pr);
        }

        personRoleDao.deleteMany(employeeRoles);

        if (!hasUserRole) personDao.delete(employeeId);
    }

    @Override
    public Optional<Person> findOne(Long id) {
        boolean hasEmployeeRoles = roleDao.getByPersonId(id)
                .stream()
                .anyMatch(Role::isEmployeeRole);

        if (!hasEmployeeRoles) {
            return Optional.empty();
        }

        return personDao.findOne(id);
    }

    @Override
    public List<Person> findAll() {
        return personDao.findAllEmployees();
    }

    @Override
    public boolean contains(Long id) {
        return findOne(id).isPresent();
    }

    private void updateRoles(Person employee, List<Long> roleIds, boolean previouslyRegistered) {
        List<PersonRole> currentEmployeeRoles =
                roleDao.getByPersonId(employee.getId())
                        .stream()
                        .filter(Role::isEmployeeRole)
                        .map(r -> new PersonRole(employee.getId(), r.getRoleId()))
                        .collect(Collectors.toList());

        List<PersonRole> employeeRolesToAdd =
                roleIds
                        .stream()
                        .map(roleId -> new PersonRole(employee.getId(), roleId))
                        .collect(Collectors.toList());

        try {
            if (previouslyRegistered) {
                personRoleDao.deleteMany(currentEmployeeRoles);
            }
            personRoleDao.saveMany(employeeRolesToAdd);
        } catch (DataIntegrityViolationException ex) {
            logger.error("Trying to give employee role which not exists.");
            throw ex;
        }
    }

    private Set<String> checkContactData(Contact contact) {
        Set<String> errors = new HashSet<>(2);
        List<Contact> duplicates =
                contactDao.findByPhoneNumberOrEmail(contact.getPhoneNumber(), contact.getEmail());
        for (Contact d : duplicates) {
            if (d.getEmail().equals(contact.getEmail())) {
                errors.add("employee.contact.email");
            } else if (d.getPhoneNumber().equals(contact.getPhoneNumber())) {
                errors.add("employee.contact.phoneNumber");
            }
        }
        return errors;
    }
}

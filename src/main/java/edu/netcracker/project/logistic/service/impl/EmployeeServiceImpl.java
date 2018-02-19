package edu.netcracker.project.logistic.service.impl;

import edu.netcracker.project.logistic.dao.PersonCrudDao;
import edu.netcracker.project.logistic.dao.PersonRoleDao;
import edu.netcracker.project.logistic.dao.RoleCrudDao;
import edu.netcracker.project.logistic.model.Person;
import edu.netcracker.project.logistic.model.PersonRole;
import edu.netcracker.project.logistic.model.Role;
import edu.netcracker.project.logistic.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    private PersonCrudDao personDao;
    private PersonRoleDao personRoleDao;
    private RoleCrudDao roleDao;

    @Autowired
    public EmployeeServiceImpl(PersonCrudDao personDao, PersonRoleDao personRoleDao, RoleCrudDao roleDao) {
        this.personDao = personDao;
        this.personRoleDao = personRoleDao;
        this.roleDao = roleDao;
    }

    @Transactional(rollbackFor = DataIntegrityViolationException.class)
    @Override
    public Person save(Person employee, Role role) {
        personDao.save(employee);

        PersonRole employeeRole = new PersonRole();
        employeeRole.setPersonId(employee.getId());
        employeeRole.setRoleId(role.getRoleId());

        try {
            personRoleDao.save(employeeRole);
        } catch (DataIntegrityViolationException ex) {
            logger.error("Trying to give employee  role which not exists.");
            throw ex;
        }
        return employee;
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
    public boolean contains(Long id) {
        return findOne(id).isPresent();
    }
}

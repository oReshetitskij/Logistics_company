package edu.netcracker.project.logistic.validation;

import edu.netcracker.project.logistic.dao.ContactDao;
import edu.netcracker.project.logistic.dao.PersonCrudDao;
import edu.netcracker.project.logistic.dao.RoleCrudDao;
import edu.netcracker.project.logistic.model.Person;
import edu.netcracker.project.logistic.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class RegistrationValidator extends AbstractPersonValidator {
    @Autowired
    public RegistrationValidator(PersonCrudDao personDao, RoleCrudDao roleDao, ContactDao contactDao) {
        super(roleDao, personDao, contactDao);
    }

    public void validate(Person user, Errors errors) {
        checkPersonData(user, errors);
        checkContactData(user, errors);
    }

    private void checkRoleData(Person user, Errors errors) {
        List<Role> clientRoles =  roleDao.findClientRoles();

        Set<Role> userRolesCopy = new HashSet<>(user.getRoles());
        userRolesCopy.removeAll(clientRoles);

        if (userRolesCopy.size() != 0) {
            errors.rejectValue("roles", "", "Only client roles can be set.");
        }
    }
}

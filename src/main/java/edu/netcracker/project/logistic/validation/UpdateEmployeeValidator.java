package edu.netcracker.project.logistic.validation;

import edu.netcracker.project.logistic.dao.ContactDao;
import edu.netcracker.project.logistic.dao.PersonCrudDao;
import edu.netcracker.project.logistic.dao.RoleCrudDao;
import edu.netcracker.project.logistic.model.Contact;
import edu.netcracker.project.logistic.model.Person;
import edu.netcracker.project.logistic.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.SmartValidator;

import java.util.Set;

@Component
public class UpdateEmployeeValidator extends AbstractEmployeeValidator {
    private SmartValidator validator;

    @Autowired
    public UpdateEmployeeValidator(SmartValidator validator, PersonCrudDao personDao, RoleCrudDao roleDao, ContactDao contactDao) {
        super(roleDao, personDao, contactDao);
        this.validator = validator;
    }

    public void validate(Person employee, Errors errors) {
        Set<Role> roles = employee.getRoles();
        if (roles == null || roles.size() < 1) {
            errors.rejectValue("roles", "Employee must have at least one role");
        }
        Contact contact = employee.getContact();
        validator.validate(contact, errors);
        checkContactData(employee, errors);
        checkRoleData(employee, errors);
    }
}

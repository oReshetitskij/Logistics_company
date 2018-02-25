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
public class CreateEmployeeValidator extends AbstractEmployeeValidator {
    private SmartValidator validator;

    @Autowired
    public CreateEmployeeValidator(SmartValidator validator, RoleCrudDao roleDao, PersonCrudDao personDao, ContactDao contactDao) {
        super(roleDao, personDao, contactDao);
        this.validator = validator;
    }

    public void validate(Person employee, Errors errors) {
        validator.validate(employee, errors);

        Contact contact = employee.getContact();
        validator.validate(contact, errors);

        Set<Role> roles = employee.getRoles();
        if (roles == null || roles.size() < 1) {
            errors.rejectValue("roles", "", "At least one role must be selected");
        }
        checkPersonData(employee, errors);
        checkContactData(employee, errors);
        checkRoleData(employee, errors);
    }
}

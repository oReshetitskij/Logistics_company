package edu.netcracker.project.logistic.validation;

import edu.netcracker.project.logistic.dao.ContactDao;
import edu.netcracker.project.logistic.dao.PersonCrudDao;
import edu.netcracker.project.logistic.dao.RoleCrudDao;
import edu.netcracker.project.logistic.model.Contact;
import edu.netcracker.project.logistic.model.EmployeeForm;
import edu.netcracker.project.logistic.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.SmartValidator;

import java.util.List;

@Component
public class UpdateEmployeeValidator extends AbstractEmployeeValidator {
    private SmartValidator validator;

    @Autowired
    public UpdateEmployeeValidator(SmartValidator validator, PersonCrudDao personDao, RoleCrudDao roleDao, ContactDao contactDao) {
        super(roleDao, personDao, contactDao);
        this.validator = validator;
    }

    public void validate(EmployeeForm employeeForm, Errors errors) {
        List<Long> roleIds = employeeForm.getRoleIds();
        if (roleIds == null || roleIds.size() < 1) {
            errors.rejectValue("roleIds", "Employee must have at least one role");
        }
        Person emp = employeeForm.getEmployee();
        if (emp == null) {
            errors.reject("Employee can't be null");
            return;
        }
        Contact contact = emp.getContact();
        validator.validate(contact, errors);
        checkContactData(contact, errors);
        checkRoleData(roleIds, errors);
    }
}

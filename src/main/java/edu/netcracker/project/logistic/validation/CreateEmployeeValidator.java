package edu.netcracker.project.logistic.validation;

import edu.netcracker.project.logistic.model.Contact;
import edu.netcracker.project.logistic.model.EmployeeForm;
import edu.netcracker.project.logistic.model.Person;
import edu.netcracker.project.logistic.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.SmartValidator;
import org.springframework.validation.Validator;

import java.util.List;

@Component
public class CreateEmployeeValidator {
    private SmartValidator validator;

    @Autowired
    public CreateEmployeeValidator(SmartValidator validator) {
        this.validator = validator;
    }

    public void validate(EmployeeForm form, Errors errors) {
        Person emp = form.getEmployee();
        validator.validate(emp, errors);

        Contact contact = emp.getContact();
        validator.validate(contact, errors);

        List<Long> roleIds = form.getRoleIds();
        if (roleIds == null) {
            errors.reject("roleIds can't be null");
        } else if (roleIds.size() < 1) {
            errors.rejectValue("roleIds", "Must contain at least one role");
        }
    }
}

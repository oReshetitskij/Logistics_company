package edu.netcracker.project.logistic.validation;

import edu.netcracker.project.logistic.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
public class UpdateUserValidator implements Validator {

    private PersonValidator personValidator;

    @Autowired
    public UpdateUserValidator(PersonValidator personValidator) {
        this.personValidator = personValidator;
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;
        personValidator.validate(person, errors);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

}

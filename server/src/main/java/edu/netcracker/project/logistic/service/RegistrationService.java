package edu.netcracker.project.logistic.service;

import edu.netcracker.project.logistic.exception.NonUniqueRecordException;
import edu.netcracker.project.logistic.model.Person;

import javax.mail.MessagingException;
import java.util.UUID;

public interface RegistrationService {
    void register(Person user) throws MessagingException, NonUniqueRecordException;

    void confirmAccount(UUID linkId);
}

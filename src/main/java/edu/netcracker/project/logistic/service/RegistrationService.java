package edu.netcracker.project.logistic.service;

import edu.netcracker.project.logistic.exception.NonUniqueRecordException;
import edu.netcracker.project.logistic.model.RegistrationForm;

import javax.mail.MessagingException;
import java.util.UUID;

public interface RegistrationService {
    void register(RegistrationForm form) throws MessagingException, NonUniqueRecordException;
    void confirmAccount(UUID linkId);
}

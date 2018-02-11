package edu.netcracker.project.logistic.service.impl;

import edu.netcracker.project.logistic.dao.RegistrationDataDao;
import edu.netcracker.project.logistic.model.RegistrationData;
import edu.netcracker.project.logistic.model.RegistrationForm;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RegistrationService {
    private RegistrationDataDao dao;

    public RegistrationService(RegistrationDataDao dao) {
        this.dao = dao;
    }

    public RegistrationData register(RegistrationForm form) {
        RegistrationData regData = fromForm(form);
        UUID id = generateId();
        regData.setRegistrationDataId(id);
        dao.save(regData);
        return regData;
    }

    private RegistrationData fromForm(RegistrationForm form) {
        RegistrationData data = new RegistrationData();
        data.setFirstName(form.getFirstName());
        data.setLastName(form.getLastName());
        data.setUsername(form.getUsername());
        data.setPassword(form.getPassword());
        data.setPhoneNumber(form.getPhoneNumber());
        data.setEmail(form.getEmail());

        return data;
    }

    private UUID generateId() {
        UUID id = UUID.randomUUID();
        while (dao.contains(id)) {
            id = UUID.randomUUID();
        }
        return id;
    }
}

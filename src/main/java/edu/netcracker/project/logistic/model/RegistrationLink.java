package edu.netcracker.project.logistic.model;

import java.util.UUID;

public class RegistrationLink {
    private UUID registrationLinkId;
    private Long personId;

    public UUID getRegistrationLinkId() {
        return registrationLinkId;
    }

    public void setRegistrationLinkId(UUID registrationLinkId) {
        this.registrationLinkId = registrationLinkId;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }
}

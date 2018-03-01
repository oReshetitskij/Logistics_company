package edu.netcracker.project.logistic.dao;

import edu.netcracker.project.logistic.model.Contact;

import java.util.List;

public interface ContactDao extends CrudDao<Contact, Long> {
    List<Contact> findByPhoneNumberOrEmail(String phoneNumber, String email);
}

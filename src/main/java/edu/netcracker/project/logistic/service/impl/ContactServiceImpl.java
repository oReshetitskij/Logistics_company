package edu.netcracker.project.logistic.service.impl;

import edu.netcracker.project.logistic.dao.ContactCrudDao;
import edu.netcracker.project.logistic.model.Contact;
import edu.netcracker.project.logistic.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ContactServiceImpl implements ContactService {

    private   ContactCrudDao contactCrudDao;

    @Autowired
    public ContactServiceImpl(ContactCrudDao contactCrudDao) {
        this.contactCrudDao = contactCrudDao;
    }

    @Override
    public void saveContact(Contact contact) {
        contactCrudDao.save(contact);
    }

    @Override
    public void delete(Long aLong) {
          contactCrudDao.delete(aLong);
    }

    @Override
    public Optional<Contact> findOne(Long aLong) {
        return contactCrudDao.findOne(aLong);
    }

    @Override
    public boolean exists(Long aLong) {
        return contactCrudDao.contains(aLong);
    }
}

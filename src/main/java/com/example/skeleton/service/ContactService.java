package com.example.skeleton.service;

import com.example.skeleton.api.IContactService;
import com.example.skeleton.model.dao.ContactDao;
import com.example.skeleton.model.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("ContactService")
@Lazy
public class ContactService implements IContactService {

    @Autowired
    private ContactDao contactDao;

    @Override
    public Optional<Contact> queryContact(Contact contact) {
        return contactDao.findById(contact.getId());
    }

    @Override
    public List<Contact> queryAllContact() {
       return contactDao.findAll();
    }

    @Override
    public int insertContact(Contact contact) {
        contactDao.saveAndFlush(contact);
        return contact.getId();
    }

    @Override
    public int updateContact(Contact contact) {
        contactDao.saveAndFlush(contact);
        return contact.getId();
    }

    @Override
    public int deleteContact(Contact contact) {
        int id = contact.getId();
        contactDao.delete(contact);
        return id;
    }
}

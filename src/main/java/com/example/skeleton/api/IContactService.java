package com.example.skeleton.api;

import com.example.skeleton.model.Contact;

import java.util.List;
import java.util.Optional;


public interface IContactService {

    //CRUD Operations
    Optional<Contact> queryContact(Contact contact);
    List<Contact> queryAllContact();
    int insertContact(Contact contact);
    int updateContact(Contact contact);
    int deleteContact(Contact contact);
}

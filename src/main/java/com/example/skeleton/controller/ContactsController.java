package com.example.skeleton.controller;

import com.example.skeleton.api.IContactService;
import com.example.skeleton.model.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/contacts")
public class ContactsController {

    @Autowired
    private IContactService contactService;

    @RequestMapping(value = "/testController", method = RequestMethod.GET)
    public String testContactsController() {
        return "Contacts controller works!";
    }

    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public Optional<Contact> queryContact(@RequestBody Contact contact) {
        return contactService.queryContact(contact);
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public List<Contact> queryAllContact() {
        return contactService.queryAllContact();
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public int addContact(@RequestBody Contact contact) {
        return contactService.insertContact(contact);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public int updateContact(@RequestBody Contact contact) {
        return contactService.updateContact(contact);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public int deleteContact(@RequestBody Contact contact) {
        return contactService.deleteContact(contact);
    }
}

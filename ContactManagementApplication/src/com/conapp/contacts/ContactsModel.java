package com.conapp.contacts;

import java.util.List;

import com.conapp.contacts.dto.Contact;
import com.conapp.contacts.repository.ContactsRepository;

public class ContactsModel implements ContactsControllerToModel{
	private ContactsModelToController contactsController;
    private ContactsRepository repo;

    ContactsModel(ContactsModelToController contactsController)
    {
        this.contactsController = contactsController;
        repo = ContactsRepository.getInstance();
    }

    @Override
    public List<Contact> viewContacts() {
        
        return repo.getContacts();
    }

    @Override
    public Contact openContact(String name, char type) {
        return repo.getContact(name, type);
    }

    @Override
    public boolean addContact(String name, String number) {
        return repo.addContact(name, number);
    }

    @Override
    public boolean editContact(String name, String field, String value) {
        return repo.editContact(name, field, value);
    }

    @Override
    public boolean deleteContact(String name) {
        return repo.deleteContact(name);
    }

}

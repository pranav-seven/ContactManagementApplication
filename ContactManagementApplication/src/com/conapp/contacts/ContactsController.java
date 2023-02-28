package com.conapp.contacts;

import java.util.List;
import java.util.regex.Pattern;

import com.conapp.contacts.dto.Contact;

public class ContactsController implements ContactsModelToController, ContactsViewToController{

	private ContactsControllerToModel contactsModel;
	private ContactsControllerToView contactsView;

    ContactsController(ContactsControllerToView contactsView)
    {
        this.contactsView = contactsView;
        contactsModel = new ContactsModel(this);
    }

    @Override
    public List<Contact> viewContacts()
    {
        return contactsModel.viewContacts();
    }

    @Override
    public Contact openContact(String name, char type) {
        return contactsModel.openContact(name, type);
    }

    @Override
    public boolean addContact(String name, String number) {
        return contactsModel.addContact(name, number);
    }

    @Override
    public boolean editContact(String name, String field, String value) {
        return contactsModel.editContact(name, field, value);
    }

    @Override
    public boolean deleteContact(String name) {
        return contactsModel.deleteContact(name);
    }

    @Override
    public boolean isValidNumber(String number) {
        return Pattern.matches("[0-9]+", number);
    }
}

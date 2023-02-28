package com.conapp.contacts;

import java.util.List;

import com.conapp.contacts.dto.Contact;

public interface ContactsViewToController {

    List<Contact> viewContacts();

    Contact openContact(String name, char c);

    boolean addContact(String name, String number);

    boolean editContact(String name, String field, String value);

    boolean deleteContact(String name);

    boolean isValidNumber(String number);

}

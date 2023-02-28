package com.conapp.contacts.dto;

public class Contact implements Comparable<Contact>{
    private String name;
    private String number;

    public Contact(String name, String number)
    {
        this.name = name;
        this.number = number;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
    
    public void setNumber(String number)
    {
        this.number = number;
    }

    public String getNumber()
    {
        return number;
    }

    public String toString()
    {
        return "Name: "+name+"\nPhone number: "+number;
    }

    @Override
    public int compareTo(Contact contact) {
        return name.compareTo(contact.getName());
    }
}

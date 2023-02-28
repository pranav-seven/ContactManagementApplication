package com.conapp.contacts.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.conapp.contacts.dto.Contact;

public class ContactsRepository {
    private static ContactsRepository repository;
	private Statement statement;
	private Connection connection;
    private List<Contact> contacts;
    private ResultSet results;

    private ContactsRepository()
    {
        contacts = new ArrayList<>();
        statement = null;
        connection = null;
    }

    public static ContactsRepository getInstance()
    {
    	try {
            if(repository==null)
            {
                repository = new ContactsRepository();
                repository.executeConnection();
            }
    	}catch(Exception e)
    	{
    		e.printStackTrace();
    	}
        return repository;
    }

	private void executeConnection() throws Exception {
		String url = "jdbc:mysql://localhost:3306/contacts";
		String username = "root";
		String password = "Vikram@22";
		connection = DriverManager.getConnection(url, username, password);
		statement = connection.createStatement();
	}

    public List<Contact> getContacts() {
        contacts.clear();
        try{
            results = statement.executeQuery("SELECT * FROM CONTACTS ORDER BY NAME");
            while(results.next())
                contacts.add(new Contact(results.getString("name"), results.getString("number")));
        }catch(SQLException sql)
        {
            sql.printStackTrace();
        }
        return contacts;
    }

    public Contact getContact(String value, char type) {
        Contact contact = null;
        try{
            if(type=='n')
                results = statement.executeQuery("SELECT * FROM contacts WHERE name='"+value+"'");
            else
                results = statement.executeQuery("SELECT * FROM contacts WHERE number='"+value+"'");
            if(results.next())
                contact = new Contact(results.getString("name"), results.getString("number"));
        }catch(SQLException sql)
        {
            sql.printStackTrace();
        }
        return contact;
    }

    public boolean addContact(String name, String number) {
        boolean exists = true;
        try{
            statement.executeUpdate("INSERT INTO CONTACTS VALUES ('"+name+"', '"+number+"')");
        }catch(SQLIntegrityConstraintViolationException sql)
        {
            exists = false;
        }catch(SQLException sql)
        {
            sql.printStackTrace();
        }
        return exists;
    }

    public boolean deleteContact(String name) {
        // int index = -1;
        boolean exists = false;
        // for(Contact temp: contacts)
        // {
        //     index++;
        //     if(temp.getName().contentEquals(name))
        //     {
        //         exists = true;
        //         break;
        //     }
        // }
        // if(exists)
        //     contacts.remove(index);
        try{
            results = statement.executeQuery("SELECT NAME FROM CONTACTS WHERE name='"+name+"'");
            if(results.next())
            {
                exists = true;
                statement.executeUpdate("DELETE FROM contacts WHERE name='"+name+"'");
            }
        }catch(SQLException sql)
        {
            sql.printStackTrace();
        }
        return exists;
    }

    public boolean editContact(String name, String field, String value) {
        boolean exists = false;
        try{
            results = statement.executeQuery("SELECT * FROM CONTACTS WHERE name='"+name+"'");
            if(results.next())
            {
                System.out.println("results.next=true");
                exists = true;
                if(field.contentEquals("name"))
                {
                    statement.executeUpdate("UPDATE contacts SET name ='"+value+"' WHERE name='"+name+"'");
                    System.out.println("+++namechanged");
                }
                else if(field.contentEquals("number"))
                {
                    statement.executeUpdate("UPDATE contacts SET number ='"+value+"' WHERE name='"+name+"'");
                    System.out.println("+++numberchanged");
                }
            }
        }
        catch(SQLIntegrityConstraintViolationException sql)
        {
            exists = false;
        }
        catch(SQLException sql)
        {
            sql.printStackTrace();
        }
        return exists;
    }


}

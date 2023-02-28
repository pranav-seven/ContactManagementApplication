package com.conapp.contacts;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import com.conapp.contacts.dto.Contact;

public class ContactsView implements ContactsControllerToView{
	private ContactsViewToController contactsController;
	private BufferedReader reader;
	
	ContactsView()
	{
		contactsController = new ContactsController(this);
		System.out.println("Contacts");
		reader = new BufferedReader(new InputStreamReader(System.in));
		openContacts();
	}

	public static void main(String[] args) {
		new ContactsView();
	}

	void openContacts()
	{
		char choice = 0;
		do{
			System.out.println("----------------------");
			System.out.println("1 - View contacts");
			System.out.println("2 - Open a contact");
			System.out.println("3 - Add contact");
			System.out.println("4 - Edit contact");
			System.out.println("5 - Delete contacts");
			System.out.println("0 - Exit");
			try{
				choice = reader.readLine().charAt(0);
			}catch(IOException io)
			{
				io.printStackTrace();
			}
			switch(choice)
			{
				case '1':	viewContacts();
							break;
				case '2':	openContact();
							break;
				case '3':	addContact();
							break;
				case '4':	editContact();
							break;
				case '5':	deleteContact();
							break;
				case '0':	break;
				default :	System.out.println("Invalid choice!");
			}
		}while(choice!='0');
	}

	private void viewContacts() {
		List<Contact> contacts = contactsController.viewContacts();
		System.out.println("----------------------");
		if(contacts.isEmpty())
			System.out.println("Contacts empty");
		else
		{
			// System.out.println(String.format("%-26s%-15s", "Name", "Phone no."));
			char c = 0;
			for(Contact contact : contacts)
			{
				String name = contact.getName();
				if(c!=name.charAt(0))
				{
					c=name.charAt(0);
					if(Character.isLowerCase(c))
						c = (char)(c - 'a' + 'A');
					System.out.println(c);
				}
				System.out.println(contact.getName());
			}
		}
	}

	private void openContact() {
		char choice = 0;
		do{
			System.out.println("-----------------");
			System.out.println("Search by name or number? (N/C): ");
			System.out.println("1 - Name");
			System.out.println("2 - Number");
			System.out.println("0 - Back");
			Contact contact = null;
			try{
				choice = reader.readLine().charAt(0);
				switch(choice)
				{
					case '1':	System.out.println("Enter name: ");
								String name = reader.readLine();
								System.out.println("-----------------");
								contact = contactsController.openContact(name, 'n');	//pass 'n' for name, 'N' for number
								break;
					case '2':	System.out.println("Enter number: ");
								String number = reader.readLine();
								System.out.println("-----------------");
								contact = contactsController.openContact(number, 'N');
								break;
					case '0':	return;
					default :	System.out.println("Invalid choice!");
				}
			}catch(IOException io)
			{
				io.printStackTrace();
			}
			if(contact == null)
			{
				System.out.println("Contact unavailable");

			}
			else
				System.out.println(contact);
		}while(choice!='0');
	}
	
	private void addContact() {
		boolean exists = true;
		try{
			System.out.print("Enter name ('0' to go back): ");
			String name = reader.readLine();
			if(!name.contentEquals("0")){
				String number = getNumber();
				if(!number.contentEquals("0"))
					exists = contactsController.addContact(name, number);
			}
		}catch(IOException io)
		{
			io.printStackTrace();
		}
		if(exists)
			System.out.println("Contact added.");
		else
			System.out.println("Contact with same name already exists.");
	}

	String getNumber() throws IOException
	{
		System.out.print("Enter number ('0' to go back): ");
		String number = reader.readLine();
		if(!number.contentEquals("0") &&!contactsController.isValidNumber(number))
		{
			System.err.println("Enter valid number");
		}
		return number;
	}

	//yet to be implemented
	private void editContact() {
		String name = "";
		System.out.print("Enter name ('0' to go back): ");
		try{
			name = reader.readLine();
		}catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
		if(!name.contentEquals("0")){
			Contact contact = contactsController.openContact(name, 'n');
			if(contact==null)
			{
				System.out.println("Contact unavailable.");
				return;
			}
			System.out.println(contact);
			char choice = 0;
			System.out.println("Enter field to edit: ");
			System.out.println("1 - Name");
			System.out.println("2 - Number");
			System.out.println("0 - Exit");
			try{
				choice = reader.readLine().charAt(0);
				switch(choice)
				{
					case '1':	System.out.print("Enter new name: ");
								String newName = reader.readLine();
								if(!contactsController.editContact(name, "name", newName))
									System.out.println("Name already exists!");
								else
									System.out.println("Updated successfully.");
								break;
					case '2':	String newNumber = getNumber();
								if(!newNumber.contentEquals("0"))
								{
									contactsController.editContact(name, "number", newNumber);
									System.out.println("Updated successfully.");
								}
								break;
					case '0':	break;
					default :	System.out.println("Invalid choice!");
				}
			}catch(IOException ioe)
			{
				ioe.printStackTrace();
			}
		}
	}

	private void deleteContact() {
		String name = "";
		System.out.print("Enter name: ");
		try{
			name = reader.readLine();
		}catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
		if(contactsController.deleteContact(name))
			System.out.println("Contact deleted.");
		else
			System.out.println("Contact unavailable.");
	}
}

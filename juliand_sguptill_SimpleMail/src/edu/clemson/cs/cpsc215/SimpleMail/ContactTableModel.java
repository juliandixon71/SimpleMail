package edu.clemson.cs.cpsc215.SimpleMail;

/**
 * 
 * @author Julian Dixon and Shane Guptil
 * @since 4/27/2014
 * @version 1.0
 * 
 * Contact Table Model, subclasses AbstractTabelModel to provide a customized
 * table suitable for displaying information about saved Contacts
 *
 */
import java.util.*;

import javax.swing.table.*;

// Manages the contact table's data
@SuppressWarnings("serial")
public class ContactTableModel extends AbstractTableModel {
	     
	// Names of the columns in the table
	private static final String[] columnName = {"Name", "Email", "Address", "Phone Number"};
	     
	// List of contacts in the table
	private ArrayList<Contact> contacts = new ArrayList<Contact>();
	     
	// Set the list of contacts in the table
	public void setContact(ArrayList<Contact> savedContact) {
		for (int i = 0;i < savedContact.size(); i++) {
			contacts.add(savedContact.get(i));
	    }     
	    // Fire table data change notification to table
	    fireTableDataChanged();
	}
	     
	// Get contact for specified row
	public Contact getContact(int row) {
		return contacts.get(row);
	}
	     
	// Delete contact from the list
	public void deleteContact(int row) {
		contacts.remove(row);
	    // Fire table row deletion notification to table
	    fireTableRowsDeleted(row, row);
	}
	     
	// Get the amount of columns in the table
	public int getColumnCount() {
		return columnName.length;
	}
	     
	// Get a column's name
	public String getColumnName(int col) {
		return columnName[col];
	}
	     
	// Get the table's row count
	public int getRowCount() {
		return contacts.size();
	}
	     
	// Get the contact list data
	public ArrayList<Contact> retrieveContacts() {
		return contacts;
	}
	     
	// Get the value for specific column and row combinations
	public Object getValueAt(int row, int col) {
			
		Contact contact = contacts.get(row);
		String ret = null;
		try {
			switch (col) {
	       		case 0: 
	        		ret = contact.getName();
	        		break;
	        	case 1:
	        		ret = contact.getEmail();
	        		break;
	        	case 2:
	        		ret = contact.getAddress();
	        		break;
	        	case 3:
	        		ret = contact.getPhone();
	        		break;
	        	default:
	        		break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
		
	}
}

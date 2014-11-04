package edu.clemson.cs.cpsc215.SimpleMail;

/**
 * 
 * @author Julian Dixon and Shane Guptil
 * @since 4/27/2014
 * @version 1.0
 * 
 * DataStore class, implements a singleton pattern, Stores information
 * about contacts and configuration. Also provides functionality
 * for saving them to disk with the serializable interface
 *
 */
import java.io.*;
import java.util.*;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class DataStore {
	
	// Singleton class that saves data configuration and contacts to the disk
	private static final DataStore instance = new DataStore();
	private ArrayList<Contact> contacts = new ArrayList<Contact>();
	private Configuration config = new Configuration();
	private Authenticator authenticator = null;
	
	public static DataStore getInstance() {
		return instance;
	}
	
	private DataStore() {
	}
	
	public void loadConfigData() throws Exception {
		FileInputStream inFile = new FileInputStream("config.txt");
        ObjectInputStream input = new ObjectInputStream(inFile);
        config = (Configuration) input.readObject();
        input.close();
        inFile.close();
	}
	
	public Configuration getConfigData() {
		return config;
	}
	
	public void storeConfigData(Configuration input) throws Exception {
		FileOutputStream outFile = new FileOutputStream("config.txt");
        ObjectOutputStream output = new ObjectOutputStream(outFile);
        output.writeObject(input);
        output.close();
        outFile.close();
	}
	
	@SuppressWarnings("unchecked")
	public void loadContactData() throws Exception {
		FileInputStream inFile2 = new FileInputStream("contacts.txt");
        ObjectInputStream input = new ObjectInputStream(inFile2);
        contacts = (ArrayList<Contact>) input.readObject();
        input.close();
        inFile2.close();
	}
	
	public ArrayList<Contact> getContactData() {
		return contacts;
	}
	
	public void storeContactData(ArrayList<Contact> input) throws Exception {
		FileOutputStream outFile2 = new FileOutputStream("contacts.txt");
        ObjectOutputStream output = new ObjectOutputStream(outFile2);
        output.writeObject(input);
        output.close();
        outFile2.close();
	}
	
	public Authenticator getAuthenticator() {
		return authenticator;
	}
	
	public void setAuthenticator(final String user, final String password) {
        authenticator = new Authenticator () {
			public PasswordAuthentication getPasswordAuthentication(){
				return new PasswordAuthentication(user, password);
			}
		};
	}
	
	// Get user's email
    public String getUser(String input) {
		JPanel panel = new JPanel();
		JLabel label = new JLabel("Enter your Email:");
		JTextField user = new JTextField(30);
		user.setText(input);
		panel.add(label);
		panel.add(user);
		String[] options = new String[]{"OK", "Cancel"};
		int option = JOptionPane.showOptionDialog(null, panel, "Enter your Email", JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[1]);
		if(option == 0) {
		    config.setUserEmail(user.getText());
		    return new String(user.getText());
		}	
		return null;
    }
     
    // Get email password
 	public String getPassword() {
		JPanel panel = new JPanel();
		JLabel label = new JLabel("Enter your Password:");
		JPasswordField password = new JPasswordField(30);
		panel.add(label);
		panel.add(password);
		String[] options = new String[]{"OK", "Cancel"};
		int option = JOptionPane.showOptionDialog(null, panel, "Enter your Password", JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[1]);
		if(option == 0) {
		    return new String(password.getPassword());
		}
		return null;
 	}
}

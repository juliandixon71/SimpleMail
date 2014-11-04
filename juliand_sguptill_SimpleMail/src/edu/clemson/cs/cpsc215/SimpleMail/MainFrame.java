package edu.clemson.cs.cpsc215.SimpleMail;

/**
 * 
 * @author Julian Dixon and Shane Guptil
 * @since 4/27/2014
 * @version 1.0
 * 
 * MainFrame class. This is the primary window for the application, it contains
 * the contacts table, the message table, buttons for adding editing and deleting contacts,
 * buttons for composing messages and refreshing the inbox, and the menu bar.
 * Most of the functionality for connecting, sending, and receiving emails is contained in
 * this class.
 *
 */

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.net.InetAddress;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {
	
	public String output;
	// DataStore
	DataStore dataStore = DataStore.getInstance();
	
	// our session, and store
	private Session session;
	private Store store;
		
	// Connection information
	private Configuration connectionConfig = new Configuration();
	
	// password
	private String password;
		
	// Contact table models
    private ContactTableModel tableModel;
    private MessageTableModel msgModel;
	     
    // Tables with contacts and messages
	private JTable contacts, messages;
	     
	// Panel that holds the message view panel and the messages table
	private JSplitPane splitPane;
	     
	// Currently selected contact within the table
	private Contact selectedContact;
	public ArrayList<Contact> editedContact;
	     
	// Flag for whether a contact is being deleted
	private boolean delete;
	     
	// Buttons for managing a selected contact
	private JButton deleteButton, addButton, editButton;
	     
	// Email client constructor
	public MainFrame() {
		try {
			dataStore.loadContactData();
			dataStore.loadConfigData();
		} catch (Exception e1) {
			showError("Data cannot be loaded", false);
		}
	    	 
		// Set title of the Email client
		setTitle("Simple Mail");
	         
		// Set the window size
		setSize(640, 480);
	         
		// Closing the window
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				actionExit();
			}
		});
	         
		// Create the "File" drop down menu
		JMenuBar menu = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		JMenuItem exitFileMenu = new JMenuItem("Exit", KeyEvent.VK_X);
	
		exitFileMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionExit();
	    	}
		});
	
		fileMenu.add(exitFileMenu);
		menu.add(fileMenu);
		setJMenuBar(menu);
	         
		// Create the "Configuration" drop down menu
		JMenu config = new JMenu("Configuration");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		JMenuItem exitConfigMenu = new JMenuItem("Options", KeyEvent.VK_X);
	
		exitConfigMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionConfigDlg();
	    	}
		});
	
		config.add(exitConfigMenu);
		menu.add(config);
		setJMenuBar(menu);
	         
		// Create the "Help" drop down menu
		JMenu help = new JMenu("Help");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		JMenuItem exitHelpMenu = new JMenuItem("About", KeyEvent.VK_X);
	
		exitHelpMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionSystemInfoDlg();
	    	}
		});	
	
		help.add(exitHelpMenu);
		menu.add(help);
		setJMenuBar(menu);
	         
		// Create the buttons panel
		JPanel buttonPanel = new JPanel();
		JButton button = new JButton("Compose Message");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Contact tempContact = new Contact();
	        	actionEmailTransDlg(tempContact);
			}
		});
		buttonPanel.add(button);
		
		JButton refresh = new JButton("Refresh Mail");
		refresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionRefresh();
			}
		});
		buttonPanel.add(refresh);
	         
		// Create contact table
		tableModel = new ContactTableModel();
		tableModel.setContact(dataStore.getContactData());
		contacts = new JTable(tableModel);
	 	contacts.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
		 	public void valueChanged(ListSelectionEvent e) {
			 	tableSelectionChanged();
		 	}
	 	});   
	 
	 	// Add mouse listener to contact table
	 	contacts.addMouseListener(new MouseAdapter() {
		 	public void mouseClicked(MouseEvent e) {
			 	if (e.getClickCount() == 2) {
				 	Contact selected = tableModel.getContact(contacts.getSelectedRow());
				 	actionEmailTransDlg(selected);
			 	}
		 	}
	 	});
	         
	 	// Allow for one row to be selected at a time
	 	contacts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	 	
	 	// create the message table
	 	msgModel = new MessageTableModel();
	 	messages = new JTable(msgModel);
	 	messages.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
		 	public void valueChanged(ListSelectionEvent e) {
			 	tableSelectionChanged();
		 	}
	 	});   
	 
	 	// Allow for one row to be selected at a time
	 	messages.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	 	
	 // Add mouse listener to message table
	 	 messages.addMouseListener(new MouseAdapter() {
	 	 	public void mouseClicked(MouseEvent e) {
			 	if (e.getClickCount() == 2) {
	 			 	Message selected = msgModel.getMsg(messages.getSelectedRow());
	 			 	actionViewMessageDlg(selected);	
	 		 	}
	 		}
	 	 });
	         
	 	// Create contacts panel
	 	JPanel contactPanel = new JPanel();
	 	contactPanel.setBorder(BorderFactory.createTitledBorder("Contacts"));
	 	contactPanel.setLayout(new BorderLayout());
	 	contactPanel.add(contacts, BorderLayout.CENTER);
	 	
	 	// create the inbox panel
	 	JPanel inboxPanel = new JPanel();
	 	inboxPanel.setBorder(BorderFactory.createTitledBorder("Inbox"));

	 	inboxPanel.setLayout(new BorderLayout());
	 	inboxPanel.add(messages, BorderLayout.CENTER);

	 	// now create the full panel with inbox and contacts
	 	splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, contactPanel, inboxPanel);
	 	JPanel fullPanel = new JPanel();
	 	fullPanel.setLayout(new BorderLayout());
	 	fullPanel.add(splitPane, BorderLayout.CENTER);
	 	
	 	
	         
	 	// Create a second button panel
	 	JPanel buttonPanel2 = new JPanel();
	 
	 	// Create "Add" button
	 	addButton = new JButton("Add");
	 	addButton.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
			 	actionAdd();
	     	}
	 	});
	 	addButton.setEnabled(true);
	 	buttonPanel2.add(addButton);
	  
	 	// Create "Edit" button
	 	editButton = new JButton("Edit");
	 	editButton.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
			 	int i = contacts.getSelectedRow();
	         	selectedContact = tableModel.getContact(i);
	         	actionEdit(selectedContact, i);
		 	}
	 	});
	 	editButton.setEnabled(false);
		buttonPanel2.add(editButton);
	      
		// Create "Delete" button
		deleteButton = new JButton("Delete");
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionDelete();
			}
		});
		deleteButton.setEnabled(false);
		buttonPanel2.add(deleteButton);
	
		// Add the panels to the display
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(buttonPanel2, BorderLayout.NORTH);
		getContentPane().add(fullPanel, BorderLayout.CENTER);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
	}
	     
	// Display application window on the screen
	@SuppressWarnings("deprecation")
	public void show() {
		super.show();
	    // Divide the panel in half
	    splitPane.setDividerLocation(.5);
	}
	     
	// Connect to the server
	public void connect() {
		connectionConfig = dataStore.getConfigData();
		try {
			Properties props = new Properties();
			props.put("mail.transport.protocol", "smtp");
			props.put("mail.smtp.host", dataStore.getConfigData().getUserAddress());
			connectionConfig.setUserAddress(dataStore.getConfigData().getUserAddress());
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.port", dataStore.getConfigData().getUserPort());
			props.put("mail.smtp.socketFactory.port", dataStore.getConfigData().getUserPort());
			connectionConfig.setUserPort(dataStore.getConfigData().getUserPort());
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.auth", "true");	
			props.put("mail.host", connectionConfig.getPop3Host());
			props.put("mail.store.protocol", "pop3");
			props.put("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.put("mail.pop3.socketFactory.port", "995");
			props.put("mail.pop3.auth" , "true");
			props.put("mail.pop3.port", "995");
			if(dataStore.getAuthenticator() == null) {
				dataStore.setAuthenticator(getUser(dataStore.getConfigData().getUserEmail()), getPassword());
				session = Session.getDefaultInstance(props, dataStore.getAuthenticator());
			}			
			InetAddress ip =InetAddress.getLocalHost();
			connectionConfig.setUserIP(ip.getHostAddress());
			session = Session.getDefaultInstance(props, dataStore.getAuthenticator());
			store = session.getStore("pop3");
			store.connect(connectionConfig.getUserEmail(), password);
			
		} catch (Exception e) {
			showError("Cannot connect!", false);
			e.printStackTrace();
		}
	}   
	
	// Get user email
	public String getUser(String input) {
		JPanel panel = new JPanel();
		JLabel label = new JLabel("Enter Email:");
		JTextField user = new JTextField(30); 
		user.setText(input);
		panel.add(label);
		panel.add(user);
		String[] options = new String[]{"OK", "Cancel"};
		int option = JOptionPane.showOptionDialog(null, panel, "Enter your email", JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,  null, options, options[1]);
		if(option == 0) {
			connectionConfig.setUserEmail(user.getText());
			return new String(user.getText());
		}
		return null;
	}
	    
	 // Get email password
	public String getPassword() {
		JPanel panel = new JPanel();
		JLabel label = new JLabel("Enter your password:");
		JPasswordField password = new JPasswordField(30);
		panel.add(label);
		panel.add(password);
		String[] options = new String[]{"OK", "Cancel"};
		int option = JOptionPane.showOptionDialog(null, panel, "Enter your password", JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[1]);
		if(option == 0 && password.getPassword().length > 0) {
			    this.password = new String(password.getPassword());
			    return this.password;
		}
		showError("Password not entered", false);
		return null;
	}
		
	     
	// Exit the Program
	private void actionExit() {
		try {
			dataStore.storeContactData(tableModel.retrieveContacts());
	    	dataStore.storeConfigData(connectionConfig);
	    } catch (Exception e) {
	    	showError("Cannot store data", false);
	    } finally {
	    	System.exit(0);
	    }
	}
	     
	// Open the "about" window
	private void actionSystemInfoDlg() {
		@SuppressWarnings("unused")
		final SystemInformationDlg about = new SystemInformationDlg(this);
	}
	     
	// Open the "configuration" window
	private void actionConfigDlg() {
		final ConfigurationDlg newconfig = new ConfigurationDlg(connectionConfig, this);
		SwingUtilities.invokeLater(new Runnable() {
			@SuppressWarnings("deprecation")
			public void run() {
				newconfig.show();
			}
		});
		connectionConfig = newconfig.showDialog();
		dataStore.getConfigData().setUserEmail(connectionConfig.getUserEmail());
		dataStore.getConfigData().setUserAddress(connectionConfig.getUserAddress());
		dataStore.getConfigData().setUserPort(connectionConfig.getUserPort());
		dataStore.getConfigData().setUserIP(connectionConfig.getUserIP());
		dataStore.getConfigData().setPop3Host(connectionConfig.getPop3Host());
		dataStore.setAuthenticator(dataStore.getConfigData().getUserEmail(), getPassword());
		connect();
	}
	     
	// Create new email message with contact data
	private void actionEmailTransDlg(Contact tempContact) {
		// Display message dialog
	    EmailTransmissionDlg emailDialog;    
	    try {
	    	emailDialog = new EmailTransmissionDlg(tempContact, this);	    	
	    	if (!emailDialog.display()) {
	    		return;
	    	}    	
	    } catch (Exception e) {
	    	showError("Message unable to compile.", false);
	    		return;
	    }        
	    try {
	    	// Create a new message with data from dialog
	    	Message newEmail = new MimeMessage(session);
	    	
	    	// set from, just the users email
	    	newEmail.setFrom(new InternetAddress(emailDialog.getFrom()));
	    	
	    	// now get the recipients
	    	newEmail.addRecipient(RecipientType.TO, new InternetAddress(emailDialog.getTo()));
	    	newEmail.addRecipients(RecipientType.CC, InternetAddress.parse(emailDialog.getCC()));
	    	newEmail.addRecipients(RecipientType.BCC, InternetAddress.parse(emailDialog.getBCC()));
	    	
	    	// now get subject and date
	    	newEmail.setSubject(emailDialog.getSubject());
	    	newEmail.setSentDate(new Date());
	    	
	    	// now add the attachment if there is one
	    	File attachFile = emailDialog.getAttachment();
	    	
	    	if (attachFile != null) {
	    		// create a MimeBodyPart and add the text
	    		BodyPart messageBody = new MimeBodyPart();
	    		messageBody.setText(emailDialog.getContent());
	    	
	    		// create another MimeBodyPart for the file attachment
	    		MimeBodyPart attachment = new MimeBodyPart();
	    		DataSource source = new FileDataSource(emailDialog.getAttachment());
	    		attachment.setDataHandler(new DataHandler(source));
	    		attachment.setFileName(emailDialog.getAttachment().getName());
	    	
	    		// create a multipart and add the body and file
	    		Multipart mp = new MimeMultipart();
	    		mp.addBodyPart(messageBody);
	    		mp.addBodyPart(attachment);
	    	
	    		// add the multipart to our email
	    		newEmail.setContent(mp);
	    	} else {
	    		newEmail.setText(emailDialog.getContent());
	    	}
	    	
	    	// Send the new message
	    	Transport.send(newEmail);
	    	JOptionPane.showMessageDialog(null, "Success! Message Sent!");
	    	
	    } catch (Exception e) {
	    	showError("Error! Unable to send message!", false);
	    	e.printStackTrace();
	    }	    	    
	}
	
	// refresh the emails
	public void actionRefresh() {
		
		try {
			// open our inbox for read only
			Folder inbox = store.getFolder("INBOX");
			inbox.open(Folder.READ_ONLY);
			Message[] msgs = inbox.getMessages();
			
			msgModel.setMessages(msgs);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// view the message
	public void actionViewMessageDlg(Message msg) {
		final ViewMessageDlg viewMsg = new ViewMessageDlg(msg, this);
		SwingUtilities.invokeLater(new Runnable() {
		    @SuppressWarnings("deprecation")
			public void run() {
		    	viewMsg.show();
		    }
		});
	}
	     
	// Create a new contact
	public void actionAdd() {
		Contact tempContact = new Contact();
	    final ContactEditingDlg newContact = new ContactEditingDlg(tempContact, this);
	    SwingUtilities.invokeLater(new Runnable() {
	    @SuppressWarnings("deprecation")
			public void run() {
	    		newContact.show();
	    	}
	    });
	    tempContact = newContact.showDialog();
	    if(tempContact.getName().length() > 0) {
	    	editedContact = new ArrayList<Contact>();
	    	editedContact.add(tempContact);
	    	tableModel.setContact(editedContact);
	    }
	    updateButtons();
	}
	     
	// Editing a contact
	public void actionEdit(Contact tempContact, int i) {
		final ContactEditingDlg newContact = new ContactEditingDlg(tempContact, this);
	    SwingUtilities.invokeLater(new Runnable() {
	    @SuppressWarnings("deprecation")
			public void run() {
	        	newContact.show();
	        }
	    });
	    Contact contactInfo = newContact.showDialog();
	    System.out.println(tempContact.getName());
	    if(tempContact.getName().length() > 0) {
	    	ArrayList<Contact> editedContact = new ArrayList<Contact>();
	    	editedContact.add(contactInfo);
	    	actionDelete();
	    	tableModel.setContact(editedContact);
	    }	 
	    if(tableModel.getRowCount() < 1) {
	    	selectedContact = null;
	    }
	    else {
	    	selectedContact = tableModel.getContact(tableModel.getRowCount() - 1);
	    }
	    updateButtons();
	}
	     
	// Delete contact
	private void actionDelete() {
		// Set delete flag to true
		delete = true;   
	    // Delete message
	    tableModel.deleteContact(contacts.getSelectedRow());	         
	    // Update the GUI
	    contacts.clearSelection();
	    // Reset delete flag to false
	    delete = false;
	    selectedContact = null;
	    updateButtons();
	}
	
	
	     	     
	private void tableSelectionChanged() {
		// If message is not being deleted, set selected message and display it
		if (!delete && contacts.getSelectedRow() >= 0) {
			selectedContact = tableModel.getContact(contacts.getSelectedRow());
			updateButtons();
		}
		else if (contacts.getSelectedRow() == -1 && contacts.getRowCount() > 0) {
			selectedContact = tableModel.getContact(contacts.getRowCount() - 1);
			updateButtons();
		}
		else {
			selectedContact = null;
			updateButtons();
		}
	}
	     
	// Determine if a message is selected
	private void updateButtons() {
		if (selectedContact != null) {
			addButton.setEnabled(true);
	        editButton.setEnabled(true);
	        deleteButton.setEnabled(true);
		} else {
			addButton.setEnabled(true);
	        editButton.setEnabled(false);
	        deleteButton.setEnabled(false);
	    }
	}
	     
	private void showError(String message, boolean exit) {
		JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
	    if (exit)
	    	System.exit(0);
	}
}
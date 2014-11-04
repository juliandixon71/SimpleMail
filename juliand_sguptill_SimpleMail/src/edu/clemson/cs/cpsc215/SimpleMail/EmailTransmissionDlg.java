package edu.clemson.cs.cpsc215.SimpleMail;
/**
 * 
 * @author Julian Dixon and Shane Guptil
 * @since 4/27/2014
 * @version 1.0
 * 
 * Email Transmission Dialog. This class provides a dialog box allowing
 * the user to send an email, the user can also attach files with a JFileChooser
 *
 */
import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class EmailTransmissionDlg extends JDialog {
	
	DataStore dataStore = DataStore.getInstance();
	
	// Email text fields
    private JTextField fromField, toField, ccField, bccField, subjectField;

	private JLabel attachField;
	
	// file that is attached
	private File attachFile;
    
    // Email content text area
    private JTextArea contentText;
    
    // Flag to specify if dialog was cancelled
    private boolean cancel;
    
    // Create temporary contact
	private Contact tempContact = new Contact();
	
	// Compose email window dialogue 
	public EmailTransmissionDlg(Contact recipient, Frame frame) {
		super(frame, true);
		tempContact = recipient;
	    String toInfo = "", subjectInfo = "", contentInfo = "", attachFile = "";   
	    setTitle("Compose Message");
	        
	    // Handle the closing of windows
	    addWindowListener(new WindowAdapter() {
	    	public void windowClosing(WindowEvent e) {
	                actionCancel();
	        }
	    });    
	    JPanel fieldPanel = new JPanel();
	    GridBagConstraints constraints;
	    GridBagLayout layout = new GridBagLayout();
	    fieldPanel.setLayout(layout);
	  
	    // Create the "From:" field
	    JLabel fromLabel = new JLabel("From:");
	    constraints = new GridBagConstraints();
	    constraints.anchor = GridBagConstraints.EAST;
	    constraints.insets = new Insets(5, 5, 0, 0);
	    layout.setConstraints(fromLabel, constraints);
	    fieldPanel.add(fromLabel);     
	    fromField = new JTextField();
	    constraints = new GridBagConstraints();
	    constraints.fill = GridBagConstraints.HORIZONTAL;
	    constraints.gridwidth = GridBagConstraints.REMAINDER;
	    constraints.insets = new Insets(5, 5, 0, 0);
	    layout.setConstraints(fromField, constraints);
	    fromField.setText(dataStore.getConfigData().getUserEmail());
	    fieldPanel.add(fromField);
	        
	    // Set up the "To:" field
	    JLabel toLabel = new JLabel("To:");
	    constraints = new GridBagConstraints();
	    constraints.anchor = GridBagConstraints.EAST;
	    constraints.insets = new Insets(5, 5, 0, 0);
	    layout.setConstraints(toLabel, constraints);
	    fieldPanel.add(toLabel);    
	    toField = new JTextField(toInfo);
	    constraints = new GridBagConstraints();
	    constraints.fill = GridBagConstraints.HORIZONTAL;
	    constraints.gridwidth = GridBagConstraints.REMAINDER;
	    constraints.insets = new Insets(5, 5, 0, 0);
	    constraints.weightx = 1.0D;
	    layout.setConstraints(toField, constraints);
	    toField.setText(tempContact.getEmail());
	    fieldPanel.add(toField);
	    
	    // Set up the "CC:" field
	    JLabel ccLabel = new JLabel("CC:");
	    constraints = new GridBagConstraints();
	    constraints.anchor = GridBagConstraints.EAST;
	    constraints.insets = new Insets(5, 5, 0, 0);
	    layout.setConstraints(ccLabel, constraints);
	    fieldPanel.add(ccLabel);    
	    ccField = new JTextField(toInfo);
	    constraints = new GridBagConstraints();
	    constraints.fill = GridBagConstraints.HORIZONTAL;
	    constraints.gridwidth = GridBagConstraints.REMAINDER;
	    constraints.insets = new Insets(5, 5, 0, 0);
	    constraints.weightx = 1.0D;
	    layout.setConstraints(ccField, constraints);
	    fieldPanel.add(ccField);
	    
	    // Set up the "BCC:" field
	    JLabel bccLabel = new JLabel("BCC:");
	    constraints = new GridBagConstraints();
	    constraints.anchor = GridBagConstraints.EAST;
	    constraints.insets = new Insets(5, 5, 0, 0);
	    layout.setConstraints(bccLabel, constraints);
	    fieldPanel.add(bccLabel);    
	    bccField = new JTextField(toInfo);
	    constraints = new GridBagConstraints();
	    constraints.fill = GridBagConstraints.HORIZONTAL;
	    constraints.gridwidth = GridBagConstraints.REMAINDER;
	    constraints.insets = new Insets(5, 5, 0, 0);
	    constraints.weightx = 1.0D;
	    layout.setConstraints(bccField, constraints);
	    fieldPanel.add(bccField);
	    
	    // Set up the "Subject:" field
	    JLabel subjectLabel = new JLabel("Subject:");
	    constraints = new GridBagConstraints();
	    constraints.insets = new Insets(5, 5, 5, 0);
	    layout.setConstraints(subjectLabel, constraints);
	    fieldPanel.add(subjectLabel);    	        
	    subjectField = new JTextField(subjectInfo);
	    constraints = new GridBagConstraints();
	    constraints.fill = GridBagConstraints.HORIZONTAL;
	    constraints.gridwidth = GridBagConstraints.REMAINDER;
	    constraints.insets = new Insets(5, 5, 5, 0);
	    layout.setConstraints(subjectField, constraints);
	    fieldPanel.add(subjectField);
	    
	    // Set up the "Attachment:" field
	    JButton attachButton = new JButton("Attach");
	    constraints = new GridBagConstraints();
	    constraints.insets = new Insets(5, 5, 5, 0);
	    layout.setConstraints(attachButton, constraints);
	    fieldPanel.add(attachButton);    	        
	    attachField = new JLabel(attachFile);
	    constraints = new GridBagConstraints();
	    constraints.fill = GridBagConstraints.HORIZONTAL;
	    constraints.gridwidth = GridBagConstraints.REMAINDER;
	    constraints.insets = new Insets(5, 5, 5, 0);
	    layout.setConstraints(attachField, constraints);
	    fieldPanel.add(attachField);
	    
	    // add the action listener to our attach button
	    attachButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				attachFile();
			}
	    });
	    
	    // Create the content panel
	    JScrollPane contentPanel = new JScrollPane();
	    contentText = new JTextArea(contentInfo, 10, 50);
	    contentPanel.setViewportView(contentText);
	    
	    // Create the button panel
	    JPanel buttonPanel = new JPanel();
	        
	    // Create "Send" button
	    JButton sendButton = new JButton("Send");
	    sendButton.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		actionSend();
	    	}
	    });
	    buttonPanel.add(sendButton);
	        
	    // Create "Cancel" button
	    JButton cancelButton = new JButton("Cancel");
	    cancelButton.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		actionCancel();
	        }
	    }); 
	    buttonPanel.add(cancelButton);
	            
	    // Add the panels to the display
	    getContentPane().setLayout(new BorderLayout());
	    getContentPane().add(fieldPanel, BorderLayout.NORTH);
	    getContentPane().add(contentPanel, BorderLayout.CENTER);
	    getContentPane().add(buttonPanel, BorderLayout.SOUTH);    
	    pack();
	      
	    // Center the dialog
	    setLocationRelativeTo(frame);
	}

	// Action to send message
	private void actionSend() {
		if (fromField.getText().trim().length() < 1 || toField.getText().trim().length() < 1 || subjectField.getText().trim().length() < 1 || contentText.getText().trim().length() < 1) {
			JOptionPane.showMessageDialog(this, "There are empty fields.", "Empty Field(s)", JOptionPane.ERROR_MESSAGE);
			return;
		}
	    dispose();
	}
	    
	// Cancel message and close the dialog
	private void actionCancel() {
		cancel = true;
		dispose();
	}
	    
	// action to attach the file
	private void attachFile() {
		JFileChooser fileChooser = new JFileChooser();
		int retVal = fileChooser.showDialog(this, "Attach");
		
		if (retVal == JFileChooser.APPROVE_OPTION) {
			attachFile = fileChooser.getSelectedFile();
			attachField.setText(attachFile.getName());
		}
	}
	// Display the dialog
	@SuppressWarnings("deprecation")
	public boolean display() {
		show();   
		return !cancel;
	}
	    
	// Get the "From" field 
	public String getFrom() {
		return fromField.getText();
	}
	    
	// Get the "To" field
	public String getTo() {
		return toField.getText();
	}
	    
	// Get the "CC" Field
	public String getCC() {
		return ccField.getText();
	}

	// Get the "BCC" field
	public String getBCC() {
		return bccField.getText();
	}
	
	// Get the "Subject" field
	public String getSubject() {
		return subjectField.getText();
	}
	    
	// Get the "Content" field
	public String getContent() {
		return contentText.getText();
	}

	// Get the file attachment
	public File getAttachment() {
		return attachFile;
	}

}
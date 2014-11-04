package edu.clemson.cs.cpsc215.SimpleMail;

/**
 * 
 * @author Julian Dixon and Shane Guptil
 * @since 4/27/2014
 * @version 1.0
 * 
 * System Information Dialog, this dialog opens when the user clicks the about button
 * under the Help menu
 *
 */
import java.awt.Frame;

import javax.swing.*;

@SuppressWarnings("serial")
public class SystemInformationDlg extends JDialog {
	
	@SuppressWarnings("deprecation")
	public SystemInformationDlg(Frame frame) {
		super(frame, true);
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("About");
	    String message = "<html>Simple Mail created by Shane Guptill and Julian Dixon <br>" + "<br>" +
	    			"This is a simple email client designed to work with SMTP email servers." + "<br>" +
	    			"<br> This client takes an email address and password to establish a connection to the SMTP Host allowing the user to send emails." +
	    			"<br> Users can save contacts to the contact list and send emails by double clicking on a saved contact." +
	    			"<br> Users can connect to different emails by going to the Configuration menu and selecting Options and changing the fields according to their email's SMTP host." +
	    			"<br>" + "<br> This client was built using the JavaMailAPI which allows users to edit and save contacts, send emails, and change connection configurations." + "<br>" +
	    			"<br> With questions and concerns you can contact Shane Guptill at sguptil@g.clemson.edu or Julian Dixon at juliand@g.clemson.edu ."
	    			 + "<br>Thank you for choosing Shane and Julian's Simple Mail!";
	    JOptionPane aboutPane = new JOptionPane(message);
	    JDialog aboutDialog = aboutPane.createDialog(new JFrame(), "About Shane and Julian's Simple Mail");
	    aboutDialog.show();
	}
}

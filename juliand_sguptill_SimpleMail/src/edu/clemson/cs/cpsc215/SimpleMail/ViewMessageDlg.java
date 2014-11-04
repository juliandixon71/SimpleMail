package edu.clemson.cs.cpsc215.SimpleMail;

/**
 * 
 * @author Julian Dixon and Shane Guptil
 * @since 4/27/2014
 * @version 1.0
 * 
 * View Message Dialog, this dialog will show when the user double clicks a message on the
 * message table. Provides buttons for forwarding and replying to emails. This class is 
 * incomplete, as I was unable to finish the functionality for parsing multipart emails
 * It should still work for text only emails
 *
 */
import java.awt.BorderLayout;
import java.awt.Frame;
import java.io.IOException;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.InternetAddress;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class ViewMessageDlg extends JDialog {
	
	private JTextArea msgContent;
	private JButton reply, forward;
	private JPanel buttonPanel;
	private String from, subject, msgText;
	private Message message;
	

	public ViewMessageDlg(Message msg, Frame frame) {
		super(frame, true);
		message = msg;
		
		// construct the message content 
		try {
			parseMessage();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.setTitle(from + " " + subject);
		msgContent = new JTextArea(msgText);
		
		// build the reply and forward button
		reply = new JButton("Reply");
		forward = new JButton("Forward");
		
		buttonPanel = new JPanel();
		buttonPanel.add(reply);
		buttonPanel.add(forward);
		
		// add the components
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(msgContent, BorderLayout.CENTER);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		pack();
		
		
	}
	
	private void parseMessage() throws MessagingException, IOException {
		// get the subject
		subject = message.getSubject();
		
		this.from = new String();
		
		// get the from field
		Address[] from = message.getFrom();
		for (int i = 0; i < from.length; i++) {
			InternetAddress address = (InternetAddress) from[i];
			this.from = this.from + address.getAddress();
		}
		
		String contentType = message.getContentType();
		
		if (contentType.contains("text/plan") || contentType.contains("text/html")) {
			// plain text email
			msgText = message.getContent() != null ? message.getContent().toString() : "";
		} else if (contentType.contains("multipart")) {
			// message has attachments
			Multipart mp = (Multipart) message.getContent();
			
			for (int i = 0; i < mp.getCount(); i++) {
				BodyPart part = mp.getBodyPart(i);
				if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
					// part is attachment
					
				} else {
					// not attachment
					msgText = part.getContent() != null ? part.getContent().toString(): "";
				}
			}
		}
		
		
		
	}
}

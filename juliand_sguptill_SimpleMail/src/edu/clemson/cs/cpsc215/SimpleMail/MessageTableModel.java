package edu.clemson.cs.cpsc215.SimpleMail;
/**
 * 
 * @author Julian Dixon and Shane Guptil
 * @since 4/27/2014
 * @version 1.0
 * 
 * Message Table Model, similar to contact table model, subclasses Abstract Table Model to 
 * biuld a table suitable for displaying basic information about messages, including the
 * sender, subject, and send date
 *
 */
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.internet.InternetAddress;

@SuppressWarnings("serial")
public class MessageTableModel extends AbstractTableModel {
	
	private static final String[] columnNames = {"From", "Subject", "Date"};
	
	private ArrayList<Message> messages = new ArrayList<Message>();

	public void setMessages(Message[] messages) {
		// only display the first 20 messages in the array
		for (int i = 0; i < messages.length; i++) {
			if (i > 20)
				break;
			if (messages[i] != null)
				this.messages.add(messages[i]);
		}
		
		fireTableDataChanged();
	}
	
	public Message getMsg(int row) {
		return messages.get(row);
	}
	
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		if (messages == null)
			return 0;
		
		return messages.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		
		Message msg = messages.get(row);
		String ret = null;
		try {
			switch (col) {
	       		case 0:
	       			// get the from field
	       			ret = new String();
	       			Address[] from = msg.getFrom();
	       			for (int i = 0; i < from.length; i++) {
	       				InternetAddress address = (InternetAddress) from[i];
	       				ret = address.toString();
	       			}
	        		break;
	        	case 1:
	        		ret = msg.getSubject();
	        		break;
	        	case 2:
	        		ret = msg.getSentDate().toString();
	        	default:
	        		break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	    
		return ret;
	}

}

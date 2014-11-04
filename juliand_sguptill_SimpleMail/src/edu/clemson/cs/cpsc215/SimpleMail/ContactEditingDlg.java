package edu.clemson.cs.cpsc215.SimpleMail;
/**
 * 
 * @author Julian Dixon and Shane Guptil
 * @since 4/27/2014
 * @version 1.0
 * 
 * Contact editing dialog, provides a dialog box for editing information
 * about existing contacts or adding new contacts
 *
 */
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

@SuppressWarnings("serial")
public class ContactEditingDlg extends JDialog implements ActionListener {
	
	// Edit or create a contact and save it to the contact table
	private JButton jButton1, jButton2;
	private JLabel jLabel1, jLabel2, jLabel3, jLabel4;
	private JTextField jTextField1, jTextField2, jTextField3, jTextField4;
	private Contact tempContact = new Contact();
	
	public ContactEditingDlg(Contact contact, Frame parent) {
		super(parent, true);
		tempContact = contact;
		
		jLabel1 = new JLabel();
		jTextField1 = new JTextField();
		jLabel2 = new JLabel();
		jTextField2 = new JTextField();
		jLabel3 = new JLabel();
		jTextField3 = new JTextField();
		jLabel4 = new javax.swing.JLabel();
		jTextField4 = new JTextField();
		jButton1 = new JButton();
		jButton1.addActionListener(this);
		jButton2 = new JButton();
		jButton2.addActionListener(this);
		
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("New Contact");
		
		jLabel1.setText("Name");
		jLabel2.setText("Address");
		jLabel3.setText("Phone Number");
		jLabel4.setText("Email");
		jTextField1.setText(contact.getName());
		jTextField2.setText(contact.getAddress());
		jTextField3.setText(contact.getPhone());
		jTextField4.setText(contact.getEmail());
		jButton1.setText("Save");
		jButton2.setText("Cancel");
		
		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(jLabel1).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE).addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jLabel2).addComponent(jLabel3).addComponent(jLabel4)).addGap(18, 18, 18).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jTextField4, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE).addComponent(jTextField3, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE).addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE))).addGroup(layout.createSequentialGroup().addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(18, 18, 18).addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))).addContainerGap()));
		layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButton1, jButton2});
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel1).addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel2).addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel3).addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel4).addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jButton1).addComponent(jButton2)).addContainerGap(22, Short.MAX_VALUE)));
		
		pack();
	}
	
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == jButton1) {
			String name = jTextField1.getText();
			String address = jTextField2.getText();
			String email = jTextField4.getText();
			String phone = jTextField3.getText();
			
			tempContact.setName(name);
			tempContact.setAddress(address);
			tempContact.setEmail(email);
			tempContact.setPhone(phone);
			
			JOptionPane.showMessageDialog(null, "Contact Saved");
			setVisible(false); 
			dispose();
		}
		else if (arg0.getSource() == jButton2) {
			setVisible(false); 
			dispose();
		}
	}
	
	public Contact showDialog() {
		setVisible(true);
		return tempContact;
	}
}

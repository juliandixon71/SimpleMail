package edu.clemson.cs.cpsc215.SimpleMail;
/**
 * 
 * @author Julian Dixon and Shane Guptil
 * @since 4/27/2014
 * @version 1.0
 * 
 * Configuration Dialog class, provides a dialog for the user
 * to input information about their system settings such as 
 * IP Address, email address, smtp and pop3 hosts and ports
 *
 */
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

@SuppressWarnings("serial")
public class ConfigurationDlg extends JDialog implements ActionListener {
	
	// Opens dialog to edit configuration for connecting to a session the user specifies
	private JButton jButton1, jButton2;
	private JLabel jLabel1, jLabel2, jLabel3, jLabel4, jLabel5;
	private JTextField jTextField1, jTextField2, jTextField3, jTextField4, jTextField5;
	private Configuration temp = new Configuration();
	
	public ConfigurationDlg(Configuration input, Frame parent) {
		super(parent, true);
		temp = input;
		
		jLabel1 = new JLabel();
		jTextField1 = new JTextField();
		jLabel2 = new JLabel();
		jTextField2 = new JTextField();
		jLabel3 = new JLabel();
		jTextField3 = new JTextField();
		jLabel4 = new JLabel();
		jTextField4 = new JTextField();
		jLabel5 = new javax.swing.JLabel();
		jTextField5 = new JTextField();
		jButton1 = new JButton();
		jButton1.addActionListener(this);
		jButton2 = new JButton();
		jButton2.addActionListener(this);
		
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Configuration Settings");
		

		jLabel1.setText("Email:");
		jLabel2.setText("Outgoing IP:");
		jLabel3.setText("Host SMTP:");
		jLabel4.setText("SMTP Port:");
		jLabel5.setText("POP3 Host:");
		jTextField1.setText(input.getUserEmail());
		jTextField2.setText(input.getUserIP());
		jTextField3.setText(input.getUserAddress());
		jTextField4.setText(input.getUserPort());
		jTextField5.setText(input.getPop3Host());
		
		jButton1.setText("Save");
		jButton2.setText("Cancel");
		
		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(jLabel1).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE).addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jLabel2).addComponent(jLabel3).addComponent(jLabel4).addComponent(jLabel5)).addGap(18, 18, 18).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jTextField5, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE).addComponent(jTextField4, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE).addComponent(jTextField3, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE).addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE))).addGroup(layout.createSequentialGroup().addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(18, 18, 18).addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))).addContainerGap()));
		layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButton1, jButton2});
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel1).addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel2).addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel3).addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel4).addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel5).addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jButton1).addComponent(jButton2)).addContainerGap(22, Short.MAX_VALUE)));
		
		pack();
	}
	
	// Action performed from clicking OK or Cancel and either saves the settings or dumps them
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == jButton1) {
			String userEmail = jTextField1.getText();
			String myip = jTextField2.getText();
			String myaddress = jTextField3.getText();
			String myport = jTextField4.getText();
			String pop3 = jTextField5.getText();
			
			temp.setUserEmail(userEmail);
			temp.setUserAddress(myaddress);
			temp.setUserPort(myport);
			temp.setUserIP(myip);
			temp.setPop3Host(pop3);
			
			JOptionPane.showMessageDialog(null, "Configuration Saved.");
			setVisible(false); 
			dispose();
		}
		else if (arg0.getSource() == jButton2) {
			setVisible(false); 
			dispose();
		}
	}
	
	public Configuration showDialog() {
		setVisible(true);
		return temp;
	}
}

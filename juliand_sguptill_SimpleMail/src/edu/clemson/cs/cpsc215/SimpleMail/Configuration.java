package edu.clemson.cs.cpsc215.SimpleMail;

/**
 * 
 * @author Julian Dixon and Shane Guptil
 * @since 4/27/2014
 * @version 1.0
 * 
 * Configuration class for SimpleMail, stores information
 * about the user IP Address, email address, smtp and pop3
 * hosts and ports
 *
 */
import java.io.Serializable;

@SuppressWarnings("serial")
public class Configuration implements Serializable {
	
	private String userEmail;
	private String smtpAddress;
	private String smtpPort;
	private String userIP;
	private String pop3Host;
	
	// Create a blank configuration
	public Configuration() {
		userEmail = "";
		smtpAddress = "";
		smtpPort = "";
		userIP = "";
		pop3Host = "";
		
	}
	
	// Configuration constructor for inputed settings
	public Configuration(String email, String smtpAddressInput, String port, String ip, String pop3) {
		userEmail = email;
		smtpAddress = smtpAddressInput;
		smtpPort = port;
		userIP = ip;
		pop3Host = pop3;
	}
	
	public void setUserEmail(String email) {
		userEmail = email;
	}
	
	public String getUserEmail() {
		return userEmail;
	}
	
	public void setUserAddress(String address) {
		smtpAddress = address;
	}
	
	public String getUserAddress() {
		return smtpAddress;
	}
	
	public void setUserPort(String port) {
		smtpPort = port;
	}
	
	public String getUserPort() {
		return smtpPort;
	}
	
	public void setUserIP(String input) {
		userIP = input;
	}
	
	public String getUserIP() {
		return userIP;
	}
	
	public void setPop3Host(String pop3){
		pop3Host = pop3;
	}
	
	public String getPop3Host() {
		return pop3Host;
	}
}

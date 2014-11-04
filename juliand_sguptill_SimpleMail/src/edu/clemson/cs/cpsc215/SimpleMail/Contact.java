package edu.clemson.cs.cpsc215.SimpleMail;

/**
 * 
 * @author Julian Dixon and Shane Guptil
 * @since 4/27/2014
 * @version 1.0
 * 
 * Contact class stores information about saved contacts, including 
 * Name, email, home address, and phone number
 *
 */
import java.io.Serializable;

@SuppressWarnings("serial")
public class Contact implements Serializable {
	
	// Saves a contact's email, address, name, and phone number in strings
	private String m_email;
	private String m_address;
	private String m_name;
	private String m_phone;
	
	public Contact() {
		m_email = "";
		m_address = "";
		m_name = "";
		m_phone = "";
	}
	
	public Contact(String name, String email, String address, String phone) {
		m_name = name;
		m_email = email;
		m_address = address;
		m_phone = phone;
	}
	
	public void setName(String name) {
		m_name = name;
	}
	
	public String getName() {
		return m_name;
	}
	
	public void setEmail(String email) {
		m_email = email;
	}
	
	public String getEmail() {
		return m_email;
	}
	
	public void setAddress(String address) {
		m_address = address;
	}
	
	public String getAddress() {
		return m_address;
	}
	public void setPhone(String phone) {
		m_phone = phone;
	}
	
	public String getPhone() {
		return m_phone;
	}
}

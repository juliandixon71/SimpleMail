package edu.clemson.cs.cpsc215.SimpleMail;

/**
 * 
 * @author Julian Dixon and Shane Guptil
 * @since 4/27/2014
 * @version 1.0
 * 
 * MainDriver for SimpleMail. Opens the window and 
 * connects to the server
 *
 */

public class MainDriver {
	
	// Start the email client and call for connection
	public static void main(String[] args) {
		MainFrame window = new MainFrame();
		window.setVisible(true);
		window.connect();
	}
}

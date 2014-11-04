package edu.clemson.cs.cpsc215.SimpleMail;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class DataStoreTest {
	
	private DataStore store;
	private Configuration config;
	private Contact contact;

	@Before
	public void setUp() throws Exception {
		store = DataStore.getInstance();
	}

	@Test
	public void testStoreConfig() {
		config = new Configuration("juliand@clemson.edu", "smtp.gmail.com", "465", "196.773.19.1", "pop.gmail.com");
	}

}

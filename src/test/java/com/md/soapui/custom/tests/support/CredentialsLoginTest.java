package com.md.soapui.custom.tests.support;

import org.junit.Test;

import com.md.soapui.custom.support.ui.login.CredsContainer;

public class CredentialsLoginTest {
	
	@Test
	public void CredsCollectorTest() {
		CredsContainer creds = new CredsContainer("username", "password".toCharArray());
		assert "username password".equals(creds.getUsername() + " " + creds.getPassword().toString());
	}
}
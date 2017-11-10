package com.md.soapui.custom.tests.support;

import javax.swing.JFrame;

import org.junit.Test;

import com.md.soapui.custom.support.ui.login.CredsContainer;
import com.md.soapui.custom.support.ui.login.UILoginComponent;
import com.md.soapui.custom.support.ui.login.UILoginComponentImplementation;

public class CredentialsLoginTest {
	
	@Test
	public void CredsCollectorTest() {
		CredsContainer creds = new CredsContainer("username", "password".toCharArray());
		System.out.println(creds.getUsername() + " " + new String(creds.getPassword()));
		assert "username password".equals(creds.getUsername() + " " + new String(creds.getPassword()));
	}
	
	@Test
	public void UILoginComponentNakedTest() {
		UILoginComponent ui = new UILoginComponent();
		JFrame frame = new JFrame();
		frame.add(ui.getComponent());
		frame.pack();
		frame.setVisible(true);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void UILoginComponentImplementationGetCredsTest() {
		UILoginComponentImplementation loginUi = new UILoginComponentImplementation();
		CredsContainer creds = loginUi.launch().getCreds();
		assert "username password".equals(creds.getUsername() + " " + new String(creds.getPassword()));
	}
}
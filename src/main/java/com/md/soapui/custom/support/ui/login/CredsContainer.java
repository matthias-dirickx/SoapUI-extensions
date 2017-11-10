package com.md.soapui.custom.support.ui.login;

public class CredsContainer {
	
	private String un;
	private char[] pw;
	
	public String getUsername() {
		return un;
	}
	
	public char[] getPassword() {
		return pw;
	}
	
	public CredsContainer(String un, char[] pw) {
		this.un = un;
		this.pw = pw;
	}
}
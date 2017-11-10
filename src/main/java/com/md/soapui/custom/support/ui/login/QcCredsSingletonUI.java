package com.md.soapui.custom.support.ui.login;

public class QcCredsSingletonUI {
	
	private static QcCredsSingletonUI QcCreds;
	private CredsContainer creds;
	
	private QcCredsSingletonUI() {
		setCreds();
	}
	
	public String getUsername() {
		return getInstance().creds.getUsername();
	}
	
	public char[] getPassword() {
		return getInstance().creds.getPassword();
	}
	
	private static QcCredsSingletonUI getInstance() {
		if(QcCreds == null) {
			synchronized(QcCredsSingletonUI.class) {
				if(QcCreds == null) {
					QcCreds = new QcCredsSingletonUI();
				}
			}
		}
		return QcCreds;
	}

	private void setCreds() {
		this.creds = new UILoginComponentImplementation().launch().getCreds();
	}
}
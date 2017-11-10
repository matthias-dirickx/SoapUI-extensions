package com.md.soapui.custom.support.ui.login;

import javax.swing.JFrame;
import com.md.soapui.custom.support.ui.login.CredsContainer;

public class UILoginComponentImplementation {
	
	private UILoginComponent login;
	
	public UILoginComponentImplementation() {
		login = new UILoginComponent();
	}
	
	public UILoginComponentImplementation launch() {
		JFrame frame = new JFrame();
		frame.add(login.getComponent());
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		return this;
	}
	
	public CredsContainer getCreds() {
		while(login.done == false) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				//TODO show error message. Tried with dialog but gave error when adding to frame.
				e.printStackTrace();
			}
		}
		return new CredsContainer(login.getUn(), login.getPw());
	}
}
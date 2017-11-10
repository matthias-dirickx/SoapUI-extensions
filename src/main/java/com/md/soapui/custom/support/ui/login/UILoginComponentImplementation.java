package com.md.soapui.custom.support.ui.login;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import com.md.soapui.custom.support.ui.login.CredsContainer;

public class UILoginComponentImplementation {
	
	private UILoginComponent login;
	
	public UILoginComponentImplementation() {
		login = new UILoginComponent();
	}
	
	public void launch() {
		JFrame frame = new JFrame();
		frame.add(login);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public CredsContainer getCreds() {
		while(login.done == false) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				JOptionPane.showConfirmDialog(null
						                    , "Something went wrong.\n"
						                    + "Please consult the log files for more information"
						                    , "Error - Thread sleep interrupted"
						                    , JOptionPane.OK_OPTION
						                    , JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
		}
		return new CredsContainer(login.getUn(), login.getPw());
	}
}
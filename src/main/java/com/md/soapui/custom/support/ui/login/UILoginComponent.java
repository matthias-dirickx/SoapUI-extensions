package com.md.soapui.custom.support.ui.login;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class UILoginComponent extends JDialog {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JComponent component;
	private JTextField unField;
	private JPasswordField pwField;

	private String un;
	private char[] pw;
	
	public boolean done;
	
	public UILoginComponent() {
		done = false;
		component = createComponent();
	}
	
	public String getUn() {
		return un;
	}

	public char[] getPw() {
		return pw;
	}
	
	public JComponent getComponent() {
		return this.component;
	}

	private JComponent createComponent() {
		JComponent panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridx = 0;
		c.gridy = 0;
		
		panel.add(buildFields(), c);
		
		c.gridx = 0;
	    c.gridy = 1;
		
	    panel.add(buildButtons(), c);
		return panel;
	}

	private Component buildFields() {
		JComponent comp = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		JLabel unFieldLabel = new JLabel("Username: ");
		unField = new JTextField();
		JLabel pwFieldLabel = new JLabel("Password: ");
		pwField = new JPasswordField();
		
		c.gridx = 0;
		c.gridy = 0;
		
		comp.add(unFieldLabel, c);
		
		c.gridx = 1;
		c.gridy = 0;
		
		comp.add(unField, c);
		
		c.gridx = 0;
		c.gridy = 1;
		
		comp.add(pwFieldLabel, c);
		
		c.gridx = 1;
		c.gridy = 1;
		
		comp.add(pwField, c);
		
		return null;
	}

	private Component buildButtons() {
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new okAction());
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new cancelAction());
		
		return null;
	}
	
	private class okAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			un = unField.getText();
			pw = pwField.getPassword();
			done = true;
			dispose();
		}
	}
	
	private class cancelAction implements ActionListener {
		@Override
		public void actionPerformed (ActionEvent e) {
			un = "";
			pw = "".toCharArray();
			done = true;
			dispose();
		}
	}
}
package com.md.soapui.custom.util.datadriver;

public class DataDriverException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public DataDriverException() {
	}
	
	public DataDriverException(String message) {
		super(message);
	}
	
	public DataDriverException(Throwable cause) {
		super(cause);
	}
	
	public DataDriverException(String message, Throwable cause) {
		super(message,cause);
	}

}

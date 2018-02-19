package com.md.soapui.custom.util.data;

public class DataTyper {
	private boolean returnNullAsEmptyString;
	private boolean returnStringNullAsString;

	public DataTyper() {
		returnNullAsEmptyString = true;
		returnStringNullAsString = true;
	}
	
	public DataTyper(boolean returnNullAsEmptyString,
			         boolean returnStringNullAsString) {
		this.returnNullAsEmptyString = returnStringNullAsString;
		this.returnStringNullAsString = returnStringNullAsString;
	}
	
	public void setReturnNullAsEmptyString(boolean returnNullAsEmptyString) {
		this.returnNullAsEmptyString = returnNullAsEmptyString;
	}

	public void setReturnStringNullAsString(boolean returnStringNullAsString) {
		this.returnStringNullAsString = returnStringNullAsString;
	}
	
	protected Object getTypedData(String str) {
		if("NULL".equalsIgnoreCase(str)) {
			if(returnStringNullAsString) {
				return str;
			} else {
				return null;
			}
		} else if(str.trim().matches("^(\\d+(?:[\\.\\,]\\d*)?)$")) {
			Long num = Long.parseLong(str.trim());
			return num;
		} else {
			if(returnNullAsEmptyString) {
				return "";	
			} else {
				return null;
			}
		}
	}
}
package com.md.soapui.custom.util.datadriver;

import java.io.File;
import java.util.Arrays;

public class DataDriver {
	
	private DataDriverType type;
	private AbstractDataDriver dataDriver;
	private File file;
	
	public void setDataDriverType(DataDriverType type) {
		this.type = type;
	}
	public DataDriverType getDataDriverType() {
		return this.type;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public File getFile() {
		return this.file;
	}
	
	public DataDriver() {
	}
	
	public DataDriver(DataDriverType type, File file) {
		this.type = type;
		this.file = file;
		initiateDriver();
	}
	
	private void initiateDriver() {
		switch(type) {
		    case EXCEL_XLSX : dataDriver = new ExcelDataDriver(file);
		    break;
		    default: new DataDriverException("Type not known - must be one of DataDriverType Enum " + Arrays.asList(DataDriverType.values()));
		}
	}
	
	
	
}
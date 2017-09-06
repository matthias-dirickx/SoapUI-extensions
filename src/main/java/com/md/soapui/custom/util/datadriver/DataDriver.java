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
	
	public DataDriver(DataDriverType type, File file) throws DataDriverException {
		//Add check when extra implementation for abstract data driver that do not fit type and file constructor.
		this.type = type;
		this.file = file;
		initiateDriver();
	}
	
	private void initiateDriver() throws DataDriverException {
		switch(type) {
		    case EXCEL_XLSX : dataDriver = new ExcelDataDriver(file);
		    break;
		    default: throw new DataDriverException("Type not known - must be one of DataDriverType Enum " + Arrays.asList(DataDriverType.values()));
		}
	}
	
	/**
	 * Access point for all configuration as well as the command to load the set with the loadDataset() method.
	 * @return AbstractDataDriver
	 */
	public AbstractDataDriver config() {
		return dataDriver;
	}
}
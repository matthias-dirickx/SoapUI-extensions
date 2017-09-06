package com.md.soapui.custom.util.datadriver;

import java.io.File;
import java.util.Arrays;

public class DataDriver {
	
	private DataDriverType type;
	private AbstractDataDriver dataDriver;
	private File file;
	private int firstRowIndex;
	private int lastrowIndex;
	
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
	
	/**
	 * Get line of data within the boundaries of the configuration.
	 * <br/>
	 * It either returns a map or a list of strings.
	 * <br/>
	 * <p>The Map of strings is defined as:<br/>
	 * <li>key: header name</li>
	 * <li>value: cell value as String</li>
	 * </p>
	 * <p>
	 * The list of Strings contains the values for the range defined in the configuration in the order they are retrieved.<br/>
	 * 
	 * </p>
	 * 
	 * @param int rowIndex
	 * @return Map&lt;String, String&gt; or ArrayList&lt;String&gt;
	 */
	public Object getDataLine(int rowIndex) {
		return dataDriver.getLine(rowIndex);
	}
}
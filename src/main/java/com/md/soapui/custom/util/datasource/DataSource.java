package com.md.soapui.custom.util.datasource;

import java.io.File;
import java.util.Arrays;
import java.util.Map;

public class DataSource {
	
	private DataSourceType type;
	private AbstractDataSource dataSource;
	private File file;
	
	public DataSource(DataSourceType type, File file) throws DataSourceException {
		//Add check when extra implementation for abstract data driver that do not fit type and file constructor.
		this.type = type;
		this.file = file;
		initiateDriver();
	}
	
	private void initiateDriver() throws DataSourceException {
		switch(type) {
		    case EXCEL_XLSX : dataSource = new DataSourceExcel(file);
		    break;
		    case CSV : dataSource = new DataSourceCsv(file);
		    break;
		    default: throw new DataSourceException("Type not known - must be one of DataDriverType Enum "
		                                           + Arrays.asList(DataSourceType.values()));
		}
	}
	
	/**
	 * Access point for all configuration as well as the command
	 * to load the set with the loadDataset() method.
	 * @return AbstractDataDriver
	 */
	public AbstractDataSource config() {
		return dataSource;
	}
	
	/**
	 * Get line of data within the boundaries of the configuration.
	 * <br/>
	 * It either returns a map of Strings or a map of integer with Strings.<br/>
	 * The choice is made earlier in the configuration. If you want to use headers,
	 * then you need to call the config() method and define
	 * config().useHeadersAtRow(int rowIndex) to configure this. This config needs to be implemented before calling the getDataLine() method.
	 * <br/>
	 * <p>The Map of Strings to String is defined as:<br/>
	 * <li>key: header name</li>
	 * <li>value: cell value as String</li>
	 * </p>
	 * <p>The Map of Integer to String is defined as:<br/>
	 * <li>key: Row ID</li>
	 * <li>value: cell value as String</li>
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param int rowIndex
	 * @return Map&lt;String, String&gt; or Map&lt;Integer, String&gt;
	 */
	public Map<?, String> getDataLine(int rowIndex) {
		return dataSource.getLine(rowIndex);
	}
}
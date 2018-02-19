package com.md.soapui.custom.util.datasource;

import java.util.Map;

abstract public class AbstractDataSource {
	private static int DEFAULT_HEADERS_ROW = 0;
	private static int DEFAULT_FIRST_DATA_ROW = 1;
	private static boolean DEFAULT_BASED_ON_HEADERS = false;
	private static int DEFAULT_START_AT_COMUMN = 0;
	
	//Shared variables
	private int headerRowNumber;
	private int firstDataRowNumber;
	private boolean basedOnHeaders;
	private int startAtColumn;
	private int stopAtColumn;
	private int startAtRow;
	private int stopAtRow;
	
	//Getters and setters to access the private variables
	//All setters must return this for fluent code structure.
	public int getStartAtRow() {
		return startAtRow;
	}
	
	public AbstractDataSource setStartAtRow(int startAtRow) {
		this.startAtRow = startAtRow;
		return this;
	}
	
	public int getStopAtRow() {
		return stopAtRow;
	}
	
	public AbstractDataSource setStopAtRow(int stopAtRow) {
		this.stopAtRow = stopAtRow;
		return this;
	}
	
	public int getStartAtColumn() {
		return startAtColumn;
	}
	
	public AbstractDataSource setStartAtColumn(int startAtColumn) {
		this.startAtColumn = startAtColumn;
		return this;
	}
	
	public int getStopAtColumn() {
		return stopAtColumn;
	}
	
	public AbstractDataSource setStopAtColumn(int stopAtColumn) {
		this.stopAtColumn = stopAtColumn;
		return this;
	}
	
	public AbstractDataSource setBasedOnHeaders(boolean basedOnHeaders) {
		this.basedOnHeaders = basedOnHeaders;
		return this;
	}
	
	public boolean getBasedOnHeaders() {
		return this.basedOnHeaders;
	}
	
	public AbstractDataSource setHeaderRowNumber(int headerRowNumber) {
		this.headerRowNumber = headerRowNumber;
		return this;
	}
	
	public int getHeaderRowNumber() {
		return this.headerRowNumber;
	}
	
	public int getFirstDataRowNumber() {
		return this.firstDataRowNumber;
	}
	
	public AbstractDataSource setFirstDataRowNumber(int firstDataRowNumber) {
		this.firstDataRowNumber = firstDataRowNumber;
		return this;
	}
	
	//Initialize the overall default values.
	public void initializeDefaultValues() {
		setBasedOnHeaders(DEFAULT_BASED_ON_HEADERS);
		setHeaderRowNumber(DEFAULT_HEADERS_ROW);
		setFirstDataRowNumber(DEFAULT_FIRST_DATA_ROW);
	}
	
	//Get line code -- generic public accessor for abstract datasource.
	//The implementation to return the map is on the type level.
	public Map<?, String> getLine(int rowNumber) throws DataSourceException {
		if(basedOnHeaders) {
		    return getLineWithHeaders(rowNumber);
		} else {
			return getLineWithoutHeaders(rowNumber);
		}
	}
	
	public abstract AbstractDataSource getTheSheetWithName(String sheetName) throws DataSourceException;
	public abstract AbstractDataSource getTheSheetWithIndex(int sheetIndex) throws DataSourceException;
	public abstract AbstractDataSource startAtRow(int index) throws DataSourceException;
	public abstract AbstractDataSource stopAtRow(int index) throws DataSourceException;
	public abstract AbstractDataSource startAtColumn(int index) throws DataSourceException;
	public abstract AbstractDataSource stopAtColumn(int index) throws DataSourceException;
	public abstract AbstractDataSource useHeadersAtRow(int index) throws DataSourceException;
	public abstract AbstractDataSource setSeperatorTo(String sep) throws DataSourceException;
	abstract protected void setDatasetCountValues();
	
	public abstract void loadDataset();
	
	abstract protected Map<Integer,String> getLineWithoutHeaders(int rowNumber) throws DataSourceException;
	abstract protected Map<String,String> getLineWithHeaders(int rowNumber) throws DataSourceException;
}

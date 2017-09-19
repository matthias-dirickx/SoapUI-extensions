package com.md.soapui.custom.util.datasource;

import java.util.ArrayList;
import java.util.Map;

abstract public class AbstractDataSource {
	private static int DEFAULT_HEADERS_ROW = 0;
	private static int DEFAULT_FIRST_DATA_ROW = 1;
	private static boolean DEFAULT_BASED_ON_HEADERS = false;
	
	private int headerRowNumber;
	private int firstDataRowNumber;
	private boolean basedOnHeaders;
	
	public AbstractDataSource setBasedOnHeaders(boolean basedOnHeaders) {
		this.basedOnHeaders = basedOnHeaders;
		return this;
	}
	public boolean getBasedOnHeaders() {
		return this.basedOnHeaders;
	}
	public void setHeaderRowNumber(int headerRowNumber) {
		this.headerRowNumber = headerRowNumber;
	}
	public int getHeaderRowNumber() {
		return this.headerRowNumber;
	}
	public int getFirstDataRowNumber() {
		return this.firstDataRowNumber;
	}
	public void setFirstDataRowNumber(int firstDataRowNumber) {
		this.firstDataRowNumber = firstDataRowNumber;
	}
	public void initializeDefaultValues() {
		setBasedOnHeaders(DEFAULT_BASED_ON_HEADERS);
		setHeaderRowNumber(DEFAULT_HEADERS_ROW);
		setFirstDataRowNumber(DEFAULT_FIRST_DATA_ROW);
	}
	
	public Map<?, String> getLine(int rowNumber) {
		if(basedOnHeaders) {
		    return getLineWithHeaders(rowNumber);
		} else {
			return getLineWithoutHeaders(rowNumber);
		}
	}
	
	public abstract AbstractDataSource getTheSheetWithName(String sheetName);
	public abstract AbstractDataSource getTheSheetWithIndex(int sheetIndex);
	public abstract AbstractDataSource startAtRow(int index);
	public abstract AbstractDataSource stopAtRow(int index);
	public abstract AbstractDataSource startAtColumn(int index);
	public abstract AbstractDataSource stopAtColumn(int index);
	public abstract AbstractDataSource useHeadersAtRow(int index);
	
	public abstract void loadDataset();
	
	abstract protected Map<Integer,String> getLineWithoutHeaders(int rowNumber);
	abstract protected Map<String,String> getLineWithHeaders(int rowNumber);
	abstract protected void setDatasetCountValues();
}

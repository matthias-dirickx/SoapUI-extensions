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
	
	public void setBasedOnHeaders(boolean basedOnHeaders) {
		this.basedOnHeaders = basedOnHeaders;
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
	
	public Object getLine(int rowNumber) {
		Object o = null;
		if(basedOnHeaders) {
		    o = getLineWithHeaders(rowNumber);
		} else {
			o = getLineWithoutHeaders(rowNumber);
		}
		return o;
	}
	
	abstract protected void loadDataset();
	abstract protected ArrayList<String> getLineWithoutHeaders(int rowNumber);
	abstract protected Map<String,String> getLineWithHeaders(int rowNumber);
	abstract protected void setDatasetCountValues();
}

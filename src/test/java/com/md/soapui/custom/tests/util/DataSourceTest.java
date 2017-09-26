package com.md.soapui.custom.tests.util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.md.soapui.custom.util.datasource.DataSource;
import com.md.soapui.custom.util.datasource.DataSourceException;
import com.md.soapui.custom.util.datasource.DataSourceType;

public class DataSourceTest {
	
	@Test
	public void testDataDriverInitialization_InitializedValues() throws DataSourceException {
		DataSource eDriver = new DataSource(DataSourceType.EXCEL_XLSX, new File("src/test/resources/testBook_Sheets_AndColumns.xlsx"));
		eDriver.config().loadDataset();
		assert eDriver.config().getBasedOnHeaders() == false;
		assert eDriver.config().getFirstDataRowNumber() == 1;
		assert eDriver.config().getHeaderRowNumber() == 0;
	}
	
	@Test
	public void testDataDriverInitialization_ExceptionNotAnExcelFileTxt() {
		try {
			new DataSource(DataSourceType.EXCEL_XLSX, new File("src/test/resources/notAnExcel.txt")).getClass().getSimpleName().equals("DataSourceException");
		} catch (DataSourceException e) {
			assert e.getClass().getSimpleName().equals("DataSourceException");
		}
	}
	
	@Test
	public void testDataDriverInitialization_ExceptionIoExceptionFileNotExists() {
		try {
			new DataSource(DataSourceType.EXCEL_XLSX, new File("src/test/resources/doesNotExist.xlsx")).getClass().getSimpleName().equals("DataSourceException");
		} catch (DataSourceException e) {
			assert e.getClass().getSimpleName().equals("DataSourceException");
		}
	}
	
	@Test
	public void testDataDriverExceptionTestPlainWithoutMessageOrCause() {
		assert new DataSourceException() != null;
	}
	
	@Test
	public void testDataDriverExceptionTestPlainWithMessage() {
		assert new DataSourceException("With a Message").toString().equals("com.md.soapui.custom.util.datasource.DataSourceException: With a Message");
	}
	
	@Test
	public void testDataDriverExceptionTestPlainWithCause() {
		assert new DataSourceException(new IOException()).toString().equals("com.md.soapui.custom.util.datasource.DataSourceException: java.io.IOException");
	}
	
	@Test
	public void testDataDriverExceptionTestPlainWithCauseAndMessage() {
		//This is strange - would expect the thrown exception to be included.
		assert new DataSourceException("With a Message", new IOException()).toString().equals("com.md.soapui.custom.util.datasource.DataSourceException: With a Message");
	}
	
	@Test
	public void testDataDriverInitialization_ExcelNoOptionsGetFirstDataLine() throws DataSourceException {
		DataSource eDriver = new DataSource(DataSourceType.EXCEL_XLSX, new File("src/test/resources/testBook_Sheets_AndColumns.xlsx"));
		eDriver.config().loadDataset();
		
		Map<Integer, String> validationMap = new HashMap<>();
		validationMap.put(0, "Data row 1 cell 0");
		validationMap.put(1, "Data row 1 cell 1");
		validationMap.put(2, "Data row 1 cell 2");
		validationMap.put(3, "Data row 1 cell 3");
		validationMap.put(4, "");
		validationMap.put(5, "");
		
		System.out.println(eDriver.getDataLine(1).toString());
		assert eDriver.config().getLine(1).equals(validationMap);
	}
	
	@Test
	public void testDataDriverInitialization_ExcelFromSheetByIndex() throws DataSourceException {
		DataSource eDriver = new DataSource(DataSourceType.EXCEL_XLSX, new File("src/test/resources/testBook_Sheets_AndColumns.xlsx"));
		eDriver.config().getTheSheetWithIndex(1)
		                .loadDataset();
		
		Map<Integer, String> validationMap = new HashMap<>();
		validationMap.put(0, "sheet name tEst - Data row 1 cell 0");
		validationMap.put(1, "sheet name tEst - Data row 1 cell 1");
		validationMap.put(2, "sheet name tEst - Data row 1 cell 2");
		validationMap.put(3, "sheet name tEst - Data row 1 cell 3");
		validationMap.put(4, "");
		validationMap.put(5, "");
		
		System.out.println(eDriver.getDataLine(1).toString());
		assert eDriver.config().getLine(1).equals(validationMap);
	}
	
	@Test
	public void testDataDriverInitialization_ExcelFromSheetByName() throws DataSourceException {
		DataSource eDriver = new DataSource(DataSourceType.EXCEL_XLSX, new File("src/test/resources/testBook_Sheets_AndColumns.xlsx"));
		eDriver.config().getTheSheetWithName("testSheet")
		                .loadDataset();
		
		Map<Integer, String> validationMap = new HashMap<>();
		validationMap.put(0, "Sheet at index 2 - Data row 1 cell 0");
		validationMap.put(1, "Sheet at index 2 - Data row 1 cell 1");
		validationMap.put(2, "Sheet at index 2 - Data row 1 cell 2");
		validationMap.put(3, "Sheet at index 2 - Data row 1 cell 3");
		validationMap.put(4, "");
		validationMap.put(5, "");
		
		System.out.println(eDriver.getDataLine(1).toString());
		assert eDriver.config().getLine(1).equals(validationMap);
	}
	
	@Test
	public void testDataDriverInitialization_ExcelFromSheetByNameLine3WithHeaders() throws DataSourceException {
		DataSource eDriver = new DataSource(DataSourceType.EXCEL_XLSX, new File("src/test/resources/testBook_Sheets_AndColumns.xlsx"));
		eDriver.config().getTheSheetWithName("testSheet")
		                .setBasedOnHeaders(true)
		                .loadDataset();
		
		Map<String, String> validationMap = new HashMap<>();
		validationMap.put("Sheet at index 2 - Header 1", "Sheet at index 2 - Data row 1 cell 0");
		validationMap.put("Sheet at index 2 - Header 2", "Sheet at index 2 - Data row 1 cell 1");
		validationMap.put("Sheet at index 2 - Header 3", "Sheet at index 2 - Data row 1 cell 2");
		validationMap.put("Sheet at index 2 - Header 4", "Sheet at index 2 - Data row 1 cell 3");
		validationMap.put("Sheet at index 2 - Header 5", "");
		validationMap.put("Sheet at index 2 - Header 6", "");
		
		System.out.println(eDriver.getDataLine(1).toString());
		assert eDriver.config().getLine(1).equals(validationMap);
	}
	
	@Test
	public void testDataDriverInitialization_FullCustom() throws DataSourceException {
		//TODO implement assertions - this is a useless test. Only checks if code not chokes.
		DataSource eDriver = new DataSource(DataSourceType.EXCEL_XLSX, new File("src/test/resources/testBook_Sheets_AndColumns.xlsx"));
		eDriver.config().startAtColumn(1)
		                .startAtRow(3)
		                .stopAtColumn(3)
		                .stopAtRow(4)
		                .useHeadersAtRow(1);
		eDriver.config().loadDataset();
	}
	
	@Test
	public void testDataDriverInitialization_CsvShellDriverTest() throws DataSourceException {
		assert new DataSource(DataSourceType.CSV, new File("src/test/resources/testBook_Sheets_AndColumns.xlsx"))!=null;
	}
}
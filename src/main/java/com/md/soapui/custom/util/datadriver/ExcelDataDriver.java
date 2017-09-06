package com.md.soapui.custom.util.datadriver;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelDataDriver extends AbstractDataDriver {
	
	private static int DEFAULT_SHEET_INDEX = 0;
	private static final String DEFAULT_SHEET_NAME="Sheet1";
	private static final boolean DEFAULT_SELECT_SHEET_BY_NAME = false;
	
	private int firstCellNumber;
	private int lastCellNumber;
	private int firstHeaderCell;
	private int lastHeaderCell;
	private int lastDatarow;
	
	private int sheetIndex;
	private String sheetName;
	private boolean selectSheetByName;
	
	private File file;
	private XSSFWorkbook wb;
	private XSSFSheet sheet;
	private XSSFRow headerRow;
	
	/**
	 * Creates object ExcelDataDriver.
	 * Sets File file to input 'file' in parameters.
	 * Initializes standard values
	 * Header row: index 0
	 * First data row: index 1
	 * Return data with headers: false
	 * @param file
	 */
	public ExcelDataDriver(File file) {
		this.file = file;
		setWorkbook();
		initializeDefaultValues();
		this.sheetIndex = DEFAULT_SHEET_INDEX;
		this.sheetName = DEFAULT_SHEET_NAME;
		this.selectSheetByName = DEFAULT_SELECT_SHEET_BY_NAME;
	}
	
	private void setWorkbook() {
		try {
			this.wb = new XSSFWorkbook(this.file);
		} catch (InvalidFormatException e) {
			new DataDriverException("This is not a valid format");
			e.printStackTrace();
		} catch (IOException e) {
			new DataDriverException("IO Exception");
			e.printStackTrace();
		}
	}
	
	/**
	 * Load workbook at file set in constructor.
	 * When selectSheetByName equals true, the sheet name will be used.
	 * When selectSheetByName equals false, the sheet index will be used.
	 * 
	 * Default sheet index: 0
	 * Default sheet name: Sheet1
	 */
	@Override
	public void loadDataset() {
		if(!selectSheetByName) {
			sheet = wb.getSheetAt(this.sheetIndex);
		} else {
			sheet = wb.getSheet(sheetName);
		}
		setDatasetCountValues();
	}
	
	@Override
	protected void setDatasetCountValues() {
		setFirstExistingCellNumber();
		setLastExistingCellNumber();
		setFirstHeaderCellNumber();
		setLastHeaderCellNumber();
		setLastDatarowNumber();
	}
	
	public ExcelDataDriver setSheetIndex(int sheetIndex) {
		this.sheetIndex = sheetIndex;
		return this;
	}
	
	public int getSheetIndex() {
		return this.sheetIndex;
	}
	
	public ExcelDataDriver setSheetName(String sheetName) {
		this.sheetName = sheetName;
		return this;
	}
	
	public String getSheetName() {
		return this.sheetName;
	}
	
	public ExcelDataDriver setSelectSheetByName(boolean selectSheetByName) {
		this.selectSheetByName = selectSheetByName;
		return this;
	}
	
	public boolean getSelectSheetByName() {
		return this.selectSheetByName;
	}
	
	public ExcelDataDriver startAtRow(int rowIndex) {
		setFirstDataRowNumber(rowIndex);
		return this;
	}
	
	public ExcelDataDriver stopAtRow(int rowIndex) {
		this.lastDatarow = rowIndex;
		return this;
	}
	
	public ExcelDataDriver startAtColumn(int cellIndex) {
		firstCellNumber = cellIndex;
		return this;
	}
	
	public ExcelDataDriver stopAtColumn(int cellIndex) {
		lastCellNumber = cellIndex;
		return this;
	}
	
	//Private methods for the automatic initialization
	private void setFirstExistingCellNumber() {
		int firstCellNumber = sheet.getRow(0).getFirstCellNum();
		if(firstCellNumber != 0) {
			for(int i = 1; i < sheet.getLastRowNum(); i++) {
				int testInt = sheet.getRow(i).getFirstCellNum();
				if(testInt < firstCellNumber) {
					firstCellNumber = testInt;
				}
				if(testInt == 0) {
					break;
				}
			}
		}
		this.firstCellNumber = firstCellNumber;
	}
	
	private void setLastExistingCellNumber() {
		int lastCellNumber = sheet.getRow(0).getLastCellNum();
		for(int i = 1; i < sheet.getLastRowNum(); i++) {
			int testInt = sheet.getRow(i).getLastCellNum();
			if(testInt > lastCellNumber) {
				lastCellNumber = testInt;
			}
		}
		this.lastCellNumber = lastCellNumber;
	}
	
	private void setFirstHeaderCellNumber() {
		firstHeaderCell = sheet.getRow(this.getHeaderRowNumber())
				               .getFirstCellNum();
	}
	
	private void setLastHeaderCellNumber() {
		lastHeaderCell = sheet.getRow(this.getHeaderRowNumber())
				              .getLastCellNum();
	}
	
	private void setLastDatarowNumber() {
		lastDatarow = sheet.getLastRowNum();
	}
	
	@Override
	protected ArrayList<String> getLineWithoutHeaders (int rowNumber) {
		ArrayList<String> row = new ArrayList<>();
		XSSFRow dataRow = sheet.getRow(rowNumber);
		for(int cellNum = firstCellNumber; cellNum < lastCellNumber; cellNum++) {
			row.add(dataRow.getCell(cellNum).getStringCellValue());
		}
		return row;
	}
	
	@Override
	protected HashMap<String,String> getLineWithHeaders(int rowNumber) {
		HashMap<String, String> row = new HashMap<>();
		XSSFRow dataRow = sheet.getRow(rowNumber);
		headerRow = sheet.getRow(getHeaderRowNumber());
		for(int cellNum = firstHeaderCell; cellNum < lastHeaderCell; cellNum++) {
			row.put(headerRow.getCell(cellNum).getStringCellValue()
				   ,dataRow.getCell(cellNum).getStringCellValue());
		}
		return row;
	}

}

package com.md.soapui.custom.util.datadriver;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelDataDriver extends AbstractDataDriver {
	
	private static int DEFAULT_WORKSHEET_NUMBER = 0;
	
	private int firstCellNumber;
	private int lastCellNumber;
	private int firstHeaderCell;
	private int lastHeaderCell;
	private int lastDatarow;
	
	private File file;
	private XSSFWorkbook wb;
	private XSSFSheet sheet;
	private XSSFRow headerRow;
	
	public ExcelDataDriver() {
	}
	
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
		initializeDefaultValues();
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
	 * Load default sheet - Sheet at index 0.
	 */
	public void loadDataset() {
		setWorkbook();
		sheet = wb.getSheetAt(DEFAULT_WORKSHEET_NUMBER);
	}
	
	/**
	 * Load workbook at file set in constructor.
	 * Load sheet by sheet ID.
	 * 
	 * @param sheetNumber
	 */
	public void loadDataset(int sheetNumber) {
		setWorkbook();
		sheet = wb.getSheetAt(sheetNumber);
	}
	
	/**
	 * Load workbook at file set in constructor.
	 * Load sheet by sheet name.
	 * Note: the name must be unique (enforced by Excel UI)
	 * and the name must be correct including case.
	 * @param sheetName
	 */
	public void loadDataset(String sheetName) {
		setWorkbook();
		sheet = wb.getSheet(sheetName);
	}
	
	protected void setDatasetCountValues() {
		setFirstExistingCellNumber();
		setLastExistingCellNumber();
		setFirstHeaderCellNumber();
		setLastHeaderCellNumber();
		setLastDatarowNumber();
	}
	
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
	
	protected ArrayList<String> getLineWithoutHeaders (int rowNumber) {
		ArrayList<String> row = new ArrayList<>();
		XSSFRow dataRow = sheet.getRow(rowNumber);
		for(int cellNum = firstCellNumber; cellNum < lastCellNumber; cellNum++) {
			row.add(dataRow.getCell(cellNum).getStringCellValue());
		}
		return row;
	}
	
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

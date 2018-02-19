package com.md.soapui.custom.util.datasource;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.exceptions.InvalidOperationException;
import org.apache.poi.openxml4j.exceptions.NotOfficeXmlFileException;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelDataSource extends AbstractDataSource {
	
	//Default values
	private static final int DEFAULT_SHEET_INDEX = 0;
	private static final String DEFAULT_SHEET_NAME="Sheet1";
	
	private static final boolean DEFAULT_SELECT_SHEET_BY_NAME = false;
	private static final boolean DEFAULT_INITIALIZE_FIRST_CELL_NUMBER = true;
	private static final boolean DEFAULT_INITIALIZE_LAST_CELL_NUMBER = true;
	private static final boolean DEFAULT_INITIALIZE_LAST_DATAROW_NUMBER = true;
	
	//Private fields - access with getters / setters
	private int firstCellNumber;
	private int lastCellNumber;
	private int firstHeaderCell;
	private int lastHeaderCell;
	private int lastDatarow;
	private int sheetIndex;
	
	private String sheetName;
	
	private boolean selectSheetByName;
	private boolean initializeFirstCellNumber;
	private boolean initializeLastCellNumber;
	private boolean initializeLastDatarowNumber;
	
	private File file;
	private XSSFWorkbook wb;
	private XSSFSheet sheet;
	private XSSFRow headerRow;
	
	/**
	 * <p>Creates object ExcelDataDriver.</p>
	 * <p>Sets File file to input 'file' in parameters.</p>
	 * <p>Initializes standard values</p>
	 * <p>Header row: index 0</p>
	 * <p>First data row: index 1</p>
	 * <p>Return data with headers: false</p>
	 * 
	 * The following values are initialized:
	 * <li>
	 * Default sheet index (0)
	 * </li>
	 * <li>
	 * Default sheet name (Sheet1)
	 * </li>
	 * <li>
	 * Default select sheet by name boolean (false)
	 * </li>
	 * <li>
	 * Default first cell number (identified by looking for the first cell in the first line found)
	 * </li>
	 * <li>
	 * Default last cell number (identified by looking for the last cell in the range of rows defined)
	 * </li>
	 * <li>
	 * Default last data row (identified by looking for the last row with a non-null cell.
	 * </li>
	 * @param file
	 */
	public ExcelDataSource(File file) {
		this.file = file;
		setWorkbook();
		initializeDefaultValues();
		this.sheetIndex = DEFAULT_SHEET_INDEX;
		this.sheetName = DEFAULT_SHEET_NAME;
		this.selectSheetByName = DEFAULT_SELECT_SHEET_BY_NAME;
		this.initializeFirstCellNumber = DEFAULT_INITIALIZE_FIRST_CELL_NUMBER;
		this.initializeLastCellNumber = DEFAULT_INITIALIZE_LAST_CELL_NUMBER;
		this.initializeLastDatarowNumber = DEFAULT_INITIALIZE_LAST_DATAROW_NUMBER;
	}
	
	private void setWorkbook() {
		try {
			this.wb = new XSSFWorkbook(this.file);
		} catch (InvalidFormatException e) {
			new DataSourceException("This is not a valid format", e);
			e.printStackTrace();
		} catch (IOException e) {
			new DataSourceException("IO Exception", e);
			e.printStackTrace();
		} catch (NotOfficeXmlFileException e) {
			new DataSourceException("This is not an Office XML file.", e);
		} catch (InvalidOperationException e) {
			new DataSourceException(e);
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
		setHeaderRow();
	}
	
	@Override
	protected void setDatasetCountValues() {
		if(initializeFirstCellNumber) {
			initializeFirstExistingCellNumber();
			initializeFirstHeaderCellNumber();
		}
		if(initializeLastCellNumber) {
			initializeLastExistingCellNumber();
			initializeLastHeaderCellNumber();
		}
		if(initializeLastDatarowNumber) {
			initializeLastDatarowNumber();
		}
	}
	
	private void setHeaderRow() {
		headerRow = sheet.getRow(getHeaderRowNumber());
	}
	
	public ExcelDataSource getTheSheetWithName(String sheetName) {
		this.sheetName = sheetName;
		this.selectSheetByName = true;
		return this;
	}
	
	public ExcelDataSource getTheSheetWithIndex(int sheetIndex) {
		this.sheetIndex = sheetIndex;
		this.selectSheetByName = false;
		return this;
	}
	
	public ExcelDataSource startAtRow(int rowIndex) {
		setFirstDataRowNumber(rowIndex);
		return this;
	}
	
	public ExcelDataSource stopAtRow(int rowIndex) {
		this.lastDatarow = rowIndex;
		this.initializeLastDatarowNumber = false;
		return this;
	}
	
	public ExcelDataSource startAtColumn(int cellIndex) {
		this.firstCellNumber = cellIndex;
		this.initializeFirstCellNumber = false;
		return this;
	}
	
	public ExcelDataSource stopAtColumn(int cellIndex) {
		this.lastCellNumber = cellIndex;
		this.initializeLastCellNumber = false;
		return this;
	}
	
	public ExcelDataSource useHeadersAtRow(int rowIndex) {
		this.setHeaderRowNumber(rowIndex);
		this.setBasedOnHeaders(true);
		return this;
	}
	
	//Private methods for the automatic initialization
	private void initializeFirstExistingCellNumber() {
		int firstCellNumber = sheet.getRow(0).getFirstCellNum();
		if(firstCellNumber != 0) {
			for(int i = 1; i < sheet.getLastRowNum(); i++) {
				if(sheet.getRow(i) != null) {
					int testInt = sheet.getRow(i).getFirstCellNum();
					if(testInt == 0) {
						break;
					}
					if(testInt < firstCellNumber) {
						firstCellNumber = testInt;
					}
				}
			}
		}
		this.firstCellNumber = firstCellNumber;
	}
	
	private void initializeLastExistingCellNumber() {
		int lastCellNumber = sheet.getRow(0).getLastCellNum();
		for(int i = 1; i < sheet.getLastRowNum(); i++) {
			if(sheet.getRow(i) != null) {
				int testInt = sheet.getRow(i).getLastCellNum();
				if(testInt > lastCellNumber) {
					lastCellNumber = testInt;
				}
			}
		}
		this.lastCellNumber = lastCellNumber;
	}
	
	private void initializeFirstHeaderCellNumber() {
		firstHeaderCell = sheet.getRow(this.getHeaderRowNumber())
				               .getFirstCellNum();
	}
	
	private void initializeLastHeaderCellNumber() {
		lastHeaderCell = sheet.getRow(this.getHeaderRowNumber())
				              .getLastCellNum();
	}
	
	private void initializeLastDatarowNumber() {
		lastDatarow = sheet.getLastRowNum();
	}
	
	//Return Line of data methods.
	//This is the bottom line.
	@Override
	protected Map<Integer, String> getLineWithoutHeaders (int rowNumber) {
		Map<Integer, String> row = new HashMap<>();
		XSSFRow dataRow = sheet.getRow(rowNumber);
		if(dataRow != null) {
			for(int cellNum = firstCellNumber; cellNum < lastCellNumber; cellNum++) {
				if(dataRow.getCell(cellNum) != null) {
					row.put(cellNum, dataRow.getCell(cellNum).getStringCellValue());
				} else {
					row.put(cellNum, "");
				}
			}
		} 
		return row;
	}
	
	@Override
	protected HashMap<String,String> getLineWithHeaders(int rowNumber) {
		HashMap<String, String> row = new HashMap<>();
		XSSFRow dataRow = sheet.getRow(rowNumber);
		if(dataRow != null) {
			for(int cellNum = firstHeaderCell; cellNum < lastHeaderCell; cellNum++) {
				if(dataRow.getCell(cellNum) != null) {
					row.put(headerRow.getCell(cellNum).getStringCellValue()
						   ,dataRow.getCell(cellNum).getStringCellValue());
				} else {
					row.put(headerRow.getCell(cellNum).getStringCellValue(),"");
				}
		    }
		}
		return row;
	}
}
package com.md.soapui.custom.tests.util;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.md.soapui.custom.util.datasource.DataSource;
import com.md.soapui.custom.util.datasource.DataSourceException;
import com.md.soapui.custom.util.datasource.DataSourceType;

public class DataDriverTest {
	
	
	public void testDataDriverInitialization_Excel() throws DataSourceException {
		DataSource eDriver = new DataSource(DataSourceType.EXCEL_XLSX, new File("src/test/resources/testBook_Sheets_AndColumns.xlsx"));
		assert eDriver.getFile().getPath().equals("src/test/resources/testBook_Sheets_AndColumns.xlsx");
	}

}

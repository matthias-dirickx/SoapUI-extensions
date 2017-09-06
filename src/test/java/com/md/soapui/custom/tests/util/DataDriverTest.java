package com.md.soapui.custom.tests.util;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.md.soapui.custom.util.datadriver.DataDriver;
import com.md.soapui.custom.util.datadriver.DataDriverType;

public class DataDriverTest {
	
	@Test
	public void testDataDriverInitialization_Excel() {
		DataDriver eDriver = new DataDriver(DataDriverType.EXCEL_XLSX, new File("src/test/resources/testBook_Sheets_AndColumns.xlsx"));
		try {
			assert eDriver.getFile().getCanonicalPath().equals("src/test/resources/testBook_Sheets_AndColumns.xlsx");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

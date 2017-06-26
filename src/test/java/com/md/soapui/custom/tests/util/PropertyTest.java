package com.md.soapui.custom.tests.util;

import org.junit.Test;

import com.md.soapui.custom.util.Property;

public class PropertyTest {
	
	@Test
	public void propertyFileReaderTest() {
		Property property = new Property();
		assert property.getProperty("/testProperties", "testValue")
		           .equals("this/is/my/path-here");
	}
}
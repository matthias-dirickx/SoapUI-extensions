package com.md.soapui.custom.tests.actions;

import java.io.IOException;

import org.apache.xmlbeans.XmlException;
import org.junit.Test;

import com.eviware.soapui.impl.wsdl.WsdlProject;
import com.eviware.soapui.impl.wsdl.WsdlTestSuite;
import com.eviware.soapui.support.SoapUIException;
import com.md.soapui.custom.action.SearchItems;

public class SearchItemsActionTest {
	
	
	public void showItemsInUiTest() {
		WsdlProject p = null;
		
		try {
			p = new WsdlProject();
			p.addNewTestSuite("TestSuite1");
			WsdlTestSuite tsOne = p.getTestSuiteAt(0);
			tsOne.addNewTestCase("TestSuite1_TestCase1");
			tsOne.addNewTestCase("TestSuite1_TestCase2");
		} catch (XmlException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SoapUIException e) {
			e.printStackTrace();
		}
		
		new SearchItems().perform(p, null);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

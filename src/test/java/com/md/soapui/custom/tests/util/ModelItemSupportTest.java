package com.md.soapui.custom.tests.util;

import java.io.IOException;

import org.apache.xmlbeans.XmlException;
import org.junit.Test;

import com.eviware.soapui.impl.wsdl.WsdlProject;
import com.eviware.soapui.model.project.Project;
import com.eviware.soapui.support.SoapUIException;
import com.md.soapui.custom.util.soapui.ModelItemSupport;

public class ModelItemSupportTest {

	@Test
	public void GetAllChildrenTest() throws XmlException, IOException, SoapUIException {
		Project p = new WsdlProject();
		p.addNewTestSuite("TestSuiteOne");
		p.addNewTestSuite("TestSuiteTwo");
		ModelItemSupport mis = new ModelItemSupport();
		System.out.println(mis.getAllChildren(p.getProject()).toString());
	}
}

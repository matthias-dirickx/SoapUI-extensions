package com.md.soapui.custom.tests.util;

import java.io.IOException;

import org.apache.xmlbeans.XmlException;
import org.junit.Test;

import com.eviware.soapui.impl.wsdl.WsdlProject;
import com.eviware.soapui.model.project.Project;
import com.eviware.soapui.support.SoapUIException;
import com.md.soapui.custom.util.soapui.ModelItemSupport;

public class ModelItemSupportTest {
	
	private Project fixtureProject() {
		WsdlProject p = null;
		try {
			p = new WsdlProject("src/test/resources/fixtures/sampleProjectGooglemaps.xml");
		} catch (XmlException | IOException | SoapUIException e) {
			e.printStackTrace();
		}
		return p;
	}

	@Test
	public void GetAllChildrenTest() throws XmlException, IOException, SoapUIException {
		Project p = fixtureProject();
		
		ModelItemSupport mis = new ModelItemSupport();
		assert mis.getAllChildren(p.getProject()).size() == 131;
	}
	
	@Test
	public void GetAllChildrenWithTokenTest() throws XmlException, IOException, SoapUIException {
		Project p = fixtureProject();
		
		ModelItemSupport mis = new ModelItemSupport();
		System.out.println(mis.getAllChildren(p.getProject(), "autocomplete").size());
		assert mis.getAllChildren(p.getProject(), "autocomplete").size() == 4;
	}
	
	@Test
	public void GetAllChildrenWithTokenNotNormalizedCaseSensitiveTest() throws XmlException, IOException, SoapUIException {
		Project p = fixtureProject();
		
		ModelItemSupport mis = new ModelItemSupport();
		System.out.println(mis.getAllChildren(p.getProject(), "Sample Request",true,false).size());
		System.out.println(mis.getAllChildren(p.getProject(), "sample Request",true,false).size());
		assert mis.getAllChildren(p.getProject(), "Sample Request",true,false).size() == 5;
		assert mis.getAllChildren(p.getProject(), "sample Request",true,false).size() == 0;
	}
	
	@Test
	public void GetAllChildrenWithTokenNormalizedNotCaseSensitiveTest() throws XmlException, IOException, SoapUIException {
		Project p = fixtureProject();
		
		ModelItemSupport mis = new ModelItemSupport();
		System.out.println("sam plerequ est: "+mis.getAllChildren(p.getProject(), "sam plerequ est",false,true).size());
		assert mis.getAllChildren(p.getProject(), "sam plerequ est",false,true).size() == 5;
	}
}

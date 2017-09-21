package com.md.soapui.custom.tests.util;

import org.junit.Test;

import com.eviware.soapui.impl.wsdl.teststeps.HttpTestRequestStep;
import com.md.soapui.custom.util.soapui.ModelItemClassList;

public class ModelItemClassListTest {
	
	@Test
	public void modelItemClassListTest() {
		System.out.println(ModelItemClassList.HTTP_REQUEST_STEP.soapUIClass());
		assert ModelItemClassList.HTTP_REQUEST_STEP.soapUIClass().equals(HttpTestRequestStep.class);
	}

}

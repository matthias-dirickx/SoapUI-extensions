package com.md.soapui.custom.tests.util;

import org.junit.Test;

import com.eviware.soapui.impl.wsdl.teststeps.HttpTestRequestStep;
import com.md.soapui.custom.util.soapui.ModelItemList;

public class ModelItemClassListTest {
	
	@Test
	public void modelItemClassListTest() {
		System.out.println(ModelItemList.HTTP_REQUEST_STEP.soapUIClass());
		assert ModelItemList.HTTP_REQUEST_STEP.soapUIClass().equals(HttpTestRequestStep.class);
	}

}

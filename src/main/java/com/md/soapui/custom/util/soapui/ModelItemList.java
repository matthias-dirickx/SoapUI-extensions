package com.md.soapui.custom.util.soapui;

import com.eviware.soapui.model.ModelItem;
import com.eviware.soapui.model.project.Project;
import com.eviware.soapui.model.testsuite.TestSuite;
import com.eviware.soapui.impl.wsdl.testcase.WsdlTestCase;
import com.eviware.soapui.impl.wsdl.teststeps.HttpTestRequestStep;
import com.eviware.soapui.impl.wsdl.teststeps.JdbcRequestTestStep;
import com.eviware.soapui.impl.wsdl.teststeps.ManualTestStep;
import com.eviware.soapui.impl.wsdl.teststeps.RestTestRequestStep;
import com.eviware.soapui.impl.wsdl.teststeps.WsdlTestRequestStep;
import com.eviware.soapui.impl.wsdl.teststeps.AMFRequestTestStep;
import com.eviware.soapui.impl.wsdl.teststeps.WsdlGroovyScriptTestStep;
import com.eviware.soapui.impl.wsdl.teststeps.WsdlDelayTestStep;
import com.eviware.soapui.impl.wsdl.teststeps.WsdlGotoTestStep;
import com.eviware.soapui.impl.wsdl.teststeps.PropertyTransfersTestStep;

public enum ModelItemList {
	PROJECT(Project.class),
	TEST_SUITE(TestSuite.class),
	TEST_CASE(WsdlTestCase.class),
	HTTP_REQUEST_STEP(HttpTestRequestStep.class),
	JDBC_REQUEST_STEP(JdbcRequestTestStep.class),
	MANUAL_STEP(ManualTestStep.class),
	REST_REQUEST_STEP(RestTestRequestStep.class),
	WSDL_REQUEST_STEP(WsdlTestRequestStep.class),
	AMF_REQUEST_STEP(AMFRequestTestStep.class),
	GROOVY_STEP(WsdlGroovyScriptTestStep.class),
	DELAY_STEP(WsdlDelayTestStep.class),
	GOTO_STEP(WsdlGotoTestStep.class),
	PROPERTY_TRANSFER_STEP(PropertyTransfersTestStep.class);
	
	private Class<? extends ModelItem> soapUIClass;
	
	ModelItemList(Class<? extends ModelItem> soapUIClass) {
		this.soapUIClass = soapUIClass;
	}
	
	public Class<? extends ModelItem> soapUIClass() {
		return soapUIClass;
	}

}

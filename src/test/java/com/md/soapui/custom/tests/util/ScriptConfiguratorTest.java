package com.md.soapui.custom.tests.util;

import org.junit.Test;

import com.md.soapui.custom.util.groovy.ScriptConfigurator;

public class ScriptConfiguratorTest {
	@Test
	public void scriptConfiguratorTestEmptyConstructor() {
		ScriptConfigurator sc = new ScriptConfigurator();
		sc.setScriptRepository("src/test/resources/testConfig.properties");
		sc.getScriptRepository().equals("src/test/resources/testConfig.properties");
	}
}
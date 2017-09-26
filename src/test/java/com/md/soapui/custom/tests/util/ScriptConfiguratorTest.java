package com.md.soapui.custom.tests.util;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.groovy.control.MultipleCompilationErrorsException;
import org.junit.Test;

import com.md.soapui.custom.util.groovy.ScriptConfigurator;

public class ScriptConfiguratorTest {
	@Test
	public void scriptConfiguratorTestSimpleScript() {
		Map<String, Object> vars = new HashMap<>();
		vars.put("var", "Groovy");
		ScriptConfigurator sc = new ScriptConfigurator("testScript.groovy", vars);
	}
	
	@Test
	public void scriptConfigurationTestFailedCompilation() {
		Map<String, Object> vars = new HashMap<>();
		vars.put("var", "Groovy");
<<<<<<< HEAD
		new ScriptConfigurator("badTestScript.groovy", vars).getClass().isInstance(org.codehaus.groovy.control.MultipleCompilationErrorsException.class);
=======
		System.out.println(new ScriptConfigurator("badTestScript.groovy", vars).getClass().isInstance(org.codehaus.groovy.control.MultipleCompilationErrorsException.class));
		try {
			new ScriptConfigurator("badTestScript.groovy", vars).getClass().isInstance(org.codehaus.groovy.control.MultipleCompilationErrorsException.class);
		} catch (MultipleCompilationErrorsException e) {
			assert e.getClass().isInstance(MultipleCompilationErrorsException.class);
		}
>>>>>>> data-driver-excel
	}
	
	@Test
	public void createEmptyScriptConfigurator() {
		ScriptConfigurator sc = new ScriptConfigurator();
		sc.setScriptRepository("src/main/resources/groovy-scripts/");
		Map<String, Object> vars = new HashMap();
		vars.put("argument", "Groovy");
		
		sc.buildShell("testScript.groovy", vars);
		sc.execute();
	}
}
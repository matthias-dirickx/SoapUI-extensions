package com.md.soapui.custom.util.groovy;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import org.codehaus.groovy.control.CompilationFailedException;
 
import com.eviware.soapui.SoapUI;
 
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;

import com.md.soapui.custom.util.groovy.BindingCreator;
 
public class ScriptConfigurator {
 
    private static Script script = null;
    private String scriptRepository;
    private Properties prop = new Properties();
    private static String CONFIG_PATH = "/config.properties";
    private static String SCRIPT_REP_KEY = "scriptRepository";
    
    public void setScriptRepository(String rep) {
    	this.scriptRepository = rep;
    }
    public String getScriptRepository() {
    	return this.scriptRepository;
    }
    
    
    
    /**
     * For using default values, use constructor with String scriptName and variables.
     * @return script path
     */
    private String setScriptRepositoryPath() {
    	try {
            prop.load(getClass().getResourceAsStream(CONFIG_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop.getProperty(SCRIPT_REP_KEY);
    }
    
    
    
    /**
     * scriptRepository config file path and config key must be declared.
     * @param scriptName
     * @param vars
     */
    public void buildShell(String scriptName, Map<String, Object> vars) {
        SoapUI.log("Script Repository used: "+getScriptRepository());
        Binding binding = BindingCreator.fromMap(vars);
        GroovyShell shell = new GroovyShell(binding);
        try {
            SoapUI.log("Parsing Groovy from file on path \'"+getScriptRepository()+scriptName+"\'");
            script = shell.parse(new File(scriptRepository+scriptName));
        } catch (CompilationFailedException | IOException e) {
            e.printStackTrace();
        }
    }
 
    
    
    /**
     * Create ScriptConfigurator without settings.
     * Set config file, script repository path key.
     * Launch the Shell Build (with name and variables list)
     */
    public ScriptConfigurator() {
    }
    
    
    
    /**
     * Create ScriptConfigurator with the default values for:
     * - the repository config file address
     * - the default script library path key name
     * 
     * @param name
     * @param vars
     */
    public ScriptConfigurator(String name, Map<String, Object> vars){
    	setScriptRepository(setScriptRepositoryPath());
    	buildShell(name, vars);
    }
 
    public void execute() {
        script.run();
        SoapUI.log("~~~~Run done...~~~~");
    }
}

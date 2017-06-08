package com.md.soapui.custom.util.groovy;

import java.io.File;
import java.io.IOException;
import java.util.Map;
 
import org.codehaus.groovy.control.CompilationFailedException;
 
import com.eviware.soapui.SoapUI;
 
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
 
import com.md.soapui.custom.util.Property;
import com.md.soapui.custom.util.groovy.BindingCreator;
 
public class ScriptConfigurator {
 
    private static Script script = null;
    private String scriptRepository;
    private Property property = new Property();
    
    public void setScriptRepository(String rep) {
    	this.scriptRepository = rep;
    }
    
    public String getScriptRepository() {
    	return this.scriptRepository;
    }
 
    public ScriptConfigurator(String name, Map<String, Object> vars){
        setScriptRepository(property.getProperty("/config.properties", "scriptRepository"));
        SoapUI.log("Script Repository used: "+scriptRepository);
 
        Binding binding = BindingCreator.fromMap(vars);
 
        GroovyShell shell = new GroovyShell(binding);
 
        try {
            SoapUI.log("Parsing Groovy from file on path \'"+getScriptRepository()+name+"\'");
            script = shell.parse(new File(scriptRepository+name));
        } catch (CompilationFailedException | IOException e) {
            e.printStackTrace();
        }
    }
 
    public void execute() {
        script.run();
        SoapUI.log("~~~~Run done...~~~~");
    }
}

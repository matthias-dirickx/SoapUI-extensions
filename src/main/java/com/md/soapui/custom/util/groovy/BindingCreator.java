package com.md.soapui.custom.util.groovy;

import java.util.Map;

import groovy.lang.Binding;
 
public class BindingCreator {
 
    public static Binding fromMap(Map<String, Object> vars) { 
 
        Binding binding = new Binding();
 
        for (Map.Entry<String, Object> entry : vars.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            binding.setVariable(key, value);
        }
 
        return binding;
 
    }
}
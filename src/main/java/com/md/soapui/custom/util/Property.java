package com.md.soapui.custom.util;

import java.io.IOException;
import java.util.Properties;
 
public class Property {
 
        public String getProperty(String path, String key) {
 
            String value = null;
 
            Properties prop = new Properties();
            try {
                prop.load(getClass().getResourceAsStream(path));
            } catch (IOException e) {
                e.printStackTrace();
            }
 
            value = prop.getProperty(key);
 
            return value;
        }
    }
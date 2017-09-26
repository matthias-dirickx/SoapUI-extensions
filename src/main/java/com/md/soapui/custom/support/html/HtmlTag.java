package com.md.soapui.custom.support.html;

import java.util.HashMap;
import java.util.Map;

public class HtmlTag {
	private final String SPACE = " ";
	String tag;
	String attributeString;
	HashMap<String,String> attributes;
	
	public HtmlTag(String tag) {
		this.tag = tag;
	}
	
	public void addAttribute(String attributeName, String attributeValue) {
		attributes.put(attributeName, attributeValue);
	}
	
	public String start() {
		StringBuilder sb = null;
		setAttributesString();
		return sb.append("<")
				 .append(tag)
				 .append(attributeString)
				 .append(">")
				 .toString();
	}
	
	public String end() {
		return "</" + tag + ">";
	}
	
	public String startWithoutContent() {
		StringBuilder sb = null;
		setAttributesString();
		return sb.append("<")
				 .append(tag)
				 .append(attributeString)
				 .append("/>")
				 .toString();
	}
	
	private void setAttributesString() {
		StringBuilder sb = null;
		String s = "";
		if(!attributes.isEmpty()) {
			for(Map.Entry<String, String> att: attributes.entrySet()) {
				sb.append(SPACE)
				  .append(att.getKey())
				  .append("=")
				  .append("\""+att.getValue()+"\"");
				s = sb.toString();
			}
		}
		this.attributeString = s;
	}
}

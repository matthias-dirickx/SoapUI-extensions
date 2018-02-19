package com.md.soapui.custom.support.html.simplebuilder;

import java.util.HashMap;
import java.util.Map;

public class HtmlTag {
	private final String SPACE = " ";
	private String tag;
	private HashMap<String,String> attributes;
	
	public HtmlTag(String tag) {
		this.tag = tag;
		this.attributes = new HashMap<>();
	}
	
	public void addAttribute(String attributeName, String attributeValue) {
		attributes.put(attributeName, attributeValue);
	}
	
	public String start() {
		StringBuilder sb = new StringBuilder();
		return sb.append("<")
				 .append(tag)
				 .append(getAttributeString())
				 .append(">")
				 .toString();
	}
	
	public String end() {
		return "</" + tag + ">";
	}
	
	public String startWithoutContent() {
		StringBuilder sb = new StringBuilder();
		return sb.append("<")
				 .append(tag)
				 .append(getAttributeString())
				 .append("/>")
				 .toString();
	}
	
	private String getAttributeString() {
		StringBuilder sb = new StringBuilder();
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
		return s;
	}
}

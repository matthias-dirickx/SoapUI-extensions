package com.md.soapui.custom.support.html.simplebuilder;

public class HtmlSimpleElement extends HtmlTag {
	private String content;
	
	public HtmlSimpleElement(String tag, String content) {
		super(tag);
		this.content = escapeLight(content);
	}
	
	public HtmlSimpleElement(String tag) {
		super(tag);
		this.content = null;
	}
	
	public String getHtmlString() {
		if(this.content != null) {
			return this.start()+content+this.end();
		} else {
			return this.startWithoutContent();
		}
	}
	
	private String escapeLight(String s) {
		return s.replace("&", "&amp;")
				.replace("<", "&lt;")
	            .replace(">", "&gt;")
	            .replace("\"", "&quot;")
	            .replace("'", "&apos;")
	            .replace("\n", "</br>");
	}
}

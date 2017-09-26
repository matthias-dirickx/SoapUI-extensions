package com.md.soapui.custom.support.html.simplebuilder;

import java.util.ArrayList;
import java.util.List;

public class HtmlComplexElement extends HtmlTag {
	private List<String> htmlElementList;
	
	public HtmlComplexElement(String tag) {
		super(tag);
		this.htmlElementList = new ArrayList<>();
	}
	
	public HtmlComplexElement add(HtmlComplexElement hce) {
		htmlElementList.add(hce.getHtmlString());
		return this;
	}
	
	public HtmlComplexElement add(HtmlSimpleElement hse) {
		htmlElementList.add(hse.getHtmlString());
		return this;
	}
	
	public HtmlComplexElement add(String content) {
		htmlElementList.add(content);
		return this;
	}
	
	public String getHtmlString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.start());
		
		for(String html : htmlElementList){
			sb.append(html);
		}
		
		sb.append(this.end());
		
		return sb.toString();
	}
}

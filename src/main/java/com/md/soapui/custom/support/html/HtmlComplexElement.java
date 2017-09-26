package com.md.soapui.custom.support.html;

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
		for(String html : htmlElementList){
			sb.append(html);
		}
		return sb.toString();
	}
	
}

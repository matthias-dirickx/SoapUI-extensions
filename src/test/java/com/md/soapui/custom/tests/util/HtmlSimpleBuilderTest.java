package com.md.soapui.custom.tests.util;

import org.junit.Test;

import com.md.soapui.custom.support.html.simplebuilder.HtmlComplexElement;
import com.md.soapui.custom.support.html.simplebuilder.HtmlPageBuilder;
import com.md.soapui.custom.support.html.simplebuilder.HtmlSimpleElement;
import com.md.soapui.custom.support.html.simplebuilder.HtmlTag;

public class HtmlSimpleBuilderTest {
	
	@Test
	public void htmlTagTest() {
		HtmlTag tag = new HtmlTag("div");
		tag.addAttribute("style", "border:3px");
		tag.addAttribute("class", "tagTest");
		assert tag.start().equals("<div style=\"border:3px\" class=\"tagTest\">");
		assert tag.end().equals("</div>");
	}
	
	@Test
	public void htmlTagTestWithoutContentTest() {
		HtmlTag tag = new HtmlTag("div");
		assert tag.startWithoutContent().equals("<div/>");
	}
	
	@Test
	public void htmlSimpleElementWithContentTest() {
		HtmlSimpleElement se = new HtmlSimpleElement("div", "some content!\n <>&\"'");
		se.addAttribute("attributeName", "andTheValue");
		System.out.println(se.getHtmlString());
		assert se.getHtmlString().equals("<div attributeName=\"andTheValue\">some content!</br> &lt;&gt;&amp;&quot;&apos;</div>");
	}
	
	@Test
	public void htmlSimpleElementWithoutContentTest() {
		HtmlSimpleElement se = new HtmlSimpleElement("div");
		se.addAttribute("attributeName", "andTheValue");
		assert se.getHtmlString().equals("<div attributeName=\"andTheValue\"/>");
	}
	
	@Test
	public void htmlComplexElementTest() {
		HtmlComplexElement root = new HtmlComplexElement("root");
		
		HtmlComplexElement ce = new HtmlComplexElement("subRoot");
		ce.addAttribute("anattributeName", "anAttributeValue");
		
		HtmlSimpleElement se = new HtmlSimpleElement("simple", "this is some content");
		se.addAttribute("anAttribute", "attributeValue");
		
		ce.add(se);
		
		root.add(ce);
		
		root.add("an extra line of content");
		
		assert root.getHtmlString().equals("<root>"
				                              +"<subRoot anattributeName=\"anAttributeValue\">"
				                                  +"<simple anAttribute=\"attributeValue\">"
				                                  +"this is some content"
				                                  +"</simple>"
				                              +"</subRoot>"
				                              +"an extra line of content"
				                          +"</root>");
	}
	
	@Test
	public void htmlPageBuilderTest() {
		HtmlComplexElement root = new HtmlComplexElement("root");
		
		HtmlComplexElement ce = new HtmlComplexElement("subRoot");
		ce.addAttribute("anattributeName", "anAttributeValue");
		
		HtmlSimpleElement se = new HtmlSimpleElement("simple", "this is some content");
		se.addAttribute("anAttribute", "attributeValue");
		
		ce.add(se);
		
		root.add(ce);
		
		root.add("an extra line of content");
		
		HtmlPageBuilder pb = new HtmlPageBuilder("this is the title");
		pb.setHeadStyle("styling in css")
		  .setBody(root.getHtmlString());
		
		assert pb.getPageAsText().equals("<html>"
				                        +"<head>"
				                        +"<title>this is the title</title>"
				                        +"<style>styling in css</style>"
				                        +"</head>"
				                        +"<body>"
				                        +"<root>"
				                              +"<subRoot anattributeName=\"anAttributeValue\">"
				                                  +"<simple anAttribute=\"attributeValue\">"
				                                  +"this is some content"
				                                  +"</simple>"
				                              +"</subRoot>"
				                              +"an extra line of content"
				                          +"</root>"
				                          +"</body>"
				                          +"</html>");
	}
}

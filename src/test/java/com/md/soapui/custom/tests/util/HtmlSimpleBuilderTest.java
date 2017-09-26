package com.md.soapui.custom.tests.util;

import org.junit.Test;

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
	public void htmlTagTestWithoutContent() {
		HtmlTag tag = new HtmlTag("div");
		tag.addAttribute("style", "border:3px");
		tag.addAttribute("class", "tagTest");
		assert tag.startWithoutContent().equals("<div style=\"border:3px\" class=\"tagTest\"/>");
	}

}

package com.md.soapui.custom.support.html;

public class HtmlPageBuilder {
	private String headStyle;
	private String headTitle;
	private String bodyAsText;
	
	/**
	 * Create a simple string for a HTML document.
	 * Create with title.
	 * Add style (CSS) - tags are added here, so only add the css.
	 * Add body - outer body tags are added here.
	 * @param headTitle
	 */
	public HtmlPageBuilder(String headTitle) {
		this.headTitle = headTitle;
	}
	
	public void setHeadStyle(String headStyle) {
		this.headStyle = headStyle;
	}
	
	public void setHeadTitle(String headTitle) {
		this.headTitle = headTitle;
	}
	
	public void setBody(String bodyAsText) {
		this.bodyAsText = bodyAsText;
	}
	
	public String getPageAsText() {
		return composeHtmlPage();
	}
	
	private String composeHtmlPage() {
		StringBuilder htmlPage = null;
		return
		htmlPage.append("<html>")
		        .append("<head>")
		        .append("<title>")
		        .append(headTitle)
		        .append("</title>")
		        .append("<style>")
		        .append(headStyle)
		        .append("</style>")
		        .append("</head>")
		        .append("<body>")
		        .append(bodyAsText)
		        .append("</body>")
		        .append("</html>")
		        .toString();
	}
}

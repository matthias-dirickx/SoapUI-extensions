/**
 @Author : Matthias Dirickx @ Credoc Services
 @Description : Collecting statuses, counting the statuses, calculating relative percentages and showing the results as one integrated HTML file.
  
  * This script retrieves the static statuses from all items in a project and then exports those results to a simple html file.
  * There is no extra css file or other stuff included. So you can happily pass around the html without worrying about making available additional resources.
  * Assumptions and Remarks:
  *   - there is a variable testDataPath
  *   - The variable testDataPath is set as JAVA String (backslashes are doubled because of escape function)
  *   - The path ends with a double backslash
  *   - The path results are written to the 'Execution Results' folder
  *   - When there is no testDataPath variable the report is written to C:\SoapUI Reports. A message warns when the script defaults to this location.
  *   - Name of file is set automatically to soapUIProjectName+date time stamp
  *   - When all steps are passed, the test case is VALID
  *   - When one step fails, the test case is set to FAILED
  *   - In any other case the test case result is set to UNKNOWN
  *   - The same logic for the Test Suites applies as it does for the Test Cases
  *
  * Philosophy:
  *   - The results are collected into one file, and one file only
  *   - No javascripts, or fancy stuff
  *   - Interaction is minimal - the document is interlinked, but there should not be any external references
  *   - Exception to this rule is the Fednot logo, which is retrieved from a public webspace

 @Usage : Make the script available through a SoapUi interface implementation or put it in a project tear-down script.
 */
import com.eviware.soapui.model.ModelItem
import com.eviware.soapui.support.UISupport
import com.eviware.soapui.SoapUI
import java.awt.Desktop

///////////////////////////////////////////////////
//    Essential Class Variables                 //
/////////////////////////////////////////////////

statusCounterMap = new HashMap<String, Map<String, Integer>>()



///////////////////////////////////////////////////
//    Launch Script                             //
/////////////////////////////////////////////////

writeReportToDisk(buildHtmlPage())



///////////////////////////////////////////////////
//    HELPER METHODS                            //
/////////////////////////////////////////////////

/////////////////////ModelItem tests/////////////////////////

private boolean isAssertable(Class<?> claz) {
    boolean isAssertable = com.eviware.soapui.model.testsuite.Assertable.class.isAssignableFrom(claz)
    return isAssertable
}

private boolean isTestSuite(Class<?> claz) {
  boolean isAssertable = com.eviware.soapui.model.testsuite.TestSuite.class.isAssignableFrom(claz)
    return isAssertable
}

private boolean isTestCase(Class<?> claz) {
  boolean isAssertable = com.eviware.soapui.model.testsuite.TestCase.class.isAssignableFrom(claz)
    return isAssertable
}

private boolean isTestStep(Class<?> claz) {
  boolean isAssertable = com.eviware.soapui.model.testsuite.TestStep.class.isAssignableFrom(claz)
    return isAssertable
}

private boolean isTestAssertion(Class<?> claz) {
  boolean isAssertable = com.eviware.soapui.model.testsuite.TestAssertion.class.isAssignableFrom(claz)
    return isAssertable
}

/////////////////////Derive Test Status From Upper Level/////
private String deriveTestCaseStatus(tc) {
  def tsStatusList = []
  def tcStatus
  testStepList = tc.getTestStepList()
  testStepList.each {
    if(isAssertable(it.class) && it.getAssertionStatus()!=null) {
      if(!it.isDisabled()) {
        tsStatusList.add(it.getAssertionStatus().toString())
        }
      }
    }
  if(tsStatusList.contains('FAILED')) {
    tcStatus = 'FAILED'
  } else if(tsStatusList.every{it.equals('VALID')}){
      tcStatus = 'VALID'
     } else {
      tcStatus = 'UNKNOWN'
     }
  return tcStatus
}

private String deriveTestSuiteStatus(tcStatusCollection) {
    def testSuiteStatus
    if(tcStatusCollection.contains('FAILED')) {
    tcStatus = 'FAILED'
    } else if(tcStatusCollection.every{it.equals('VALID')}){
          tcStatus = 'VALID'
      } else {
             tcStatus = 'UNKNOWN'
        }
  return tcStatus
}

/////////////////////Get CSS class for status///////////////

private String getStatusClass(String status) {
  statusClass = null
  switch (status) {
    case 'VALID' : statusClass = 'pass'
    break;
    case 'FAILED' : statusClass = 'fail'
    break;
    default : statusClass = 'defaultStatus'
  }
  return statusClass
}



///////////////////////////////////////////////////
//    Get Counts Methods                        //
/////////////////////////////////////////////////

private Map<String, Integer> initialiazeStatusCounterMap() {
  Map<String, String> map = new HashMap<>()
  map.put("VALID", 0)
  map.put("FAILED", 0)
  map.put("UNKNOWN", 0)
  map.put("Disabled", 0)
  return map
  SoapUI.log("initialiazed map " + map.toString())
}

private void initializeGlobalStatusCounterMap() {
  Map<String, Integer> testSuiteMap = initialiazeStatusCounterMap()
  Map<String, Integer> testCaseMap = initialiazeStatusCounterMap()
  Map<String, Integer> testTestMap = initialiazeStatusCounterMap()
  Map<String, Integer> testAssertionMap = initialiazeStatusCounterMap()

  statusCounterMap.put("Test Suite", testSuiteMap)
  statusCounterMap.put("Test Case", testCaseMap)
  statusCounterMap.put("Test Step", testTestMap)
  statusCounterMap.put("Test Assertion", testAssertionMap)
}

private void updateStatusCounterMap(String item, String itemStatus, Integer increment) {
  if(!itemStatus.equals(null)) {
  Integer count = statusCounterMap.get(item).get(itemStatus)
  count = count + increment
  statusCounterMap.get(item).put(itemStatus, count)
  }
}

private void cascadeDisabledStatus(ModelItem item) {
  Integer inc = 1
  status = "Disabled"
  if(isTestSuite(item.getClass())) {
    updateStatusCounterMap("Test Suite", status, inc)
    item.getTestCaseList().each {
      cascadeDisabledStatus(it)
      }
    } else if(isTestCase(item.getClass())) {
        updateStatusCounterMap("Test Case", status, inc)
        item.getTestStepList().each {
          cascadeDisabledStatus(it)
        }
    } else if(isTestStep(item.getClass())) {
        updateStatusCounterMap("Test Step", status, inc)
        if(isAssertable(item.getClass())) {
           item.getAssertionList().each {
           updateStatusCounterMap("Test Assertion", status, inc)
           }
        }
    }
}

private void setStatusCounts() {

    testSuites = project.getTestSuiteList()
    def testCaseStatusList

    testSuites.each {
      testCaseStatusList = new ArrayList<String>()
      if(!it.isDisabled()) {
        testCases = it.getTestCaseList()
        testCases.each {
          if(!it.isDisabled()) {
            testCaseStatus = deriveTestCaseStatus(it)
            testCaseStatusList.add(testCaseStatus)
            updateStatusCounterMap("Test Case", testCaseStatus, 1)
            testSteps = it.getTestStepList()
            testSteps.each {
              if(!it.isDisabled()) {
                if(isAssertable(it.getClass())) {
                  testStepStatus = it.getAssertionStatus().toString()
                  updateStatusCounterMap("Test Step", testStepStatus, 1)
                  testAssertions = it.getAssertionList()
                  testAssertions.each {
                    if(!it.isDisabled()) {
                      assertionStatus = it.getStatus().toString()
                      updateStatusCounterMap("Test Assertion", assertionStatus, 1)
                    } else {
                      updateStatusCounterMap("Test Assertion", "Disabled", 1)
                    }//End else for assertion disabled
                  }//End for each for test assertions
                }//End of if for test step assertable
              } else {
                cascadeDisabledStatus(it)
              }//End of else for test step disabled
            }//End for each for test steps
          } else {
            cascadeDisabledStatus(it)
          }//End of else for test case disabled
        }//End for each for test cases
        testSuiteStatus = deriveTestSuiteStatus(testCaseStatusList)
        updateStatusCounterMap("Test Suite", testSuiteStatus, 1)
      } else {
          cascadeDisabledStatus(it)
        }//End of else for test suite disabled
    }//End for each for test suites 
}//End of method setStatusCounts

private Integer getStatusPercentage(String status, HashMap<String, Integer> map) {
    Integer sum = 0
    Integer statusValue = 0
    sum = map.get("VALID") + map.get("FAILED") + map.get("UNKNOWN")
    perc = 0

    statusValue = map.get(status)
    
    if(sum!=0 && statusValue!=0) {
      perc = (statusValue/sum*100)
    } else if (statusValue==0) {
      perc = 0
    } else {
      perc = 0
    }

    return 100-perc
}



///////////////////////////////////////////////////
//    Build Table Methods                       //
/////////////////////////////////////////////////

//////////NUMBERS///////////////////////////////////////////////////////

private String buildNumberTableRow(String item) {
  StringBuilder row = new StringBuilder()
  Integer itemPass = statusCounterMap.get(item).get("VALID")
  Integer itemFail = statusCounterMap.get(item).get("FAILED")
  Integer itemUnknown = statusCounterMap.get(item).get("UNKNOWN")
  Integer itemTotal = itemPass + itemFail + itemUnknown
  Integer itemPassPerc = getStatusPercentage("VALID", statusCounterMap.get(item))
  Integer itemFailPerc = getStatusPercentage("FAILED", statusCounterMap.get(item))
  Integer itemUnknownPerc = getStatusPercentage("UNKNOWN", statusCounterMap.get(item))

  row.append("<tr>")
  row.append("<th>$item</th>")
  row.append("<td>")
  row.append("<div class=\"centerOverLay\">$itemPass</div>")
  row.append("<div class=\"coverFade\" style=\"width:$itemPassPerc%\">&nbsp;</div>")
  row.append("<div class=\"greenFadeBar\">&nbsp;</div>")
  row.append("</td>")
  row.append("<td>")
  row.append("<div class=\"centerOverLay\">$itemFail</div>")
  row.append("<div class=\"coverFade\" style=\"width:$itemFailPerc%\">&nbsp;</div>")
  row.append("<div class=\"redFadeBar\">&nbsp;</div>")
  row.append("</td>")
  row.append("<td>")
  row.append("<div class=\"centerOverLay\">$itemUnknown</div>")
  row.append("<div class=\"coverFade\" style=\"width:$itemUnknownPerc%\">&nbsp;</div>")
  row.append("<div class=\"orangeFadeBar\">&nbsp;</div>")
  row.append("</td>")
  row.append("<td class = \"defaultStatus\">$itemTotal</td>")
  row.append("</tr>")

  return row.toString()
}

private String getNumberTableData() {
  initializeGlobalStatusCounterMap()
  setStatusCounts()
  SoapUI.log(statusCounterMap.toString())

  def StringBuilder number = new StringBuilder()

  number.append("<table class=\"numOverview\">")
  number.append("<caption>Numeric Overview")
  number.append("<div class=\"testCaseDescription\"><b>Info: </b>This table shows actual counts of the statuses. VALID, FAILED and UNKNOWN are shown here. The disabled items are not counted. The colored bar is an indicator for the relative amount as a percentage. The relative amount is weighted against the sum of the items with status VALID, FAILED and UNKNOWN.</div>")
  number.append("</caption>")
  number.append("<tr>")
  number.append("<th>Level</th>")
  number.append("<th>Passed</th>")
  number.append("<th>Failed</th>")
  number.append("<th>Unknown</th>")
  number.append("<th>Total</th>")
  number.append("</tr>")
  number.append(buildNumberTableRow("Test Suite"))
  number.append(buildNumberTableRow("Test Case"))
  number.append(buildNumberTableRow("Test Step"))
  number.append(buildNumberTableRow("Test Assertion"))
  number.append("</table>")
}

///////////////////////////////////////////////////////////////////////



//////////DETAILS//////////////////////////////////////////////////////

private String getDetailTableData() {

def StringBuilder detail = new StringBuilder()
//get test suites
testSuites = project.getTestSuiteList()

//iterate over suites
testSuites.each {
  
  if(!it.isDisabled()) {
  
      testsuiteName = it.getName()
      detail.append("<h3><a name=\"$testsuiteName\">Test Suite: $testsuiteName</a></h3>")

         //get test cases
      testCases = it.getTestCaseList()
  
      //iterate over test cases
      testCases.each {

          if(!it.isDisabled()) {
           testcaseName = it.getName()
           testcaseDescription = it.getDescription()
           detail.append("<h4><a name=\"$testcaseName\">Test Case: $testcaseName</a></h4>")
           if(!(testcaseDescription.equals(null) || testcaseDescription.equals(""))) {
               detail.append("<div class=\"testCaseDescription\"><b>Test Case Description:</b></br>$testcaseDescription</div>")
           } else {
                 detail.append("<div class=\"testCaseDescription\"><b>Test Case Description:</b></br><i>There is no description available for this test case.</i></div>")
           }
           testSteps = it.getTestStepList()
           testSteps.each {
            teststepName = it.label
            teststepType = it.getClass().getSimpleName()
            detail.append("<table class=\"testDetail\">")
            detail.append("<colgroup>")
            detail.append("<col class=\"type\"/>")
            detail.append("<col class=\"name\"/>")
            detail.append("<col class=\"status\"/>")
            detail.append("<col class=\"error\"/>")
            detail.append("</colgroup>")
            detail.append("<caption><i>Step name: </i>$teststepName<span style=\"font-weight:normal;font-size:italic;\"> ($teststepType)</span></caption>")
            detail.append("<tr>")
            detail.append("<th>Type</th>")
            detail.append("<th>Name</th>")
            detail.append("<th>Status</th>")
            detail.append("<th>Errors</th>")
            detail.append("</tr>")

             if(!it.isDisabled()) {

                          //assertable content check
                 if(isAssertable(it.class)) {
                     testStepStatus = it.getAssertionStatus()
                     assertionList = it.getAssertionList()
                      
                      //testAssertion list
                      if(assertionList.size()>0){
                        assertionList.each {
                              assertionName = it.label
                              assertionStatus = it.status
                              assertionType = it.getClass().getSimpleName()
                              StringBuilder assertionInfoSb = new StringBuilder()
                              it.getErrors().each {
                               assertionInfoSb.append(it.getMessage().toString())
                              }
                              assertionInfo = assertionInfoSb.toString()
                              assertionStatusHtmlClass = getStatusClass(assertionStatus.toString())
                          
                              detail.append("<tr>")
                              detail.append("<td>$assertionType</td>")
                              detail.append("<td>$assertionName</td>")
                              detail.append("<td class=\"$assertionStatusHtmlClass\">$assertionStatus</td>")
                              detail.append("<td>$assertionInfo</td>")
                              detail.append("</tr>")
                          }
                      //If no assertions are available    
                      } else {
                            detail.append("<tr><td colspan=\"4\">There are no assertions available for this test step.</td></tr>")
                        }
                 //If the test step is not assertable
                 }  else {
                       detail.append("<tr><td colspan=\"4\">This test step is not assertable.</td></tr>")
                   }
             }
             detail.append("</table>")
           }
          }
      }
  }
}
return detail
}

///////////////////////////////////////////////////////////////////////



//////////OVERVIEW/////////////////////////////////////////////////////

private String getOverviewTableData() {

def StringBuilder overview = new StringBuilder()
overview.append("<table class=\"listOverview\">")
overview.append("<caption>Test Suites &amp; Test Cases</caption>")
overview.append("<tr>")
overview.append("<th>Test Suite Name</th>")
overview.append("<th>Test Case Name</th>")
overview.append("<th>Test Case Status</th>")
overview.append("</tr>")
//get test suites
testSuites = project.getTestSuiteList()

//iterate over suites
testSuites.each {
  
  //Reset test case status collector
  tcStatusCollector = []
  if(!it.isDisabled()) {
  
      testsuiteName = it.label

         //get test cases
      testCases = it.getTestCaseList()
  
      //iterate over test cases
      firstTestCase = true
      Integer testCaseCount=0
      testCases.each {
        if(!it.isDisabled()) {
          testCaseCount++
        }
      }

      testCases.each {
          if(!it.isDisabled()) {
           testcaseName = it.label
           
           //get the test case status
           testcaseStatus = deriveTestCaseStatus(it)
           tcStatusCollector.add(testcaseStatus)
           statusHtmlClass = getStatusClass(testcaseStatus)

           overview.append("<tr>")
           if(firstTestCase) {
            overview.append("<td class=\"hooverable\" rowspan=\"$testCaseCount\"><a href=\"#$testsuiteName\"><div class=\"linkBlock\">$testsuiteName</div></a></td>")
           }
           overview.append("<td class=\"hooverable\"><a href=\"#$testcaseName\"><div class=\"linkBlock\">$testcaseName</div></a></td>")
           overview.append("<td class=\"$statusHtmlClass\">$testcaseStatus</td>")
           overview.append("</tr>")
           
           firstTestCase = false
             }
        }
        testSuiteStatus = deriveTestSuiteStatus(tcStatusCollector)
    }
}
overview.append("</table>")
return overview
}

///////////////////////////////////////////////////////////////////////////////////



///////////////////////////////////////////////////
//    Build Page Methods                        //
/////////////////////////////////////////////////

private String buildHtmlPage() {
  StringBuilder htmlPage = new StringBuilder()
     //Start page + markup (css)
     htmlPage.append("<html>")
         htmlPage.append("<head>")
             htmlPage.append("<style>")
                 htmlPage.append("body {counter-reset: h3;}")
                 htmlPage.append("div.parentContainer {width: 70%;margin-left: auto;margin-right: auto; margin-top: 100px;flex:1;}")
                 htmlPage.append("div.banner {position:fixed;top: 0;bottom: 0;left: 0;right: 0;background: linear-gradient(to right, DodgerBlue, white, DodgerBlue); height: 90px; width: 100%; z-index: 1;}")
                 htmlPage.append("div.defaultContainer {width: 100%;overflow: auto;}")
                 htmlPage.append("img.headerImage {position:absolute;display:table;top: 0;bottom: 0;left: 0;right: 0;height: 60px;width: auto;margin:auto;margin-left: auto;margin-right: auto;z-index:2;}")
                 htmlPage.append("div.testCaseDescription {border-radius: 10px;background-color: #cccccc;padding: 8px;margin-bottom: 10px;font-weight:normal;}")
                 htmlPage.append("h1 {text-align: center;font-weight: bold;}")
                 htmlPage.append("h3 {text-indent: 20px;counter-reset: h4;}")
                 htmlPage.append("h4 {text-indent: 40px;}")
                 htmlPage.append("h5 {text-indent: 60px;}")
                 htmlPage.append("h3:before {content:counter(h3,decimal)\"  \";counter-increment: h3;}")
                 htmlPage.append("h4:before {content:counter(h3,decimal)\".\"counter(h4,decimal)\"  \";counter-increment: h4;}")
                 htmlPage.append("h5:before {content:counter(h3,decimal)\".\"counter(h4,decimal)\".\"counter(h5,decimal)\"  \";}")
                 htmlPage.append("table {font-family: arial, sans-serif;border-collapse: collapse;width: 100%;min-width:400px;margin-bottom:20px;border:1px solid;}")
                 htmlPage.append("table.testDetail {}")
                 htmlPage.append("table.testDetail col.type {width:150px;}")
                 htmlPage.append("table.testDetail col.name {width:350px;}")
                 htmlPage.append("table.testDetail col.status {width:120px;}")
                 htmlPage.append("table.testDetail col.error {}")
                 htmlPage.append("table.listOverview {width: auto;min-width: 33%;max-width: 66%;border:none;display:inline-block;}")
                 htmlPage.append("table.numOverview {max-width:30%;vertical-align: top;border:none; float: right;}")
                 htmlPage.append("caption {text-align: left;font-size: 14px;font-weight: bold;padding-bottom: 8px}")
                 htmlPage.append("tr {height: 100%;}")
                 htmlPage.append("td, th {padding: 8px;border: 1px solid black;font-size: 12px;position:relative;}")
                 htmlPage.append("td.pass {background-color:green;color:white;font-weight: bold;text-align: center;}")
                 htmlPage.append("td.fail {background-color: red;color: white;font-weight: bold;text-align: center;}")
                 htmlPage.append("td.defaultStatus {text-align: center;}")
                 htmlPage.append("td.hooverable{}")
                 htmlPage.append("table.numOverview th {}")
                 htmlPage.append("table.numOverview td {width:18%;padding-left:0px;padding-right:0px;}")
                 htmlPage.append("div.linkBlock {width:100%;height:100%;padding:0px;display:inline-block;}")
                 htmlPage.append("div.greenFadeBar {position:absolute;top: 0;bottom: 0;left: 0;right: 0;height:100%;background:linear-gradient(to right, white, green 85%);z-index: -2;width: 100%;}")
                 htmlPage.append("div.redFadeBar {position:absolute;top: 0;bottom: 0;left: 0;right: 0;height:100%;background: linear-gradient(to right, white, red 25%);z-index: -2;width: 100%;}")
                 htmlPage.append("div.orangeFadeBar {position:absolute;top: 0;bottom: 0;left: 0;right: 0;height:100%;background: linear-gradient(to right, white, orange 50%);z-index: -2;width: 100%;}")
                 htmlPage.append("div.coverFade {position:absolute;right: 0;top:0;height:100%;background-color: white;z-index: -1;float:right;}")
                 htmlPage.append("div.centerOverLay {position:absolute;display:table;top: 0;bottom: 0;left: 0;right: 0;margin:auto;z-index: 0;width:20px;font-weight:bold;font-size:14px;}")
                 htmlPage.append("table.listOverview td.hooverable:hover {background-color: #cccccc;}")
                 htmlPage.append("@media screen and (max-width: 1400px) {table.numOverview {width: 100%;float:none;}}")
             htmlPage.append("</style>")
         htmlPage.append("</head>")
         htmlPage.append("<body>")
            htmlPage.append("<div class=\"banner\">")
            htmlPage.append("<img class=\"headerImage\" src=\"https://www.fednot.be/credoc-fednot-theme/images/fednot-logo.png\"/>")
            htmlPage.append("</div>")
            htmlPage.append("<div class=\"parentContainer\">")
                 //retrieve project name
                 projectName = project.getName()
                 projectPath = project.getPath()
                 projectDescription = project.getDescription().replace("\n", "</br>")

                 //Set main title (project name)
                 htmlPage.append("<h1>$projectName</h1>")
                 htmlPage.append("<div style=\"background-color:#cccccc;border-radius:10px;min-height:20px;padding:10px;margin-bottom: 10px;\"><span style=\"vertical-align:middle;\"><b>Project file location:</b> $projectPath</span></div>")
                 htmlPage.append("<div class=\"testCaseDescription\"><span style=\"vertical-align:middle;\"><b>Project Description:</b></br>$projectDescription</span></div>")
                 //set subtitle 'Overview' + add overview table
                 htmlPage.append("<h2>Overview</h2>")
                 htmlPage.append(getNumberTableData())
                 htmlPage.append(getOverviewTableData())

                 //set subtitle 'Details' + add details table
                 htmlPage.append("<h2>Details</h2>")
                 htmlPage.append(getDetailTableData())

             //End page (close main div, body and html tags)
             htmlPage.append("</div>")
         htmlPage.append("</body>")
     htmlPage.append("</html>")
     return htmlPage
}



///////////////////////////////////////////////////
//    WRITE FILE TO DISK                        //
/////////////////////////////////////////////////
private void writeReportToDisk(String htmlPage) {
    //set up variables for writing the file
    Date now = new Date()
    def timestamp = now.toTimestamp().toString().replaceAll(':','')
    projectName = project.getName()
    baseLoc = project.getPropertyValue("testDataPath")
    if(baseLoc.equals(null) || baseLoc.equals("")) {
      baseLoc="C:\\SoapUI Reports\\"
      UISupport.showInfoMessage("The path variable \"testDataPath\" on project level was not found.\nThe report will be stored in the \"C:\\SoapUIReports\" folder.")
    }
    // Create a File object representing the target folder
    def folder = new File( baseLoc + "Execution results" )
 
    // If it doesn't exist
    if( !folder.exists() ) {
        // Create all folders
        folder.mkdirs()
    }
 
     // Then, write to the file inside the folder
 def fileName = projectName + "_" + timestamp + ".html";
 def targetFile = new File( folder, fileName );
 targetFile.withWriterAppend { w -> w << htmlPage};
 
 //Show the file in the default browser
 Desktop.getDesktop().browse(targetFile.toURI());
}
package com.md.soapui.custom.util.soapui;

import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.ArrayList;
import java.util.List;

import com.eviware.soapui.model.ModelItem;
import com.eviware.soapui.model.testsuite.Assertable;
import com.eviware.soapui.model.testsuite.TestCase;
import com.eviware.soapui.model.testsuite.TestStep;

public class ModelItemSupport {

	/**
	 * Get all child items of a given ModelItem item.
	 * @param item
	 * @return
	 */
	public List<ModelItem> getAllChildren(ModelItem item) {
		List<ModelItem> allChildren = new ArrayList<>();
		findAllChildrenRecursive(allChildren, item);
		return allChildren;
	}
	
	/**
	 * Get all child items for a given ModelItem item for which the name contains the String token.
	 * This implementation with the token ignores the case.
	 * @param item
	 * @param token
	 * @return
	 */
	public List<ModelItem> getAllChildren(ModelItem item, String token) {
		List<ModelItem> allChildren = getAllChildren(item);
		List<ModelItem> searchResult = new ArrayList<>();
		
		for( ModelItem child : allChildren){
            if(child.getName().toLowerCase().contains(token.toLowerCase())) {
            	searchResult.add(child);
            }
        }
		return searchResult;
	}
	
	public List<ModelItem> getAllChildren(ModelItem item
			                             ,String token
			                             ,boolean caseSensitive
			                             ,boolean normalize) {
		List<ModelItem> allChildren = getAllChildren(item);
		List<ModelItem> searchResult = new ArrayList<>();
		
		String usedToken = token;
		
		if(normalize) {
			usedToken = normalize(token);
		}
		if(!caseSensitive) {
			usedToken = usedToken.toLowerCase();
		}
		for( ModelItem child : allChildren){
            if(normalize(child.getName()).contains(usedToken)) {
            	searchResult.add(child);
            }
        }
		return searchResult;
	}
	
	private String normalize(String token) {
		if (token != null) {
			return Normalizer.normalize(token, Form.NFD)
		                     .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
		                     .replaceAll(" ", "");
		} else {
			return null;
		}
	}

	/**
	 * Return a list of ModelItems filtered from a given list
	 * You will exclude certain types, given as a list of ENUM ModelItemClassName
	 * @param list
	 * @param exclude
	 * @return
	 */
	public List<ModelItem> excludeModelItemClass(List<ModelItem> list, ModelItemList... exclude ) {
		List<ModelItem> filteredList = new ArrayList<>();
		List<Class> excludeList = new ArrayList<>();
		
		for(ModelItemList mic : exclude) {
			excludeList.add(mic.soapUIClass());
		}
		for(ModelItem item : list) {
			if(!excludeList.contains(item.getClass())) {
				filteredList.add(item);
			}
		}
		return filteredList;
	}
	
	/**
	 * Return a list of ModelItems filtered from a given list
	 * You will include certain types, given as a list of ENUM ModelItemClassName
	 * @param list
	 * @param exclude
	 * @return
	 */
	public List<ModelItem> includeModelItemClass(List<ModelItem> list, ModelItemList... include ) {
		List<ModelItem> filteredList = new ArrayList<>();
		List<Class> includeList = new ArrayList<>();
		
		for(ModelItemList mic : include) {
			includeList.add(mic.soapUIClass());
		}
		for(ModelItem item : list) {
			if(includeList.contains(item.getClass())) {
				filteredList.add(item);
			}
		}
		return filteredList;
	}
	
	//Basic recursive function for retrieving child items starting from a given point.
	private void findAllChildrenRecursive(List<ModelItem> allChildren, ModelItem parent) {
        for( ModelItem child : parent.getChildren()){
            	allChildren.add(child);
            	findAllChildrenRecursive( allChildren, child);
        }
	}
	
/////////////////////ModelItem tests/////////////////////////

    public boolean isAssertable(Class<? extends ModelItem> claz) {
        boolean isAssertable = com.eviware.soapui.model.testsuite.Assertable.class.isAssignableFrom(claz);
        return isAssertable;
    }

    public boolean isTestSuite(Class<? extends ModelItem> claz) {
        boolean isAssertable = com.eviware.soapui.model.testsuite.TestSuite.class.isAssignableFrom(claz);
        return isAssertable;
    }

    public boolean isTestCase(Class<? extends ModelItem> claz) {
        boolean isAssertable = com.eviware.soapui.model.testsuite.TestCase.class.isAssignableFrom(claz);
        return isAssertable;
    }

    public boolean isTestStep(Class<? extends ModelItem> claz) {
        boolean isAssertable = com.eviware.soapui.model.testsuite.TestStep.class.isAssignableFrom(claz);
        return isAssertable;
    }

    public boolean isTestAssertion(Class<? extends ModelItem> claz) {
        boolean isAssertable = com.eviware.soapui.model.testsuite.TestAssertion.class.isAssignableFrom(claz);
        return isAssertable;
    }
    
    public SoapUIStatus getStepStatus(TestStep step) {
    	String stringStatus = ((Assertable) step).getAssertionStatus().toString();
    	if(step.isDisabled()) {
    		return SoapUIStatus.DISABLED;
    	} else {
    	    switch(stringStatus) {
    	        case "VALID" : return SoapUIStatus.VALID;
    	        case "FAILED" : return SoapUIStatus.FAILED;
    	        case "UNKNOWN" : return SoapUIStatus.UNKNOWN;
    	        default : return SoapUIStatus.UNKNOWN;
    	    }
    	}
    }
    
    //Helper utilities for status consolidation
    public SoapUIStatus getDerivedTestCaseStatus(TestCase tc) {
    	  List<SoapUIStatus> tsStatusList = new ArrayList<>();
    	  List<TestStep> testStepList = tc.getTestStepList();
    	  for(TestStep ts : testStepList) {
    	      if(isAssertable(ts.getClass())) {
    	          if(!ts.isDisabled()) {
    	              tsStatusList.add(getStepStatus(ts));
    	          }
    	      }
    	  }
    	  
    	  if(tsStatusList.contains(SoapUIStatus.FAILED)) {
    	    return SoapUIStatus.FAILED;
    	  } else {
    		  int validCounter;
    		  validCounter = 0;
    		  for(SoapUIStatus status : tsStatusList) {
    			  if(status.equals(SoapUIStatus.VALID)) {
    				  validCounter++;
    			  }
    		  }
    		  if(validCounter == tsStatusList.size()) {
    			  return SoapUIStatus.VALID;
    		  } else {
    			  return SoapUIStatus.UNKNOWN;
    		  }
    	  }
      }
}

package com.md.soapui.custom.util.soapui;

import java.util.ArrayList;
import java.util.List;

import com.eviware.soapui.model.ModelItem;

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
	
	/**
	 * Return a list of ModelItems filtered from a given list
	 * You will exclude certain types, given as a list of ENUM ModelItemClassName
	 * @param list
	 * @param exclude
	 * @return
	 */
	public List<ModelItem> excludeModelItemClass(List<ModelItem> list, ModelItemClassList... exclude ) {
		List<ModelItem> filteredList = new ArrayList<>();
		List<Class> excludeList = new ArrayList<>();
		
		for(ModelItemClassList mic : exclude) {
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
	public List<ModelItem> includeModelItemClass(List<ModelItem> list, ModelItemClassList... include ) {
		List<ModelItem> filteredList = new ArrayList<>();
		List<Class> includeList = new ArrayList<>();
		
		for(ModelItemClassList mic : include) {
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
}

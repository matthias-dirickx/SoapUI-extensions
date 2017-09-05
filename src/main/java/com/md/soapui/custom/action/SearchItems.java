package com.md.soapui.custom.action;

import com.eviware.soapui.model.ModelItem;
import com.eviware.soapui.support.UISupport;
import com.eviware.soapui.support.action.support.AbstractSoapUIAction;
import com.eviware.soapui.support.components.ModelItemListDesktopPanel;

import java.util.ArrayList;
import java.util.List;

public class SearchItems extends AbstractSoapUIAction<ModelItem> {

    public SearchItems() {
        super("Item Search", "Find items by item name - Any item is searched through.");
    }

    @Override
    public void perform(ModelItem item, Object o) {
        String token = UISupport
        		      .prompt( "Provide search string.\nSearch is done on item name."
        		    		 , "Item Search"
        		    		 , "" );

        List<ModelItem> searchResult = new ArrayList<>();
        findItems(searchResult, item, token);

        //User message on empty results list
        if( searchResult.isEmpty()) {
            UISupport.showErrorMessage( "No items matching [" + token + "] found in project");
            return;
        }

        //Show in default modelitem panel when results found.
        UISupport.showDesktopPanel(
            new ModelItemListDesktopPanel(
                 "Search Result"
        	    ,"The following items matched [" + token + "]"
        	    ,searchResult.toArray(new ModelItem[searchResult.size()])
        	)
        );
    }

    //Get all descendants.
    //Match token.toLowerCase() to name.toLowerCase()
    private void findItems(List<ModelItem> searchResult, ModelItem parent, String token) {
        for( ModelItem child : parent.getChildren()){
            if(child.getName().toLowerCase().contains(token.toLowerCase())) {
            	searchResult.add(child);
            	findItems( searchResult, child, token );
            } else {
            	findItems( searchResult, child, token );
            }
        }
    }
}
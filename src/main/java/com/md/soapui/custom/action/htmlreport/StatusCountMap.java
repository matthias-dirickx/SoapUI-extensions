package com.md.soapui.custom.action.htmlreport;

import java.util.HashMap;
import java.util.Map;

import com.eviware.soapui.SoapUI;
import com.md.soapui.custom.util.soapui.Status;

public class StatusCountMap {
	
	public StatusCountMap() {
		
	}
	
	class BasicStatusCountMap {
		
		protected Map<Status, Integer> initialiazeStatusCounterMap() {
			  Map<Status, Integer> map = new HashMap<>();
			  map.put(Status.VALID, 0);
			  map.put(Status.FAILED, 0);
			  map.put(Status.UNKNOWN, 0);
			  map.put(Status.DISABLED, 0);
			  SoapUI.log("initialiazed map " + map.toString());
			  return map;
			}
	}

}

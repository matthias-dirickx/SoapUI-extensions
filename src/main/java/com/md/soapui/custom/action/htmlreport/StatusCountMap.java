package com.md.soapui.custom.action.htmlreport;

import java.util.HashMap;
import java.util.Map;

import com.eviware.soapui.SoapUI;
import com.md.soapui.custom.util.soapui.SoapUIStatus;

public class StatusCountMap {
	
	public StatusCountMap() {
		
	}
	
	class BasicStatusCountMap {
		
		protected Map<SoapUIStatus, Integer> initialiazeStatusCounterMap() {
			  Map<SoapUIStatus, Integer> map = new HashMap<>();
			  map.put(SoapUIStatus.VALID, 0);
			  map.put(SoapUIStatus.FAILED, 0);
			  map.put(SoapUIStatus.UNKNOWN, 0);
			  map.put(SoapUIStatus.DISABLED, 0);
			  SoapUI.log("initialiazed map " + map.toString());
			  return map;
			}
	}

}

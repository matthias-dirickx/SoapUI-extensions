package com.md.soapui.custom.util.soapui.setvalues;

import java.util.Map;

public class SetValuesInJsonRequest {
	
	public SetValuesInJsonRequest(String json, Map<String, String> values) {
		initizlizeJsonObject(json);
		
	}

	public SetValuesInJsonRequest(String json, Map<String, String> values, String basePath) {
		initizlizeJsonObject(json);
	}
	
	private void initizlizeJsonObject(String json) {
		try {
			JSONObject jo = new JSONObject();
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}

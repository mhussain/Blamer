package com.mhussain.blamer;

import org.json.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;

public class DashBoardInfo {

	private final String DEFAULT_JENKINS_SERVER = "http://10.0.2.2:8900/";
	private String server = null;
	
	public DashBoardInfo() {
		this(null, null);
	}
	
	public DashBoardInfo(String host, String port) {
		if (null == host || null == port) {
			server = DEFAULT_JENKINS_SERVER;
		}
		else {
			server = host.concat(":").concat(port);
		}
	}
	
	public String getServerAddress() {
		return this.server;
	}
	
	public JSONObject getDataFromServer() throws Exception {
		StringBuffer data = new StringBuffer();
		JSONObject json_data = null;
		try {
			URL yahoo = new URL(this.getServerAddress());
	        URLConnection yc = yahoo.openConnection();
	        BufferedReader in = new BufferedReader(
	                                new InputStreamReader(
	                                yc.getInputStream()));
	        String inputLine = null;

	        int i = 0;
	        while ((inputLine = in.readLine()) != null) {
	            data.append(inputLine);
	            i++;
	        }
	        in.close();
	        json_data = new JSONObject(data.toString());
		} 
		catch (Exception e) {
			throw new Exception("Something bad happened in getting the JSON from " + this.getServerAddress() + ":" + e.toString() );
		}
		return json_data;
	}

	
}
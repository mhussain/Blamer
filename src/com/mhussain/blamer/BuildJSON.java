package com.mhussain.blamer;

import org.json.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;

public class BuildJSON {

	private String server = null;
	private JSONObject build_data = null;
	private ArrayList<Build> builds = new ArrayList<Build>();
	private ArrayList<String> buildNames = new ArrayList<String>();
	private final String API = "/api/json";
	
	public BuildJSON() {
		/* Figure out a way of making this class grab all the JSON without these headaches */
	}

	public BuildJSON(String host, String port, String suffix) {
		
		this.server = host.concat(":").concat(port).concat((!"".equalsIgnoreCase(suffix) ? "/" + suffix : "")).concat(API);
		
		try {
			this.getDataFromServer();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getServerAddress() {
		return this.server;
	}
	
	public JSONObject getLastBuild(String buildUrl) throws Exception {
		JSONObject oneBuild = getJSONFromBuildServer(buildUrl);
		
		String lastBuildUrl = (String)oneBuild.getJSONObject("lastBuild").get("url");	
		JSONObject lastBuild = getJSONFromBuildServer(lastBuildUrl);
		
		JSONObject changeSet = lastBuild.getJSONObject("changeSet").getJSONArray("items").getJSONObject(0);
		
		return changeSet;
	}
	
	private void getDataFromServer() throws Exception {
		this.build_data = getJSONFromBuildServer(this.getServerAddress());
		
		this.populateBuilds(this.build_data);
	}

	private JSONObject getJSONFromBuildServer(String url) throws Exception {
		StringBuffer data = new StringBuffer();
		JSONObject buildData = new JSONObject();
		try {
			URL buildServer = new URL(url);
	        URLConnection conn = buildServer.openConnection();
	        BufferedReader in = new BufferedReader(
	        	new InputStreamReader(
	        		conn.getInputStream()
	        	)
	        );
	        String inputLine = null;

	        while ((inputLine = in.readLine()) != null) {
	            data.append(inputLine);
	        }
	        in.close();
	        buildData = new JSONObject(data.toString());
		} 
		catch (Exception e) {
			throw new Exception("Something bad happened in getting the JSON from " + this.getServerAddress() + ":" + e.toString() );
		}
		
		return buildData;
	}
	
	private void populateBuilds(JSONObject build_data) {
		JSONArray jobs = new JSONArray();
		try {
			jobs = build_data.getJSONArray("jobs");
		} 
		catch (JSONException jsonException) {
			jsonException.printStackTrace();
		}
    	
		JSONObject job = null;
		
    	for (int job_index=0;job_index<jobs.length();job_index++) {
    		
    		try {
    			job = jobs.getJSONObject(job_index);
    			String name = (String)job.get("name"); 
    			String color = (String)job.get("color");
    			String status = "";
    			if (color.equalsIgnoreCase("red")) {
    				status = "failure";
    			}
    			else if (color.equalsIgnoreCase("blue")) {
    				status = "success";
    			}
    			else {
    				status = "building";
    			}
    			
    			this.builds.add(
    				new Build(name, (String)job.get("url"), status)
    			);
    			this.buildNames.add(name);
			} 
    		catch (JSONException e) {
				e.printStackTrace();
			}
    	}
	}
	
	public ArrayList<Build> getBuildInfo() {
		return this.builds;
	}
	
	public ArrayList<String> getBuildNames() {
		return this.buildNames;
	}
	

	
}
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
	
	public BuildJSON() {
		this(null, null);
	}
	
	public BuildJSON(String host, String port) {
		
		this.server = host.concat(":").concat(port);
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
	
	private void getDataFromServer() throws Exception {
		StringBuffer data = new StringBuffer();
		try {
			URL yahoo = new URL(this.getServerAddress());
	        URLConnection yc = yahoo.openConnection();
	        BufferedReader in = new BufferedReader(
	                                new InputStreamReader(
	                                yc.getInputStream()));
	        String inputLine = null;

	        while ((inputLine = in.readLine()) != null) {
	            data.append(inputLine);
	        }
	        in.close();
	        this.build_data = new JSONObject(data.toString());
		} 
		catch (Exception e) {
			throw new Exception("Something bad happened in getting the JSON from " + this.getServerAddress() + ":" + e.toString() );
		}
		
		this.populateBuilds(this.build_data);
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
    			this.builds.add(
    				new Build(name, (String)job.get("url"), (String)job.get("lastBuildStatus"))
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
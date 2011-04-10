package com.mhussain.blamer;

import android.os.Bundle;

import com.mhussain.blamer.R;

import android.app.ListActivity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import org.json.*;

import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.io.*;



public class Blamer extends ListActivity {

	private final String JENKINS_SERVER = "http://10.0.2.2:8900/";
	private ArrayList<Build> builds = new ArrayList<Build>();
	private ArrayList<String> buildNames = new ArrayList<String>();
	
	public JSONObject getDataFromServer() {
		StringBuffer data = new StringBuffer();
		JSONObject json_data = null;
		try {
			URL yahoo = new URL(JENKINS_SERVER);
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
			Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
		}
		return json_data;
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {

    	final JSONObject build_data = getDataFromServer();
    	
    	if (null == build_data) {
    		Toast.makeText(Blamer.this, "JSONData is null", Toast.LENGTH_SHORT).show();
    	}
    	
    	JSONArray jobs = new JSONArray();
		try {
			jobs = build_data.getJSONArray("jobs");
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
    	
		JSONObject job = null;
    	for (int job_index=0;job_index<jobs.length();job_index++) {
    		try {
    			job = jobs.getJSONObject(job_index);
    			String name = (String)job.get("name"); 
    			builds.add(
    				new Build(name, (String)job.get("url"), (String)job.get("lastBuildStatus"))
    			);
    			buildNames.add(name);
			} 
    		catch (JSONException e) {
				e.printStackTrace();
			}
    	}
    	    	
    	super.onCreate(savedInstanceState);

    	this.setListAdapter(new SpecialAdapter<String>(this, R.layout.dashboard, buildNames, builds));

    	ListView lv = getListView();
    	lv.setTextFilterEnabled(true);
    	 	
    	lv.setOnItemClickListener(new OnItemClickListener() {

    		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    			// TODO: Need to transition to another page.
    		}
    	});
    }
    
    public class SpecialAdapter<E> extends ArrayAdapter<E> {
    	
    	private ArrayList<Build> builds;
    	
    	public SpecialAdapter(Context c, int layout, List<E> data, ArrayList<Build> builds) {
    		super(c, layout, data);
    		this.builds = builds;
    	}
    	
    	@Override
    	public View getView(int position, View convertView, ViewGroup parent) {

    		View all_builds = convertView;
    		 
    		if (null == all_builds) {
    			LayoutInflater inflater = getLayoutInflater();
    			all_builds = inflater.inflate(R.layout.dashboard, null);
    		} 
    		
    		TextView build_element = (TextView)all_builds.findViewById(R.id.build);
    		
    		all_builds.setBackgroundColor(Color.WHITE);
    		build_element.setTextColor(builds.get(position).getBackgroundColor());
    		build_element.setText(builds.get(position).getName());
    		
    		return all_builds;
    	}
    }
}
package com.mhussain.blamer;

import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class IndividualBuildDashboard extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Build build = (Build) getIntent().getExtras().get("build");
        
        setContentView(R.layout.individual_build_dashboard);
        TextView name = (TextView)this.findViewById(R.id.build_name);
        name.setText(build.getName());
        
        TextView desc = (TextView)this.findViewById(R.id.build_desc);
        desc.setText(build.getDescription());
        
   
        BuildJSON lastBuild = new BuildJSON();
        
        try {
			JSONObject lastBuildData = lastBuild.getLastBuild(build.getUrl());
			TextView who = (TextView)this.findViewById(R.id.last_committer);
	        who.setText(lastBuildData.getJSONObject("author").get("fullName").toString());
	        
	        TextView what = (TextView)this.findViewById(R.id.last_commit_id);
	        what.setText(lastBuildData.getJSONObject("id").toString());
	        
	        TextView when = (TextView)this.findViewById(R.id.last_commit_date);
	        when.setText(lastBuildData.getJSONObject("date").toString());
	        
		} catch (Exception e) {
			System.err.println("Could not get last Build information from " + build.getUrl() + ":" + e.toString());
		}
        
        
        
	}
	
}

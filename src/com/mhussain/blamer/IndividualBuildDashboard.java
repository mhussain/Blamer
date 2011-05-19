package com.mhussain.blamer;

import org.json.JSONException;
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
        
        JSONObject lastBuildData = null; 
        
        try {
			lastBuildData = lastBuild.getLastBuild(build.getUrl());
        } 
        catch (Exception e) {
			System.err.println("Could not get last Build information from " + build.getUrl() + ":" + e.toString());
		}
			
		TextView who = (TextView)this.findViewById(R.id.last_committer);
		TextView what = (TextView)this.findViewById(R.id.last_commit_id);
		TextView when = (TextView)this.findViewById(R.id.last_commit_date);
		
		String lastCommitBy = build.getLastCommitBy();
		String lastCommitId = build.getLastCommitId();
		String lastCommitDate = build.getLastCommitDateTime().toString();
		
		if (lastBuildData != null) {
			try {
				lastCommitBy = lastBuildData.getJSONObject("author").get("name").toString();
				lastCommitId = lastBuildData.getJSONObject("id").toString();
				lastCommitDate = lastBuildData.getJSONObject("date").toString();
			} 
			catch (JSONException e) {
				e.printStackTrace();
			}

		}
		
        who.setText(lastCommitBy);
        what.setText(lastCommitId);
        when.setText(lastCommitDate);
		
	}
}

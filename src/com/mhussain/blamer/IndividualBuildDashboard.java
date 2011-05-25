package com.mhussain.blamer;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class IndividualBuildDashboard extends Activity {
	
	private String contactText = "Email ";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Build build = (Build) getIntent().getExtras().get("build");
        
        BuildJSON lastBuild = new BuildJSON();
        
        String lastCommitBy = build.getLastCommitBy();
        String lastCommitId = build.getLastCommitId();
        String lastCommitDate = build.getLastCommitDateTime().toString();
        
        JSONObject lastBuildData = null; 
        
        try {
			lastBuildData = lastBuild.getLastBuild(build.getUrl());
        } 
        catch (Exception e) {
			System.err.println("Could not get last Build information from " + build.getUrl() + ":" + e.toString());
		}
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
        
        contactText = contactText.concat("'"+lastCommitBy+"'");
        
        setContentView(R.layout.individual_build_dashboard);
        TextView name = (TextView)this.findViewById(R.id.build_name);
        name.setText(build.getName());
        
        TextView desc = (TextView)this.findViewById(R.id.build_desc);
        desc.setText(build.getDescription());
        
			
		TextView who = (TextView)this.findViewById(R.id.last_committer);
		TextView what = (TextView)this.findViewById(R.id.last_commit_id);
		TextView when = (TextView)this.findViewById(R.id.last_commit_date);
		
		Button contact = (Button)this.findViewById(R.id.contact);
		contact.setText(contactText);
		
		contact.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Toast.makeText(IndividualBuildDashboard.this, "Sending SMS", Toast.LENGTH_SHORT).show();
				
				SmsManager smsManager = SmsManager.getDefault();
				
				PendingIntent sentPI = PendingIntent.getBroadcast(IndividualBuildDashboard.this, 0, new Intent("sms_sent"), 0);
				PendingIntent deliveredPI = PendingIntent.getBroadcast(IndividualBuildDashboard.this, 0,
                        new Intent("sms_delivered"), 0);

				smsManager.sendTextMessage("0424726928", null, "You broke the build", sentPI, deliveredPI);
			}
		});
		
        who.setText(lastCommitBy);
        what.setText(lastCommitId);
        when.setText(lastCommitDate);
		
	}
}

package com.mhussain.blamer;

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
        
        TextView who = (TextView)this.findViewById(R.id.last_committer);
        who.setText(build.getLastCommitBy());
        
        TextView what = (TextView)this.findViewById(R.id.last_commit_id);
        what.setText(build.getLastCommitId());
        
        TextView when = (TextView)this.findViewById(R.id.last_commit_date);
        when.setText(build.getLastCommitDateTime().toString());
        
	}
	
}

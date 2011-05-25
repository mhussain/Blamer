package com.mhussain.blamer;

import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.markupartist.android.widget.ActionBar;
import com.markupartist.android.widget.PullToRefreshListView;
import com.markupartist.android.widget.PullToRefreshListView.OnRefreshListener;
import com.mhussain.blamer.R;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import com.markupartist.android.widget.ActionBar.IntentAction;

public class BuildDashboard extends ListActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.build_dashboard);
		
       final ActionBar actionBar = (ActionBar) findViewById(R.id.actionbar);
       actionBar.setHomeAction(new IntentAction(this, createIntent(this), R.drawable.settings));
		
        ((PullToRefreshListView) getListView()).setOnRefreshListener(new OnRefreshListener() {
            public void onRefresh() {
            	new ReloadBuilds().execute();
            }
        });
		
		Bundle serverInfo = getIntent().getExtras();
		
    	BuildJSON dashboard = new BuildJSON(
    		serverInfo.getString("host"),
    		serverInfo.getString("port"),
    		serverInfo.getString("suffix")
    	);
    	
    	this.setListAdapter(
    		new DashboardListAdapter<String>(
    			this,
    			R.layout.build_dashboard,
    			dashboard.getBuildNames(),
    			dashboard.getBuildInfo()
    		)
    	);
    }
	
    public static Intent createIntent(Context context) {
        Intent i = new Intent(context, Settings.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return i;
    }


	private class ReloadBuilds extends AsyncTask<Void, Void, String[]> {
	    @Override
	    protected void onPostExecute(String[] result) {
	    	startActivity(getIntent());
	    	finish();
	        ((PullToRefreshListView) getListView()).onRefreshComplete();
	        super.onPostExecute(result);
	    }

		@Override
		protected String[] doInBackground(Void... arg0) { return null; }
	}

	class DashboardListAdapter<E> extends ArrayAdapter<E> {
    	
    	private ArrayList<Build> builds;
    	
    	public DashboardListAdapter(Context c, int layout, List<E> data, ArrayList<Build> builds) {
    		super(c, layout, data);
    		this.builds = builds;
    	}
    	
    	@Override
    	public View getView(int position, View convertView, ViewGroup parent) {

    		View all_builds = convertView;
    		 
    		if (null == all_builds) {
    			LayoutInflater inflater = getLayoutInflater();
    			all_builds = inflater.inflate(R.layout.dashboard_row, null);
    		} 
    		
    		final TextView build_element = (TextView)all_builds.findViewById(R.id.build);
    		
    		all_builds.setBackgroundColor(Color.WHITE);
    		final Build build  = builds.get(position);
    		
    		build_element.setTextColor(build.getBackgroundColor());
    		build_element.setText(build.getName());
    		
    		build_element.setOnClickListener(new OnClickListener(){
				public void onClick(View view) {
					
					if (build.failed()) {
						Intent individualBuildDashboard = new Intent(BuildDashboard.this, IndividualBuildDashboard.class);
						Bundle buildInfo = new Bundle();
						buildInfo.putSerializable("build", build);
						individualBuildDashboard.putExtras(buildInfo);
						startActivity(individualBuildDashboard);
					}
					else if(build.isBuilding()) {
						Toast.makeText(BuildDashboard.this, "Please let it build!", Toast.LENGTH_LONG).show();
					}
					else {
						Toast.makeText(BuildDashboard.this, "Congratulations, the last build was successful!", Toast.LENGTH_LONG).show();
					}
				}
    			
    		});
    		
    		return all_builds;
    	}
	}
}

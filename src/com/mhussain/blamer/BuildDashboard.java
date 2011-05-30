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
import android.widget.ImageView;
import android.widget.ProgressBar;
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
		
		Bundle serverInfo = getIntent().getExtras();
		
		BuildJSON dashboard = new BuildJSON(
				serverInfo.getString("host"),
				serverInfo.getString("port"),
				serverInfo.getString("suffix")
		);
		
       final ActionBar actionBar = (ActionBar) findViewById(R.id.actionbar);
       actionBar.setHomeAction(new IntentAction(this, createIntent(this), R.drawable.settings));
		
        ((PullToRefreshListView) getListView()).setOnRefreshListener(new OnRefreshListener() {
            public void onRefresh() {
            	new ReloadBuilds().execute();
            }
        });
    	
    	
    	this.setListAdapter(
    		new DashboardListAdapter<String>(
    			this,
    			R.layout.build_dashboard,
    			R.id.build,
    			dashboard.getBuildNames(),
    			dashboard.getBuildInfo()
    		)
    	);
    }

	class DashboardListAdapter<E> extends ArrayAdapter<E> {
    	
    	private ArrayList<Build> builds;
    	
    	public DashboardListAdapter(Context context, int layout, int label, List<E> data, ArrayList<Build> builds) {
    		super(context, layout, label, data);
    		this.builds = builds;
    	}
    	
    	@Override
    	public View getView(int position, View convertView, ViewGroup parent) {

    		View row = convertView;
    		final Build build  = builds.get(position);
    		 
    		if (null == row) {
    			LayoutInflater inflater = getLayoutInflater();
    			row = inflater.inflate(R.layout.dashboard_row, null);
    		} 
    		row.setBackgroundColor(Color.WHITE);
    		
    		final TextView label = (TextView)row.findViewById(R.id.build);
    		
    		ImageView contextImage = null;
    		
    		label.setTextColor(build.getBackgroundColor());
    		label.setText(build.getName());
    		
    		contextImage = (ImageView)row.findViewById(R.id.information);
    		ProgressBar progress = (ProgressBar)row.findViewById(R.id.progress);
    		if (build.failed()) {
	    		contextImage.setImageResource(R.drawable.context);
	    		progress.setVisibility(View.INVISIBLE);
    		}
    		else if (build.wasSuccessful()) {
    			contextImage.setImageResource(R.drawable.correct);
    			progress.setVisibility(View.INVISIBLE);
    		}
    		else {
    			progress.setVisibility(View.VISIBLE);
    		}
    		
    		label.setOnClickListener(new OnClickListener(){
				public void onClick(View view) {
					
					if (build.failed()) {
						Intent individualBuildDashboard = new Intent(BuildDashboard.this, IndividualBuildDashboard.class);
						Bundle buildInfo = new Bundle();
						
						buildInfo.putSerializable("build", build);
						individualBuildDashboard.putExtras(buildInfo);
						
						startActivity(individualBuildDashboard);
					}
					else if(build.isBuilding()) {
						Toast.makeText(BuildDashboard.this, "Please let it build!", Toast.LENGTH_SHORT).show();
					}
					else {
						Toast.makeText(BuildDashboard.this, "Congratulations, the last build was successful!", Toast.LENGTH_SHORT).show();
					}
				}
    			
    		});
    		
    		return row;
    	}
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

}

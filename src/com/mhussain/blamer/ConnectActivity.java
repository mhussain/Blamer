package com.mhussain.blamer;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mhussain.blamer.R;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

public class ConnectActivity extends ListActivity {
	
	@Override
    public void onCreate(Bundle savedInstanceState) {

		Bundle serverInfo = getIntent().getExtras();
		
		
    	DashBoardInfo dashboard = new DashBoardInfo(
    		serverInfo.getString("host"),
    		serverInfo.getString("port")
    	);
    	    	
    	super.onCreate(savedInstanceState);

    	this.setListAdapter(
    		new SpecialAdapter<String>(
    			this,
    			R.layout.dashboard, 
    			dashboard.getBuildNames(), 
    			dashboard.getBuildInfo()
    		)
    	);

    	ListView lv = getListView();
    	lv.setTextFilterEnabled(true);
    }
    
    class SpecialAdapter<E> extends ArrayAdapter<E> {
    	
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
    		final Build build  = builds.get(position);
    		
    		build_element.setTextColor(build.getBackgroundColor());
    		build_element.setText(build.getName());
    		
    		build_element.setOnClickListener(new OnClickListener(){
				public void onClick(View view) {
					Toast.makeText(ConnectActivity.this, build.getUrl(), Toast.LENGTH_SHORT).show();
				}
    			
    		});
    		
    		return all_builds;
    	}
    }

}

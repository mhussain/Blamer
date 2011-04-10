package com.mhussain.blamer;

import android.os.Bundle;

import com.mhussain.blamer.R;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import java.util.ArrayList;
import java.util.List;

public class Blamer extends ListActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {

    	DashBoardInfo dashboard = new DashBoardInfo();
    	    	
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
    	 	
    	lv.setOnItemClickListener(new OnItemClickListener() {

    		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//    			parent.addView(parent.findViewById(R.layout.dashboard));
    			
    			//Intent add_data = new Intent(Blamer.this, ConnectActivity.class);
    			//startActivity(add_data);
    		}
    	});
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
    		build_element.setTextColor(builds.get(position).getBackgroundColor());
    		build_element.setText(builds.get(position).getName());
    		build_element.setOnClickListener(new OnClickListener(){

				public void onClick(View view) {
					//Toast.makeText(Blamer.this, "I am inside on click", Toast.LENGTH_LONG).show();
					startActivity(new Intent(Blamer.this, ConnectActivity.class));
				}
    			
    		});
    		
    		return all_builds;
    	}
    }
}
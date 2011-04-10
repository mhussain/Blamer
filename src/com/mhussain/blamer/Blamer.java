 package com.mhussain.blamer;

import android.os.Bundle;

import com.mhussain.blamer.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Blamer extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Toast.makeText(Blamer.this, "I am inside ConnectActivity", Toast.LENGTH_LONG).show();
        
        setContentView(
        	R.layout.input
        );
        
        Button connect = (Button)this.findViewById(R.id.connect);
        final EditText host = (EditText)this.findViewById(R.id.host); 
        final EditText port = (EditText)this.findViewById(R.id.port); 
        
        connect.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				String hostname = host.getText().toString();
				String portNumber = port.getText().toString();
				
				Bundle serverInfo = new Bundle();
				serverInfo.putString("host", hostname);
				serverInfo.putString("port", portNumber);
				
				Intent showBuildInfo = new Intent(Blamer.this, ConnectActivity.class);
				showBuildInfo.putExtras(serverInfo);
				startActivity(showBuildInfo);
			}
		});
        
	}
	
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//
//    	DashBoardInfo dashboard = new DashBoardInfo();
//    	    	
//    	super.onCreate(savedInstanceState);
//
//    	this.setListAdapter(
//    		new SpecialAdapter<String>(
//    			this,
//    			R.layout.dashboard, 
//    			dashboard.getBuildNames(), 
//    			dashboard.getBuildInfo()
//    		)
//    	);
//
//    	ListView lv = getListView();
//    	lv.setTextFilterEnabled(true);
//    }
    
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
					startActivity(new Intent(Blamer.this, ConnectActivity.class));
				}
    			
    		});
    		
    		return all_builds;
    	}
    }
}
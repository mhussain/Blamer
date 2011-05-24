package com.mhussain.blamer;

import android.os.Bundle;

import com.mhussain.blamer.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Settings extends Activity {

	private final String PREFS = "shared_preferences";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        SharedPreferences serverData = this.getSharedPreferences(PREFS, MODE_PRIVATE);
        final SharedPreferences.Editor serverDataEditor = serverData.edit();
        
        setContentView(
        	R.layout.input
        );
        
        Button connect = (Button)this.findViewById(R.id.connect);
        final EditText host = (EditText)this.findViewById(R.id.host); 
        final EditText port = (EditText)this.findViewById(R.id.port);
        final EditText suffix = (EditText)this.findViewById(R.id.suffix);
        
        connect.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				String hostname = host.getText().toString();
				String portNumber = port.getText().toString();
				String suffixString = suffix.getText().toString();
				
				Bundle serverInfo = new Bundle();
				serverInfo.putString("host", hostname);
				serverInfo.putString("port", portNumber);
				serverInfo.putString("suffix", suffixString);
				
				serverDataEditor.putString("host", hostname);
				serverDataEditor.putString("port", portNumber);
				serverDataEditor.putString("suffix", suffixString);
				
				Intent buildDashboard = new Intent(Settings.this, BuildDashboard.class);
				buildDashboard.putExtras(serverInfo);
				
				startActivity(buildDashboard);
			}
		});
        
	}
}
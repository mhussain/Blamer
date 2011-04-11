package com.mhussain.blamer;

import android.os.Bundle;

import com.mhussain.blamer.R;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Blamer extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
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
				
				Intent buildDashboard = new Intent(Blamer.this, BuildDashboard.class);
				buildDashboard.putExtras(serverInfo);
				startActivity(buildDashboard);
			}
		});
        
	}
}
package com.mhussain.blamer;

import android.os.Bundle;
import android.widget.Toast;
import com.mhussain.blamer.R;
import android.app.Activity;

public class ConnectActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Toast.makeText(ConnectActivity.this, "I am inside ConnectActivity", Toast.LENGTH_LONG).show();
        
        setContentView(
        	R.layout.input
        );
	}

}

package com.mhussain.blamer;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends Activity {

	private final int SPLASH_DISPLAY_LENGTH = 3000;
	private final String PREFS = "shared_preferences";

	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_screen);

		final SharedPreferences serverData = this.getSharedPreferences(PREFS, MODE_PRIVATE);
		
		
		new Handler().postDelayed(new Runnable() {
			public void run() {
				Intent nextIntent  = null; 
				if ("".equalsIgnoreCase(serverData.getString("host", ""))) {
					nextIntent = new Intent(SplashScreen.this, Settings.class);
				}
				else {
					nextIntent = new Intent(SplashScreen.this, BuildDashboard.class);
				}
				startActivity(nextIntent);
				finish();
			}
		}, SPLASH_DISPLAY_LENGTH);
	}
}

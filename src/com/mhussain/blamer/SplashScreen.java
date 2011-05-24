package com.mhussain.blamer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends Activity {

	private final int SPLASH_DISPLAY_LENGTH = 3000;

	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_screen);

		new Handler().postDelayed(new Runnable() {
			public void run() {
				Intent mainIntent = new Intent(SplashScreen.this, Blamer.class);
				startActivity(mainIntent);
				finish();
			}
		}, SPLASH_DISPLAY_LENGTH);
	}
}

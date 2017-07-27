package com.cocolocomoco.tapalap.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SettingsActivity extends AppCompatActivity {
	protected static final String KEY_PREF_SCREEN_ON = "pref_screen_on";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Display the fragment as the main content.
		getFragmentManager().beginTransaction()
				.add(android.R.id.content, new SettingsFragment())
				.commit();
	}
}

package com.cocolocomoco.tapalap.ui.settings;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cocolocomoco.tapalap.ui.settings.SettingsFragment;

public class SettingsActivity extends AppCompatActivity {
	public static final String KEY_PREF_SCREEN_ON = "pref_screen_on";
	public static final String KEY_PREF_LAPS_PER_MILE= "pref_laps_per_mile";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Display the fragment as the main content.
		getFragmentManager().beginTransaction()
				.add(android.R.id.content, new SettingsFragment())
				.commit();
	}
}

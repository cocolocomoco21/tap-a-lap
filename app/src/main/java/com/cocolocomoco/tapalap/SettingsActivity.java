package com.cocolocomoco.tapalap;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

public class SettingsActivity extends AppCompatActivity  implements SharedPreferences.OnSharedPreferenceChangeListener {
	protected static final String KEY_PREF_SCREEN_ON = "pref_screen_on";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Display the fragment as the main content.
		getFragmentManager().beginTransaction()
				.add(android.R.id.content, new SettingsFragment())
				.commit();
	}

	@Override
	public void onResume() {
		super.onResume();

		// Register listener for preference change
		PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);
	}

	@Override
	public void onPause() {
		super.onPause();

		// Unregister listener for preference change
		PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		if (key.equals(SettingsActivity.KEY_PREF_SCREEN_ON)) {
			boolean result = sharedPreferences.getBoolean(key, true);
			onUpdateScreenOn(result);
		}
	}

	public void onUpdateScreenOn(boolean screenOn) {
		if (screenOn) {
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		} else {
			getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		}
	}
}

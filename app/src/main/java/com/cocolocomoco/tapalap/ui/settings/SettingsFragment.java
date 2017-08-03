package com.cocolocomoco.tapalap.ui.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.PreferenceFragment;
import android.support.v4.app.Fragment;
import java.util.Map;

import com.cocolocomoco.tapalap.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

	public SettingsFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Load the preferences from an XML resource
		addPreferencesFromResource(R.xml.preferences);

		EditTextPreference pref = (EditTextPreference) getPreferenceManager().findPreference(SettingsActivity.KEY_PREF_LAPS_PER_MILE);
		updateLapPerMilePreferenceSummary(pref);
	}

	@Override
	public void onResume() {
		super.onResume();

		SharedPreferences sharedPreferences = getPreferenceManager().getSharedPreferences();

		// Register listener for preference change
		sharedPreferences.registerOnSharedPreferenceChangeListener(this);

		// Update any EditTextPreferences to show value in summary
		for (Map.Entry<String, ?> preferenceEntry : sharedPreferences.getAll().entrySet()) {
			if (preferenceEntry.getClass().equals(EditTextPreference.class)) {
				updateLapPerMilePreferenceSummary((EditTextPreference)preferenceEntry.getValue());
			}
		}
	}

	@Override
	public void onPause() {
		super.onPause();

		// Unregister listener for preference change
		getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		if (key.equals(SettingsActivity.KEY_PREF_LAPS_PER_MILE)) {
			EditTextPreference pref = (EditTextPreference) getPreferenceManager().findPreference(SettingsActivity.KEY_PREF_LAPS_PER_MILE);
			updateLapPerMilePreferenceSummary(pref);
		}
	}

	/**
	 * Update lap per mile preference summary on the Settings page according to the preference paramter.
	 * @param pref - Preference parameter used to get text from and update the setting. If empty,
	 *             set to default.
	 */
	private void updateLapPerMilePreferenceSummary(EditTextPreference pref) {
		String text = pref.getText();
		if (text.isEmpty()) {
			pref.setSummary(R.string.laps_per_mile_rate_summary);
			return;
		}

		Double existingRate = Double.valueOf(text);
		if (existingRate.equals(0.0)) {
			// Clear values equal to 0
			pref.setSummary(R.string.laps_per_mile_rate_summary);
			pref.setText("");
		} else {
			pref.setSummary(existingRate.toString());
			pref.setText(existingRate.toString());
		}
	}
}

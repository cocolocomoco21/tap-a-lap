package com.cocolocomoco.tapalap.ui.lapcount;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

import com.jakewharton.threetenabp.AndroidThreeTen;
import org.threeten.bp.Instant;

import com.cocolocomoco.tapalap.model.session.Session;
import com.cocolocomoco.tapalap.R;
import com.cocolocomoco.tapalap.ui.settings.SettingsActivity;


public class LapCountActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
	private static final int NUM_PAGES = 2;

	//private int lapCount = 0;
	Session session;

	private ViewPager viewPager;
	private LapCountFragment lapCountFragment;
	private SessionFragment sessionFragment;
	private StatsFragment statsFragment;
	private StatsActionRequiredFragment statsActionRequiredFragment;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lap_count);

		this.lapCountFragment = new LapCountFragment();
		this.sessionFragment = new SessionFragment();
		this.statsFragment = new StatsFragment();
		this.statsActionRequiredFragment = new StatsActionRequiredFragment();

		LapPagerAdapter pagerAdapter = new LapPagerAdapter(getSupportFragmentManager());
		pagerAdapter.addFragment(this.sessionFragment);
		pagerAdapter.addFragment(this.lapCountFragment);

		String existingRate = PreferenceManager.getDefaultSharedPreferences(this).getString(SettingsActivity.KEY_PREF_LAPS_PER_MILE, "");
		if (!existingRate.isEmpty()) {
			pagerAdapter.addFragment(this.statsFragment);
		} else {
			pagerAdapter.addFragment(this.statsActionRequiredFragment);
		}

		this.viewPager = (ViewPager) findViewById(R.id.pager);
		this.viewPager.setAdapter(pagerAdapter);

		// Set default preference values and register this activity as listener
		PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
		PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);

		// Load existing preferences
		loadPreferences();

		// Initialize timezone
		AndroidThreeTen.init(this);
	}

	/**
	 * LapPagerAdapter class used for handling paging of Fragments in the ViewPager.
	 */
	private class LapPagerAdapter extends FragmentPagerAdapter {
		private List<Fragment> fragments = new ArrayList<Fragment>();

		private LapPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		private void addFragment(Fragment fragment) {
			this.fragments.add(fragment);
			notifyDataSetChanged();
		}

		@Override
		public Fragment getItem(int position) {
			return this.fragments.get(position);
		}

		@Override
		public int getCount() {
			return NUM_PAGES;
		}
	}

	/**
	 * Load SharedPreferences. UI loading is handled in SettingsFragment, while the logic/actions
	 * (e.g. screen always on) are handled here.
	 */
	private void loadPreferences() {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

		// Screen always on
		boolean screenOn = sharedPreferences.getBoolean(SettingsActivity.KEY_PREF_SCREEN_ON, true);
		onUpdateScreenOn(screenOn);
	}

	public Double getLapPerMilePreference() {
		String existingRate = PreferenceManager.getDefaultSharedPreferences(this).getString(SettingsActivity.KEY_PREF_LAPS_PER_MILE, "");
		if (existingRate.isEmpty()) {
			return null;
		}
		return Double.valueOf(existingRate);
	}

	public void showLapCountFragment() {
		// TODO access dynamically, don't use hardcoded 1 here
		this.viewPager.setCurrentItem(1);
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

	@Override
	public void onBackPressed() {
		if (this.viewPager.getCurrentItem() == 0) {
			// If the user is currently looking at the first step, allow the system to handle the
			// Back button. This calls finish() on this activity and pops the back stack.
			super.onBackPressed();
		} else {
			// Otherwise, select the previous step.
			this.viewPager.setCurrentItem(this.viewPager.getCurrentItem() - 1);
		}
	}

	public void increaseLapCount() {
		this.session.increaseLapCount();

		TextView textView = (TextView)findViewById(R.id.lapCountDisplay);
		textView.setText(String.valueOf(getLapCount()));
	}

	public int getLapCount() {
		printDebugLaps();
		return this.session.getLapCount();
	}

	// TODO for debugging - delete
	private void printDebugLaps() {
		TextView textView = (TextView)findViewById(R.id.debugBox);
		textView.setText(this.session.debugLaps());
	}

	public void onDecreaseClick(View view) {
		this.session.decreaseLapCount();

		TextView textView = (TextView)findViewById(R.id.lapCountDisplay);
		textView.setText(String.valueOf(getLapCount()));
	}

	public void onResetClick(View view) {
		this.session.resetLapCount();

		TextView textView = (TextView)findViewById(R.id.lapCountDisplay);
		textView.setText(String.valueOf(getLapCount()));
	}

	public void onStartSessionClick(View view) {
		this.sessionFragment.onStartSessionClick(view);
	}

	public Session getSession() {
		return this.session;
	}

	public void initializeSession(Instant start) {
		Double existingRate = getLapPerMilePreference();
		if (existingRate == null) {
			this.session = new Session(start);
		} else {
			initializeSession(start, existingRate);
		}
	}

	public void initializeSession(Instant start, Double lapsPerMileRate) {
		// Initialize current Session with lapsPerMileRate to start a Session
		this.session = new Session(start, lapsPerMileRate);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.options, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem) {
		switch (menuItem.getItemId()) {
			case R.id.settings: {
				// Intent to start SettingsActivity
				Intent intent = new Intent(this, SettingsActivity.class);
				startActivity(intent);
				break;
			}
			case R.id.help: {
				showHelp();
				break;
			}
			default: {
				break;
			}
		}

		// TODO better here?
		return true;
	}

	private void showHelp() {
		// Display help dialog popup
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.help_dialog_title)
				.setMessage(R.string.help_dialog_message);

		AlertDialog dialog = builder.create();
		dialog.show();
	}
}

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
import com.cocolocomoco.tapalap.manager.SessionManager;
import com.cocolocomoco.tapalap.model.session.SessionStatus;
import com.cocolocomoco.tapalap.R;
import com.cocolocomoco.tapalap.ui.settings.SettingsActivity;


public class LapCountActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
	private static final int NUM_PAGES = 3;

	// Abstact inner-workings of the Session to SessionManager
	private SessionManager sessionManager;

	private ViewPager viewPager;

	// Fragments for use in ViewPager
	private LapCountFragment lapCountFragment;
	private SessionFragment sessionFragment;
	private StatsFragment statsFragment;
	private StatsActionRequiredFragment statsActionRequiredFragment;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lap_count);

		// Initialize fragments
		this.lapCountFragment = new LapCountFragment();
		this.sessionFragment = new SessionFragment();
		this.statsFragment = new StatsFragment();
		this.statsActionRequiredFragment = new StatsActionRequiredFragment();

		// Initialize ViewPager and PagerAdapter
		LapPagerAdapter pagerAdapter = new LapPagerAdapter(getSupportFragmentManager());
		pagerAdapter.addFragment(this.sessionFragment);
		pagerAdapter.addFragment(this.lapCountFragment);

		// TODO look into updating fragments after lapPerMileRate is set (after startup)
		String existingRate = PreferenceManager.getDefaultSharedPreferences(this).getString(SettingsActivity.KEY_PREF_LAPS_PER_MILE, "");
		if (!existingRate.isEmpty()) {
			pagerAdapter.addFragment(this.statsFragment);
		} else {
			pagerAdapter.addFragment(this.statsActionRequiredFragment);
		}

		this.viewPager = (ViewPager) findViewById(R.id.pager);
		this.viewPager.setAdapter(pagerAdapter);

		// Initialize preferences
		PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
		PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);
		loadPreferences();

		// Initialize timezone
		AndroidThreeTen.init(this);

		this.sessionManager = new SessionManager();
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

	/**
	 * Get lap per mile rate preference from SharedPreferences.
	 */
	public Double getLapPerMilePreference() {
		String existingRate = PreferenceManager.getDefaultSharedPreferences(this).getString(SettingsActivity.KEY_PREF_LAPS_PER_MILE, "");
		if (existingRate.isEmpty()) {
			return null;
		}
		return Double.valueOf(existingRate);
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


	/**
	 * Update activity's ViewPager to show LapCountFragment.
	 */
	public void showLapCountFragment() {
		// TODO access dynamically, don't use hardcoded 1 here
		this.viewPager.setCurrentItem(1);
	}

	/**
	 * Update activity's ViewPager to show SessionFragment
	 */
	public void showSessionFragment() {
		// TODO access dynamically, don't use hardcoded 0 here
		this.viewPager.setCurrentItem(0);
	}


	/**
	 * Get the current Session's lap count.
	 */
	public int getLapCount() {
		printDebugLaps();
		return this.sessionManager.getCurrentSession().getLapCount();
	}

	// TODO for debugging - delete
	private void printDebugLaps() {
		TextView textView = (TextView)findViewById(R.id.debugBox);
		textView.setText(this.sessionManager.getCurrentSession().debugLaps());
	}

	/**
	 * onClick handler for Decrease button in LapCountFragment.
	 */
	public void onDecreaseClick(View view) {
		this.lapCountFragment.onDecreaseClick(view);
	}

	/**
	 * onClick handler for Reset button in LapCountFragment.
	 */
	public void onResetClick(View view) {
		this.lapCountFragment.onResetClick(view);
	}


	/**
	 * onClick handler for Start Session button in SessionFragment.
	 */
	public void onStartSessionClick(View view) {
		this.sessionFragment.onStartSessionClick(view);
	}

	/**
	 * onClick handler for Stop Session button in SessionFragment.
	 */
	public void onStopSessionClick(View view) {
		this.sessionFragment.onStopSessionClick(view);

		// TODO debugging - delete
		printDebugLaps();
	}

	/**
	 * Begin a new Session after one has already been started.
	 * @param start - start timestamp of this session, should it need to be initialized.
	 * @param existingRate - lap per mile rate.
	 */
	public void beginNewSession(Instant start, Double existingRate) {
		this.sessionManager.beginNewSession(start, existingRate);
	}

	/**
	 * Start the session. Initialize the session if empty, and otherwise attempt to start/resume an
	 * already existing session, if able. Regardless, return the previous session status.
	 * @param start - start timestamp of this session, should it need to be initialized.
	 * @param lapsPerMileRate - lap per mile rate for this session.
	 * @return SessionStatus - the previous status for the session (for the UI to use to gauge state).
	 */
	public SessionStatus startSession(Instant start, Double lapsPerMileRate) {
		return this.sessionManager.startSession(start, lapsPerMileRate);
	}

	/**
	 * End the session if able. Regardless, return the previous session status.
	 * @param end - end timestamp of the session.
	 * @return SessionStatus - the previous status for the session (for the UI to use to gauge state).
	 */
	public SessionStatus endSession(Instant end) {
		return this.sessionManager.endCurrentSession(end);
	}

	public Session getCurrentSession() {
		return this.sessionManager.getCurrentSession();
	}


	/**
	 * Options menu.
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.options, menu);

		return true;
	}

	/**
	 * Options menu.
	 */
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

	/**
	 * Show help when selected from the Options menu.
	 */
	private void showHelp() {
		// Display help dialog popup
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.help_dialog_title)
				.setMessage(R.string.help_dialog_message);

		AlertDialog dialog = builder.create();
		dialog.show();
	}
}

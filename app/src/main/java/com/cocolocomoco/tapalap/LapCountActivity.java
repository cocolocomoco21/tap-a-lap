package com.cocolocomoco.tapalap;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class LapCountActivity extends AppCompatActivity {

	private int lapCount = 0;

	private ViewPager viewPager;
	private LapPagerAdapter pagerAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lap_count);

		//getSupportFragmentManager().beginTransaction()
		//		.add(R.id.pager, new LapCountFragment()).commit();

		this.viewPager = (ViewPager) findViewById(R.id.pager);
		this.pagerAdapter = new LapPagerAdapter(getSupportFragmentManager());

		this.pagerAdapter.addFragment(new LapCountFragment());
		this.pagerAdapter.addFragment(new StatsFragment());

		this.viewPager.setAdapter(this.pagerAdapter);


		//this.viewPager.setOnTouchListener();
	}

	class LapPagerAdapter extends FragmentPagerAdapter {
		private List<Fragment> fragments = new ArrayList<Fragment>();


		public LapPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		public void addFragment(Fragment fragment) {
			this.fragments.add(fragment);
			notifyDataSetChanged();
		}

		@Override
		public Fragment getItem(int position) {
//			if (position == 0) {
//				return new LapCountFragment();
//			} else {
//				return new StatsFragment();
//			}

			return this.fragments.get(position);
		}

		@Override
		public int getCount() {
			return 2;
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

	@Override
	public boolean onTouchEvent(MotionEvent motionEvent) {
		switch (motionEvent.getAction()) {
			case MotionEvent.ACTION_DOWN:
				this.lapCount++;
				break;
		}

		TextView textView = (TextView)findViewById(R.id.lapCountDisplay);
		textView.setText(String.valueOf(this.lapCount));

		return true;
		//return super.onTouchEvent(motionEvent);
	}

	public void increaseLapCount() {
		this.lapCount++;

		TextView textView = (TextView)findViewById(R.id.lapCountDisplay);
		textView.setText(String.valueOf(this.lapCount));
	}

	public void onDecreaseClick(View view) {
		if (this.lapCount == 0) {
			return;
		}

		TextView textView = (TextView)findViewById(R.id.lapCountDisplay);
		textView.setText(String.valueOf(--this.lapCount));
	}

	public void onResetClick(View view) {
		this.lapCount = 0;

		TextView textView = (TextView)findViewById(R.id.lapCountDisplay);
		textView.setText(String.valueOf(this.lapCount));
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
			case R.id.stats: {
				// Intent to start SettingsActivity
				Intent intent = new Intent(this, StatsActivity.class);
				startActivity(intent);
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

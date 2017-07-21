package com.cocolocomoco.tapalap;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class LapCountFragment extends Fragment {
	private int lapCount = 0;
	public static String LAP_COUNT_FRAGMENT_KEY = "lap_count_fragment_key";
	private OnLapCountChangeListener lapCountChangeListener;

	public LapCountFragment() {
		// Required empty public constructor
	}

	public interface OnLapCountChangeListener {
		public void onIncreaseClick(View view);
		public void onDecreaseClick(View view);
		public void onResetClick(View view);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 final Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_lap_count, container, false);

		view.setOnTouchListener((v, motionEvent) -> {
			if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
				//LapCountActivity activity = (LapCountActivity) this.getActivity();
				//activity.increaseLapCount();

				this.onIncreaseClick(view);
				return true;
			}
			return true;
		});

		this.lapCountChangeListener = (OnLapCountChangeListener) getActivity();

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	public void onIncreaseClick(View view) {
		this.lapCount++;

		TextView textView = (TextView) this.getView().findViewById(R.id.lapCountDisplay);
		textView.setText(String.valueOf(this.lapCount));
	}

	public void onDecreaseClick(View view) {
		if (this.lapCount == 0) {
			return;
		}

		//TextView textView = (TextView)getView().findViewById(R.id.lapCountDisplay);
		TextView textView = (TextView) this.getView().findViewById(R.id.lapCountDisplay);
		textView.setText(String.valueOf(--this.lapCount));
	}

	public void onResetClick(View view) {
		this.lapCount = 0;

		//TextView textView = (TextView)findViewById(R.id.lapCountDisplay);
		TextView textView = (TextView) this.getView().findViewById(R.id.lapCountDisplay);
		textView.setText(String.valueOf(this.lapCount));
	}

	/*
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
*/
}

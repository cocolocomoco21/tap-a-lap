package com.cocolocomoco.tapalap;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class LapCountActivity extends AppCompatActivity {

	private int lapCount = 0;
	//private GestureDetectorCompat gestureDetector;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lap_count);

		//this.gestureDetector = new GestureDetectorCompat(this, new MyGestureListener(this));
	}

	/*
	class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

		Context context;

		public MyGestureListener(Context context) {
			this.context = context;
		}

		@Override
		public boolean onDown(MotionEvent event) {
			Log.d("test", "down performed");
			return true;
		}

		@Override
		public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {
			Log.d("test", "Left to Right swipe performed");
			return true;
		}
	}
	*/

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

package com.cocolocomoco.tapalap;

import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class LapCountActivity extends AppCompatActivity {

	int lapCount = 0;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lap_count);
	}

	@Override
	public boolean onTouchEvent(MotionEvent motionEvent) {
		if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
			this.lapCount++;
		}

		TextView textView = (TextView)findViewById(R.id.lapCountDisplay);
		textView.setText(String.valueOf(this.lapCount));

		return true;
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
}

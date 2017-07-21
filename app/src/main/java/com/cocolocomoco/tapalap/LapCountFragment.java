package com.cocolocomoco.tapalap;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class LapCountFragment extends Fragment {
	private int lapCount = 0;
	private OnLapCountChangeListener lapCountChangeListener;

	public LapCountFragment() {
		// Required empty public constructor
	}

	public interface OnLapCountChangeListener {
		void onIncreaseClick(View view);
		void onDecreaseClick(View view);
		void onResetClick(View view);
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
}

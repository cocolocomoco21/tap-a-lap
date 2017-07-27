package com.cocolocomoco.tapalap.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.cocolocomoco.tapalap.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LapCountFragment extends Fragment {


	public LapCountFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 final Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_lap_count, container, false);

		view.setOnTouchListener((v, motionEvent) -> {
			if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
				LapCountActivity activity = (LapCountActivity) this.getActivity();
				activity.increaseLapCount();

				return true;
			}
			return true;
		});

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
}

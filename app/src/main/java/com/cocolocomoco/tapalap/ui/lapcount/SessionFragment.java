package com.cocolocomoco.tapalap.ui.lapcount;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.threeten.bp.Instant;

import com.cocolocomoco.tapalap.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SessionFragment extends Fragment {


	public SessionFragment() {
		// Required empty public constructor
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_session, container, false);
	}

	/**
	 * onClick handler for Start Session button.
	 */
	public void onStartSessionClick(View view) {
		LapCountActivity activity = (LapCountActivity) this.getActivity();
		Double existingRate = activity.getLapPerMilePreference();
		if (existingRate == null) {
			// Display Toast for laps per mile input
			Toast.makeText(activity, R.string.laps_per_mile_required_toast, Toast.LENGTH_SHORT).show();
		} else {
			// Initialize Session, return to show LapCountFragment
			activity.initializeSession(Instant.now(), existingRate);
			activity.showLapCountFragment();
			Toast.makeText(activity, R.string.session_started_toast, Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * onClick handler for Stop Session button.
	 */
	public void onStopSessionClick(View view) {
		// TODO check for if session is already ended

		LapCountActivity activity = (LapCountActivity) this.getActivity();
		if (activity.endSession(Instant.now())) {
			Toast.makeText(activity, R.string.session_ended_toast, Toast.LENGTH_SHORT).show();
		} else {
			// This assumes the only way to fail is by having a null session
			Toast.makeText(activity, R.string.session_must_start_before_ending_toast, Toast.LENGTH_SHORT).show();
		}
	}
}

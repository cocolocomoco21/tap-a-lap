package com.cocolocomoco.tapalap.ui.lapcount;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.threeten.bp.Instant;

import com.cocolocomoco.tapalap.model.session.SessionStatus;
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

		// Need to enter laps per mile rate
		if (existingRate == null) {
			// Display Toast for laps per mile input
			Toast.makeText(activity, R.string.laps_per_mile_required_toast, Toast.LENGTH_SHORT).show();
			return;
		}

		// Start session, use previous status to determine UI output (state change)
		SessionStatus previousStatus = activity.startSession(Instant.now(), existingRate);

		if (previousStatus == SessionStatus.NOT_STARTED) {
			// Initialized new Session, so return to show LapCountFragment
			Toast.makeText(activity, R.string.session_started_toast, Toast.LENGTH_SHORT).show();
			activity.showLapCountFragment();
		} else if (previousStatus == SessionStatus.IN_PROGRESS) {
			// Session already in progress
			Toast.makeText(activity, R.string.session_already_started_toast, Toast.LENGTH_SHORT).show();
		} else if (previousStatus == SessionStatus.PAUSED) {
			// Session was paused, now resume
			Toast.makeText(activity, R.string.session_resumed_toast, Toast.LENGTH_SHORT).show();
			activity.showLapCountFragment();
		} else if (previousStatus == SessionStatus.COMPLETED) {
			// TODO "are you sure you want to start new session?" popup window
			Toast.makeText(activity, "are you sure you want to start a new session? TODO", Toast.LENGTH_SHORT).show();
			activity.initializeSession(Instant.now(), existingRate);
			activity.showLapCountFragment();
		}
	}

	/**
	 * onClick handler for Stop Session button.
	 */
	public void onStopSessionClick(View view) {
		LapCountActivity activity = (LapCountActivity) this.getActivity();

		// End session, use previous status to determine UI output (state change)
		SessionStatus previousStatus = activity.endSession(Instant.now());

		if (previousStatus == SessionStatus.IN_PROGRESS || previousStatus == SessionStatus.PAUSED) {
			// Session in progress, so end
			Toast.makeText(activity, R.string.session_ended_toast, Toast.LENGTH_SHORT).show();
		} else if (previousStatus == SessionStatus.NOT_STARTED) {
			// Session not started
			Toast.makeText(activity, R.string.session_must_start_before_ending_toast, Toast.LENGTH_SHORT).show();
		} else if (previousStatus == SessionStatus.COMPLETED) {
			// Session already ended
			Toast.makeText(activity, R.string.session_already_ended_toast, Toast.LENGTH_SHORT).show();
		}
	}
}

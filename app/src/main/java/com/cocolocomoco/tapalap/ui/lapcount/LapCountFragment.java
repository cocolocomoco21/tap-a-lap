package com.cocolocomoco.tapalap.ui.lapcount;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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
			// Click events, when a user presses the screen to count a lap
			if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
				LapCountActivity activity = getLapCountActivity();

				if (activity.getCurrentSession().isNotStarted()) {
					// Notify user they must start Session, move them to Session page
					Toast.makeText(activity, R.string.session_start_required_toast, Toast.LENGTH_SHORT).show();
					activity.showSessionFragment();
					return false;
				} else {
					onIncreaseClick(view);
				}
			}

			return true;
		});

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	/**
	 * onClick handler for Increase click, or click anywhere on page.
	 */
	public void onIncreaseClick(View view) {
		LapCountActivity activity = getLapCountActivity();
		activity.getCurrentSession().increaseLapCount();

		TextView textView = (TextView)activity.findViewById(R.id.lapCountDisplay);
		textView.setText(String.valueOf(activity.getLapCount()));
	}

	/**
	 * onClick handler for Decrease button.
	 */
	public void onDecreaseClick(View view) {
		LapCountActivity activity = getLapCountActivity();
		activity.getCurrentSession().decreaseLapCount();

		TextView textView = (TextView)activity.findViewById(R.id.lapCountDisplay);
		textView.setText(String.valueOf(activity.getLapCount()));
	}

	private LapCountActivity getLapCountActivity() {
		return (LapCountActivity) this.getActivity();
	}
}

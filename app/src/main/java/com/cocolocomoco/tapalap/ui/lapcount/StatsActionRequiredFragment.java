package com.cocolocomoco.tapalap.ui.lapcount;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cocolocomoco.tapalap.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class StatsActionRequiredFragment extends Fragment {


	public StatsActionRequiredFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_stats_action_required, container, false);
	}
}

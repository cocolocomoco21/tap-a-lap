package com.cocolocomoco.tapalap.model.session;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.threeten.bp.Instant;

import com.cocolocomoco.tapalap.model.lap.Lap;
import com.cocolocomoco.tapalap.model.timeinterval.TimeInterval;


/**
 * This class represents a session of running activity. It contains data pertinent to a running
 * session of the user, including Laps, a TimeInterval for start/stop times, and the laps/mile
 * conversion for this run.
 */
public class Session {
	private List<Lap> laps;
	//private Iterator<Lap> currentLap;
	private int lapCount;		// NOTE: currently this acts as an index, not a count
	private Double lapsPerMileRate;
	private TimeInterval interval;

	/**
	 * Creates a new Session and sets the start time.
	 */
	public Session(Double lapPerMileRate) {
		this.laps = new ArrayList<>();
		this.lapCount = 0;

		this.lapsPerMileRate = lapPerMileRate;

		Instant timestamp = Instant.now();
		this.interval = new TimeInterval(timestamp);

		// Add first lap
		Lap firstLap = new Lap(timestamp);
		this.laps.add(firstLap);
	}


	private Lap getCurrentLap() {
		return this.laps.get(this.lapCount);
	}

	public int getLapCount() {
		return this.lapCount;
		//return this.laps.size();
	}

	public void increaseLapCount() {
		Instant timestamp = Instant.now();

		// Set end time for current lap
		getCurrentLap().setEnd(timestamp);

		// Create new lap with start time at end time of newly completed lap
		Lap nextLap = new Lap(timestamp);
		this.laps.add(nextLap);
		this.lapCount++;
	}

	public void decreaseLapCount() {
		if (this.lapCount == 0) {
			return;
		}

		// Remove current lap
		this.laps.remove(this.lapCount);
		this.lapCount--;

		// Unset new current lap's end time since it is now the current/active lap
		Lap newCurrentLap = getCurrentLap();
		newCurrentLap.unsetEnd();
	}

	public void resetLapCount() {
		this.laps.clear();
		this.lapCount = 0;

		// Add first lap
		Lap firstLap = new Lap(Instant.now());
		this.laps.add(firstLap);
	}

	// TODO for debugging - delete
	public String debugLaps() {
		StringBuilder res = new StringBuilder();
		res.append("Session | " + this.interval.toString() + "\n");
		for(int i = 0; i < laps.size(); i++)  {
			Lap lap = laps.get(i);
			res.append("Lap " + String.valueOf(i) + " | "
					+ "start: " + lap.getInterval().toString() + "\n");
		}

		return res.toString();
	}
}

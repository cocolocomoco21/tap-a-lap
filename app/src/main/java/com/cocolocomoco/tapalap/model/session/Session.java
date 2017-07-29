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
	private List<Lap> laps = new ArrayList<>();
	//private Iterator<Lap> currentLap;
	private int lapCount = 0;
	private Double lapsPerMileRate;
	private TimeInterval interval;

	/**
	 * Creates a new Session and sets the start time.
	 */
	public Session(Double lapPerMileRate) {
		this.lapsPerMileRate = lapPerMileRate;

		this.interval = new TimeInterval();
		this.interval.setStart(Instant.now());

	}

}

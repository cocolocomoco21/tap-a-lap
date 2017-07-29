package com.cocolocomoco.tapalap.model.lap;

import org.threeten.bp.Instant;

import com.cocolocomoco.tapalap.model.timeinterval.TimeInterval;


public class Lap {
	private TimeInterval interval;

	/**
	 * Construct a new Lap and set start time.
	 */
	public Lap(Instant start) {
		this.interval = new TimeInterval(start);
	}

	public Lap(Instant start, Instant end) {
		this.interval = new TimeInterval(start, end);
	}


	public Lap setTimeInterval(TimeInterval interval) {
		this.interval = interval;
		return this;
	}

	public Lap setStart(Instant start) {
		this.interval.setStart(start);
		return this;
	}

	public Lap setEnd(Instant end) {
		this.interval.setEnd(end);
		return this;
	}

	public Lap unsetEnd() {
		this.interval.unsetEnd();
		return this;
	}
}

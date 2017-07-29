package com.cocolocomoco.tapalap.model.timeinterval;

import org.threeten.bp.Instant;

/**
 * Class to represent a time interval, specified by a start and end timestamp.
 */
public class TimeInterval {
	private Instant start;
	private Instant end;

	public TimeInterval() {
		this.start = Instant.now();
		this.end = Instant.EPOCH;
	}

	public TimeInterval(Instant start, Instant end) {
		this.start = start;
		this.end = end;
	}


	public Instant getStart() {
		return this.start;
	}

	public Instant getEnd() {
		return this.end;
	}

	public void setStart(Instant start) {
		this.start = start;
	}

	public void setEnd(Instant end) {
		this.end = end;
	}

}

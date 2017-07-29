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
		checkValidInterval(start, end);

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

	/**
	 * Check if specified interval of start and end values is valid
	 * @param start - start of interval
	 * @param end - end of interval
	 * @throws IllegalArgumentException - throws if either start or end is invalid.
	 */
	private void checkValidInterval(Instant start, Instant end) throws IllegalArgumentException {
		if (start == null || end == null) {
			throw new IllegalArgumentException("Start or end of TimeInterval cannot be null");
		}

		if (start.isAfter(end)) {
			throw new IllegalArgumentException("Start of TimeInterval cannot be after end");
		}
	}

}

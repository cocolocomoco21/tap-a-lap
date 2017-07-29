package com.cocolocomoco.tapalap.model.timeinterval;

import org.threeten.bp.Instant;


/**
 * Class to represent a time interval, specified by a start and end timestamp.
 */
public class TimeInterval {
	private Instant start;
	private Instant end;

	/**
	 * Start a TimeInterval AND set start time to current timestamp.
	 */
	public TimeInterval(Instant start) {
		this.start = start;
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

	public TimeInterval setStart(Instant start) {
		this.start = start;

		checkValidInterval(this.start, this.end);
		return this;
	}

	public TimeInterval setEnd(Instant end) {
		this.end = end;

		checkValidInterval(this.start, this.end);
		return this;
	}

	public TimeInterval unsetEnd() {
		this.end = null;
		return this;
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

	// TODO look into Guava's function override?
	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("TimeInterval{");
		sb.append("start=").append(start);
		sb.append(", end=").append(end);
		sb.append('}');
		return sb.toString();
	}
}

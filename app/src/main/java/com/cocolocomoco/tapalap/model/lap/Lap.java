package com.cocolocomoco.tapalap.model.lap;

import org.threeten.bp.Instant;

import com.cocolocomoco.tapalap.model.timeinterval.TimeInterval;


public class Lap {
	private TimeInterval interval;
	
	Lap() {
		// Empty constructor
	}

	Lap(Instant start, Instant end) {
		this.interval = new TimeInterval(start, end);
	}


	public void setTimeInterval(TimeInterval interval) {
		this.interval = interval;
	}

	public void setStart(Instant start) {
		this.interval.setStart(start);
	}

	public void setEnd(Instant end) {
		this.interval.setEnd(end);
	}

}

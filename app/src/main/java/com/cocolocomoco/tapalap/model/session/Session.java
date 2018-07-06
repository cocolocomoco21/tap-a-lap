package com.cocolocomoco.tapalap.model.session;

import java.util.ArrayList;
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
	private SessionStatus status;
	private Double lapsPerMileRate;
	private TimeInterval interval;

	/**
	 * Creates empty Session, to be initialized when "started", to avoid having null checking.
	 */
	public Session() {
		this.status = SessionStatus.NOT_STARTED;
	}

	/**
	 * Initializes a new Session with specified start time and lapsPerMileRate.
	 */
	public void initialize(Instant start, Double lapPerMileRate) {
		// Required for a session
		this.lapsPerMileRate = lapPerMileRate;

		// Initialize lap counting mechanisms
		this.laps = new ArrayList<>();
		this.lapCount = 0;

		// Add first lap
		Lap firstLap = new Lap(start);
		this.laps.add(firstLap);

		// Session's start and end time interval
		this.interval = new TimeInterval(start);

		// Session is now in progress
		this.status = SessionStatus.IN_PROGRESS;
	}


	private Lap getCurrentLap() {
		return this.laps.get(this.lapCount);
	}

	public int getLapCount() {
		return this.lapCount;
		//return this.laps.size();
	}

	public void increaseLapCount() {
		if (!this.canAlter()) {
			return;
		}

		Instant timestamp = Instant.now();

		// Set end time for current lap
		getCurrentLap().setEnd(timestamp);

		// Create new lap with start time at end time of newly completed lap
		Lap nextLap = new Lap(timestamp);
		this.laps.add(nextLap);
		this.lapCount++;
	}

	public void decreaseLapCount() {
		if (!this.canAlter() || this.lapCount == 0) {
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
		if (!this.canAlter()) {
			return;
		}

		this.laps.clear();
		this.lapCount = 0;

		// Add first lap
		Lap firstLap = new Lap(Instant.now());
		this.laps.add(firstLap);
	}

	private boolean canAlter() {
		return this.status == SessionStatus.IN_PROGRESS;
	}

	public boolean isNotStarted() {
		return this.status == SessionStatus.NOT_STARTED;
	}

	// TODO for debugging - delete
	public String debugLaps() {
		StringBuilder res = new StringBuilder();
		if (!this.isNotStarted()) {
			res.append("Session | lapsPerMile: " + this.lapsPerMileRate + " | " + this.interval.toString() + "\n");
			for (int i = 0; i < laps.size(); i++) {
				Lap lap = laps.get(i);
				res.append("Lap " + String.valueOf(i) + " | "
						+ "start: " + lap.getInterval().toString() + "\n");
			}
		}

		return res.toString();
	}

	/**
	 * Start this Session.
	 * @return SessionStatus - the previous status for the session (for the UI to use to gauge state).
	 */
	public SessionStatus startSession() {
		SessionStatus previousStatus = updateStatusAfterStartAttempt();
		return previousStatus;
	}

	/**
	 * End this Session, using the specified end time as the end of the Session.
	 * @param end - end timestamp of the session.
	 * @return SessionStatus - the previous status for the session (for the UI to use to gauge state).
	 */
	public SessionStatus endSession(Instant end) {
		SessionStatus previousStatus = updateStatusAfterCompleteAttempt();

		// Session is able to be ended
		if (previousStatus == SessionStatus.IN_PROGRESS || previousStatus == SessionStatus.PAUSED) {
			// Set end time for current lap
			getCurrentLap().setEnd(end);

			// Set end time for interval, set status to complete
			this.interval.setEnd(end);
		}

		return previousStatus;
	}

	private SessionStatus updateStatusAfterStartAttempt() {
		SessionStatus status = this.status;

		// TODO "NOT_STARTED" doesn't happen yet, but change design to have this conditional actually get hit

		// NOT_STARTED -> IN_PROGRESS
		// IN_PROGRESS -> (no change)
		// PAUSED -> IN_PROGRESS
		// COMPLTED -> IN_PROGRESS
		if (status == SessionStatus.NOT_STARTED || status == SessionStatus.PAUSED || status == SessionStatus.COMPLETED) {
			this.status = SessionStatus.IN_PROGRESS;
		}

		return status;
	}

	private SessionStatus updateStatusAfterPauseAttempt() {
		// TODO
		return null;
	}

	private SessionStatus updateStatusAfterCompleteAttempt() {
		SessionStatus status = this.status;

		// NOT_STARTED -> (no change)
		// IN_PROGRESS -> COMPLTED
		// PAUSED -> COMPLETED
		// COMPLTED -> (no change)
		if (status == SessionStatus.IN_PROGRESS || status == SessionStatus.PAUSED) {
			this.status = SessionStatus.COMPLETED;
		}

		return status;
	}
}

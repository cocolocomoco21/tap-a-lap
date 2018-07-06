package com.cocolocomoco.tapalap.manager;

import java.util.List;

import org.threeten.bp.Instant;

import com.cocolocomoco.tapalap.model.session.Session;
import com.cocolocomoco.tapalap.model.session.SessionStatus;


/**
 * Class designed to manage the interaction and containment of one or many Sessions.
 */
public class SessionManager {

	private Session currentSession;


	public SessionManager() {
		this.currentSession = new Session();
	}


	/**
	 * Initialize session with the specified start timestamp and lapPerMileRate
	 * @param start - start timestamp of this Session.
	 * @param lapsPerMileRate - lap per mile rate for this Session.
	 */
	private void initializeSession(Instant start, Double lapsPerMileRate) {
		// Initialize current Session with lapsPerMileRate to start a Session
		this.currentSession.initialize(start, lapsPerMileRate);
	}

	/**
	 * Begin a new Session after one has already been started.
	 * @param start - start timestamp of this session, should it need to be initialized.
	 * @param lapsPerMileRate - lap per mile rate.
	 */
	public void beginNewSession(Instant start, Double lapsPerMileRate) {
		// TODO this is kind of a hack and a more permanent solution might want to be devised.
		// If not that's okay too kind of

		// Save existing Session to disk
		boolean isSaved = saveSessionToDisk(this.currentSession);

		if (!isSaved) {
			// Notify user of failure
			// TODO exception?
			// return;
		}

		initializeSession(start, lapsPerMileRate);
	}

	/**
	 * Start the session. Initialize the session if empty, and otherwise attempt to start/resume an
	 * already existing session, if able. Regardless, return the previous session status.
	 * @param start - start timestamp of this session, should it need to be initialized.
	 * @param lapsPerMileRate - lap per mile rate for this session.
	 * @return SessionStatus - the previous status for the session (for the UI to use to gauge state).
	 */
	public SessionStatus startSession(Instant start, Double lapsPerMileRate) {
		if (this.currentSession.isNotStarted()) {
			initializeSession(start, lapsPerMileRate);
			return SessionStatus.NOT_STARTED;
		}

		return this.currentSession.startSession();
	}

	/**
	 * End the session if able. Regardless, return the previous session status.
	 * @param end - end timestamp of the session.
	 * @return SessionStatus - the previous status for the session (for the UI to use to gauge state).
	 */
	public SessionStatus endCurrentSession(Instant end) {
		if (isSessionNotStarted()) {
			return SessionStatus.NOT_STARTED;
		}

		return this.currentSession.endSession(end);

	}

	private boolean isSessionNotStarted() {
		return this.currentSession.isNotStarted();
	}

	public Session getCurrentSession() {
		return this.currentSession;
	}

	private List<Session> loadSessionsFromDisk(int numSessions) {
		// TODO - this will (likely) be done in the future
		return null;
	}

	private boolean saveSessionToDisk(Session session) {
		// TODO - this will (likely) be done in the future
		return false;
	}
}

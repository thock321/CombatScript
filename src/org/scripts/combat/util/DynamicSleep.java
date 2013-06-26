package org.scripts.combat.util;

import org.powerbot.game.api.util.Timer;

/**
 * A dynamic sleeper.
 * @author Thock321
 *
 */
public abstract class DynamicSleep {
	
	private Timer timeoutTimer;
	
	/**
	 * Creates a new instance of dynamic sleep.
	 * @param timeoutTimer A timer so the dynamic sleep times out if the condition has not been met for a period of time.  This is to prevent an infinite loop.
	 */
	public DynamicSleep(Timer timeoutTimer) {
		this.timeoutTimer = timeoutTimer;
	}
	
	/**
	 * Checks if the condition to stop the dynamic sleep has been met.
	 * @return Returns <code>true</code> if the condition has been met, otherwise it returns <code>false</code>.
	 */
	public abstract boolean conditionMet();
	
	/**
	 * Starts the dynamic sleeper.
	 */
	public void execute() {
		while (timeoutTimer.isRunning() && !conditionMet()) {
			//do nothing
		}
	}

}

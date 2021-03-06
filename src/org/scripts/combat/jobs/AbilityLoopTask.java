package org.scripts.combat.jobs;

import org.powerbot.core.script.job.LoopTask;
import org.powerbot.game.api.methods.interactive.Players;

import sk.action.ActionBar;

/**
 * A loop task to handle abilities.
 * @author Thock321
 */
public class AbilityLoopTask extends LoopTask {

	@Override
	public int loop() {
		if (Players.getLocal().isInCombat() && Players.getLocal().getInteracting() != null) {
			if (!ActionBar.isExpanded()) {
				ActionBar.expand(true);
			}
	        
			for (int i = 0; i < 12; i++) {
				if (ActionBar.getNode(i).canUse()) {
					ActionBar.getNode(i).send();
					break;
				}
	
			}
		}
		return 1000;
	}

}

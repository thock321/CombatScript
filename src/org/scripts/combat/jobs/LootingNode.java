package org.scripts.combat.jobs;

import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.GroundItems;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.node.GroundItem;
import org.scripts.combat.Loot;
import org.scripts.combat.ScriptState;
import org.scripts.combat.Variables;
import org.scripts.combat.util.DynamicSleep;

/**
 * A node that handles the looting part of the script.
 * @author Thock321
 *
 */
public class LootingNode extends Node {
	
	public LootingNode(Variables vars) {
		this.vars = vars;
	}
	
	private Variables vars;
	
	private GroundItem itemToLoot;

	@Override
	public boolean activate() {
		itemToLoot = GroundItems.getNearest(new Filter<GroundItem>() {

			@Override
			public boolean accept(GroundItem g) {
				for (Loot loot : vars.getToLoot()) {
					if (g.getId() == loot.getItemId()) {
						return true;
					}
				}
				return false;
			}
		
		});
		return itemToLoot != null;
	}
	
	private DynamicSleep sleeper;

	@Override
	public void execute() {
		vars.setCurrentState(ScriptState.LOOTING);
		itemToLoot.interact("Take", itemToLoot.getGroundItem().getName());
		sleeper = new DynamicSleep(new Timer(20000)) {

			@Override
			public boolean conditionMet() {
				return Players.getLocal().isMoving() == false;
			}
			
		};
		sleeper.execute();
	}

}

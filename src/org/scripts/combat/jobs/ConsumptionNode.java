package org.scripts.combat.jobs;

import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Tabs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Inventory;
import org.scripts.combat.CombatScript;
import org.scripts.combat.ScriptState;

/**
 * A node that handles the consumption of food.
 * @author Thock321
 *
 */
public class ConsumptionNode extends Node {
	
	public ConsumptionNode(CombatScript script) {
		this.script = script;
	}
	
	private CombatScript script;

	@Override
	public boolean activate() {
		return Players.getLocal().getHealthPercent() <= 65 && script.getVars().getFoodToEat() > 0;
	}

	@Override
	public void execute() {
		if (!Inventory.contains(script.getVars().getFoodToEat()) && script.getVars().getBankPath() == null) {
			script.shutdown();
			return;
		}
		if (!Tabs.INVENTORY.isOpen()) {
			Tabs.INVENTORY.open();
			Task.sleep(100, 200);
		}
		script.getVars().setCurrentState(ScriptState.CONSUMING);
		Inventory.getItem(script.getVars().getFoodToEat()).getWidgetChild().interact("Eat");
		Task.sleep(200, 300);
	}

}

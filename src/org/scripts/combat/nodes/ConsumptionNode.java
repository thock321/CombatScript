package org.scripts.combat.nodes;

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

	@Override
	public boolean activate() {
		return Players.getLocal().getHealthPercent() <= 65 && CombatScript.getInstance().getVars().getFoodToEat() > 0;
	}

	@Override
	public void execute() {
		if (!Inventory.contains(CombatScript.getInstance().getVars().getFoodToEat()) && CombatScript.getInstance().getVars().getBankPath() == null) {
			CombatScript.getInstance().shutdown();
			return;
		}
		if (!Tabs.INVENTORY.isOpen()) {
			Tabs.INVENTORY.open();
			Task.sleep(100, 200);
		}
		CombatScript.getInstance().getVars().setCurrentState(ScriptState.CONSUMING);
		Inventory.getItem(CombatScript.getInstance().getVars().getFoodToEat()).getWidgetChild().interact("Eat");
		Task.sleep(200, 300);
	}

}

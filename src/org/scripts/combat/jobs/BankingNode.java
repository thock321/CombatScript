package org.scripts.combat.jobs;

import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.util.Timer;
import org.scripts.combat.CombatScript;
import org.scripts.combat.ScriptState;
import org.scripts.combat.util.DynamicSleep;

/**
 * A node that allows the player to bank items.
 * @author Thock321
 *
 */
public class BankingNode extends Node {

	@Override
	public boolean activate() {
		return !Inventory.contains(CombatScript.getInstance().getVars().getFoodToEat()) && CombatScript.getInstance().getVars().getBankPath() != null;
	}
	
	private DynamicSleep sleeper;

	@Override
	public void execute() {
		if (Bank.open()) {
			CombatScript.getInstance().getVars().setCurrentState(ScriptState.BANKING);
			Bank.depositInventory();
			Bank.withdraw(CombatScript.getInstance().getVars().getFoodToEat(), 20);
			sleeper = new DynamicSleep(new Timer(500)) {

				@Override
				public boolean conditionMet() {
					return Inventory.contains(CombatScript.getInstance().getVars().getFoodToEat());
				}
				
			};
			sleeper.execute();
			if (Bank.close()) {
				CombatScript.getInstance().getVars().setCurrentState(ScriptState.WALKING_TO_COMBAT_AREA);
			}
		} else {
			CombatScript.getInstance().getVars().setCurrentState(ScriptState.WALKING_TO_BANK);
		}
	}

}
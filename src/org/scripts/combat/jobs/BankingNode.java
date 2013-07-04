package org.scripts.combat.jobs;

import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.util.Timer;
import org.scripts.combat.ScriptState;
import org.scripts.combat.Variables;
import org.scripts.combat.util.DynamicSleep;

/**
 * A node that allows the player to bank items.
 * @author Thock321
 *
 */
public class BankingNode extends Node {
	
	public BankingNode(Variables vars) {
		this.vars = vars;
	}
	
	private Variables vars;

	@Override
	public boolean activate() {
		return !Inventory.contains(vars.getFoodToEat()) && vars.getBankPath() != null;
	}
	
	private DynamicSleep sleeper;

	@Override
	public void execute() {
		if (Bank.open()) {
			vars.setCurrentState(ScriptState.BANKING);
			Bank.depositInventory();
			Bank.withdraw(vars.getFoodToEat(), 20);
			sleeper = new DynamicSleep(new Timer(500)) {

				@Override
				public boolean conditionMet() {
					return Inventory.contains(vars.getFoodToEat());
				}
				
			};
			sleeper.execute();
			if (Bank.close()) {
				vars.setCurrentState(ScriptState.WALKING_TO_COMBAT_AREA);
			}
		} else {
			vars.setCurrentState(ScriptState.WALKING_TO_BANK);
		}
	}

}

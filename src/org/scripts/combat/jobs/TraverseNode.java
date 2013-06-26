package org.scripts.combat.jobs;

import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.util.Timer;
import org.scripts.combat.CombatScript;
import org.scripts.combat.ScriptState;
import org.scripts.combat.util.DynamicSleep;

public class TraverseNode extends Node {

	@Override
	public boolean activate() {
		return CombatScript.getInstance().getVars().getCurrentState() == ScriptState.WALKING_TO_BANK || 
				CombatScript.getInstance().getVars().getCurrentState() ==  ScriptState.WALKING_TO_COMBAT_AREA;
	}
	
	private DynamicSleep sleeper;

	@Override
	public void execute() {
		switch (CombatScript.getInstance().getVars().getCurrentState()) {
		case WALKING_TO_BANK:
			Walking.newTilePath(CombatScript.getInstance().getVars().getBankPath()).traverse();
			sleeper = new DynamicSleep(new Timer(180000)) {

				@Override
				public boolean conditionMet() {
					return Players.getLocal().isMoving() == false;
				}
				
			};
			sleeper.execute();
			break;
		case WALKING_TO_COMBAT_AREA:
			Walking.newTilePath(CombatScript.getInstance().getVars().getBankPath()).reverse().traverse();
			sleeper = new DynamicSleep(new Timer(180000)) {

				@Override
				public boolean conditionMet() {
					return Players.getLocal().isMoving() == false;
				}
				
			};
			break;
		default:
			break;
		
		}
	}

}

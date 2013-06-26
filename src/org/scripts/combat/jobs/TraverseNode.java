package org.scripts.combat.jobs;

import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Walking;
import org.scripts.combat.CombatScript;
import org.scripts.combat.ScriptState;

public class TraverseNode extends Node {

	@Override
	public boolean activate() {
		return CombatScript.getInstance().getVars().getCurrentState() == ScriptState.WALKING_TO_BANK || 
				CombatScript.getInstance().getVars().getCurrentState() ==  ScriptState.WALKING_TO_COMBAT_AREA;
	}
	
	@Override
	public void execute() {
		switch (CombatScript.getInstance().getVars().getCurrentState()) {
		case WALKING_TO_BANK:
			Walking.newTilePath(CombatScript.getInstance().getVars().getBankPath()).traverse();
			break;
		case WALKING_TO_COMBAT_AREA:
			Walking.newTilePath(CombatScript.getInstance().getVars().getBankPath()).reverse().traverse();
			break;
		default:
			break;
		
		}
	}

}

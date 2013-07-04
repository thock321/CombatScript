package org.scripts.combat.jobs;

import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Walking;
import org.scripts.combat.ScriptState;
import org.scripts.combat.Variables;

/**
 * A node to handle traversing.  
 * @author Thock321
 *
 */
public class TraverseNode extends Node {
	
	public TraverseNode(Variables vars) {
		this.vars = vars;
	}
	
	private Variables vars;

	@Override
	public boolean activate() {
		return vars.getCurrentState() == ScriptState.WALKING_TO_BANK || 
				vars.getCurrentState() ==  ScriptState.WALKING_TO_COMBAT_AREA;
	}
	
	@Override
	public void execute() {
		switch (vars.getCurrentState()) {
		case WALKING_TO_BANK:
			Walking.newTilePath(vars.getBankPath()).traverse();
			break;
		case WALKING_TO_COMBAT_AREA:
			Walking.newTilePath(vars.getBankPath()).reverse().traverse();
			break;
		default:
			break;
		
		}
	}

}

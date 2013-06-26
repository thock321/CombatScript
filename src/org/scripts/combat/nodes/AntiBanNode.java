package org.scripts.combat.nodes;

import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Tabs;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.wrappers.node.SceneObject;
import org.scripts.combat.CombatScript;
import org.scripts.combat.ScriptState;

/**
 * A node handling anti-ban functions.
 * @author Thock321
 *
 */
public class AntiBanNode extends Node {

	@Override
	public boolean activate() {
		return CombatScript.getInstance().getVars().getCurrentState() == ScriptState.FIGHTING && Random.nextInt(0, 50) == 1;
	}

	@Override
	public void execute() {
		int random = Random.nextInt(0, 1000);
		if (random <= 700) {
			int randomCamera = Random.nextInt(1, 3);
			switch (randomCamera) {
			case 1:
				Camera.setAngle(Random.nextInt(0, 359));
				break;
			case 2:
				Camera.setPitch(Random.nextInt(0, 100));
				break;
			case 3:
				Camera.setAngle(Random.nextInt(0, 359));
				Camera.setPitch(Random.nextInt(0, 100));
				break;
			}

		} else if (random <= 950) {
			Mouse.move(Mouse.getX() + Random.nextInt(-50, 50), Mouse.getY() + Random.nextInt(-50, 50));
		} else if (random <= 975) {
			Tabs.STATS.open();
			Task.sleep(100, 200);
			Skills.getWidgetChild(Random.nextInt(1, 150)).hover();
			Task.sleep(1000, 2000);
			Tabs.INVENTORY.open();
		} else {
			examineRandomObject();
		}
	}
	
	/**
	 * Examine a random object on the screen.
	 */
	private void examineRandomObject() {
		SceneObject[] objects = SceneEntities.getLoaded(new Filter<SceneObject>() {

			@Override
			public boolean accept(SceneObject o) {
				return o != null;
			}
			
		});
		SceneObject randomObject = objects[Random.nextInt(0, objects.length - 1)];
		if (randomObject.isOnScreen()) {
			randomObject.interact("Examine");
		} else {
			examineRandomObject();
		}
	}


}

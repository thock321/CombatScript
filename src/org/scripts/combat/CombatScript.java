package org.scripts.combat;

import java.awt.Graphics;

import javax.swing.SwingUtilities;

import org.powerbot.core.event.listeners.PaintListener;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.core.script.job.state.Tree;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.Game;
import org.scripts.combat.graphics.Paint;
import org.scripts.combat.graphics.StartupGUI;
import org.scripts.combat.jobs.*;

/**
 * A combat script.
 * @author Thock321
 *
 */
@Manifest(authors = { "Thock321" }, description = "A combat script to handle combat.", name = "Thock321's Combat Script")
public class CombatScript extends ActiveScript implements PaintListener {
	
	private Tree nodeTree;
	
	private Node currentNode;
	
	private Variables vars = new Variables();
	
	private Paint paint = new Paint();
	
	@Override
	public void onStart() {
		nodeTree = new Tree(new Node[] {new CombatNode(vars), new BankingNode(vars), new ConsumptionNode(this), new LootingNode(vars), new AntiBanNode(vars)});
		paint.initializeVariables();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					StartupGUI frame = new StartupGUI(getInstance());
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		getContainer().submit(new AbilityLoopTask());
	}

	@Override
	public int loop() {
		if (nodeTree == null || Game.getClientState() != Game.INDEX_MAP_LOADED || vars.getNpcTypesToAttack().size() == 0)
			return 1000;
		currentNode = nodeTree.state();
		if (currentNode != null) {
			nodeTree.set(currentNode);
			getContainer().submit(currentNode);
			currentNode.join();
		}
		return 50;
	}
	
	private CombatScript getInstance() {
		return this;
	}

	/**
	 * Gets the variables object the combat script uses.
	 * @return The variables object.
	 */
	public Variables getVars() {
		return vars;
	}

	@Override
	public void onRepaint(Graphics arg0) {
		paint.setPaint(arg0, vars);
	}

}

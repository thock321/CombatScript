package org.scripts.combat.graphics;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.util.Timer;
import org.scripts.combat.Variables;
import org.scripts.combat.util.Util;

/**
 * A paint class for the script's paint.
 * @author Albert's_Computer
 *
 */
public class Paint {

	private Timer timer = new Timer(0);
	
	private int attackXP, strengthXP, defenseXP, rangedXP, magicXP;
	
	private Image cursor = getCursor();
	
	private Image getCursor() {
		try {
			return ImageIO.read(new URL("http://cur.cursors-4u.net/user/images1/use3.png"));
		} catch (IOException e) {
			return null;
		}
	}
	
	/**
	 * Initializes the variables.
	 */
	public void initializeVariables() {
		attackXP = Skills.getExperience(Skills.ATTACK);
		strengthXP = Skills.getExperience(Skills.STRENGTH);
		defenseXP = Skills.getExperience(Skills.DEFENSE);
		rangedXP = Skills.getExperience(Skills.RANGE);
		magicXP = Skills.getExperience(Skills.MAGIC);
	}
	
	/**
	 * Sets the paint.
	 * @param graphic The Graphics object.
	 */
	public void setPaint(Graphics graphic, Variables vars) {
		Graphics2D g = (Graphics2D) graphic;
		g.drawString("Time ran: " + timer.toElapsedString(), 13, 100);
		g.drawString("State: " + Util.capitalize(vars.getCurrentState().toString().toLowerCase()), 13, 115);
		g.drawString("Attack XP Gained: " + (Skills.getExperience(Skills.ATTACK) - attackXP), 13, 130);
		g.drawString("Strength XP gained: " + (Skills.getExperience(Skills.STRENGTH) - strengthXP), 13, 145);
		g.drawString("Defense XP Gained: " + (Skills.getExperience(Skills.DEFENSE) - defenseXP), 13, 160);
		g.drawString("Ranged XP Gained: " + (Skills.getExperience(Skills.RANGE) - rangedXP), 13, 175);
		g.drawString("Magic XP Gained: " + (Skills.getExperience(Skills.MAGIC) - magicXP), 13, 190);
		g.drawImage(cursor, Mouse.getX() - 31, Mouse.getY() - 31, null);
	}

}

package org.scripts.combat;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.powerbot.game.api.wrappers.Tile;

/**
 * An object containing various variables that the script uses.
 * @author Thock321
 *
 */
public class Variables {
	
	private ScriptState currentState = ScriptState.LOADING;

	/**
	 * Gets the current state.
	 * @return The current state;.
	 */
	public ScriptState getCurrentState() {
		return currentState;
	}

	/**
	 * Sets the current state.
	 * @param currentState The current state.
	 */
	public void setCurrentState(ScriptState currentState) {
		this.currentState = currentState;
	}

	private List<NPCType> npcTypesToAttack = new LinkedList<NPCType>();
	
	/**
	 * Adds an npc type to attack.
	 * @param type The npc type.
	 */
	public void addNpcTypeToAttack(NPCType type) {
		npcTypesToAttack.add(type);
	}
	
	/**
	 * Gets the npc types to attack.
	 * @return The npc types to attack.
	 */
	public List<NPCType> getNpcTypesToAttack() {
		return Collections.unmodifiableList(npcTypesToAttack);
	}
	
	private List<Loot> toLoot = new LinkedList<Loot>();
	
	/**
	 * Adds a loot to loot.
	 * @param loot The loot.
	 */
	public void addLootToLoot(Loot loot) {
		toLoot.add(loot);
	}
	
	/**
	 * Gets the loots to loot.
	 * @return The loots to loot.
	 */
	public List<Loot> getToLoot() {
		return Collections.unmodifiableList(toLoot);
	}
	
	/**
	 * Gets the food id to eat.
	 * @return The food id to eat.
	 */
	public int getFoodToEat() {
		return foodToEat;
	}

	/**
	 * Sets the food id to eat.
	 * @param foodToEat The food id to eat.
	 */
	public void setFoodToEat(int foodToEat) {
		this.foodToEat = foodToEat;
	}

	/**
	 * Gets the tile path to the bank.
	 * @return The tile path to the bank.
	 */
	public Tile[] getBankPath() {
		return bankPath;
	}

	/**
	 * Sets the tile path to the bank.
	 * @param bankPath The tile path do the bank.
	 */
	public void setBankPath(Tile[] bankPath) {
		this.bankPath = bankPath;
	}
	
	/**
	 * Clears the bank path.
	 */
	public void clearBankPath() {
		bankPath = null;
	}

	private int foodToEat;
	
	private Tile[] bankPath;
}

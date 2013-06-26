package org.scripts.combat;

/**
 * Represents loot.
 * @author Thock321
 *
 */
public class Loot {
	
	private int itemId;
	
	/**
	 * Creates a new instance of Loot.
	 * @param itemId The item id of the loot to be looted.
	 */
	public Loot(int itemId) {
		this.itemId = itemId;
	}
	
	/**
	 * Gets the item id of the loot.
	 * @return The item id.
	 */
	public int getItemId() {
		return itemId;
	}

}

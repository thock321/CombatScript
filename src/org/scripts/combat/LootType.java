package org.scripts.combat;

/**
 * Represents a type of loot.
 * @author Thock321
 *
 */
public class LootType {
	
	private int itemId;
	
	/**
	 * Creates a new instance of LootType.
	 * @param itemId The item id of the loot to be looted.
	 */
	public LootType(int itemId) {
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

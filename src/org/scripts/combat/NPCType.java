package org.scripts.combat;

/**
 * Represents a type of NPC.
 * @author Thock321
 */
public class NPCType {
	
	private String name;
	private int level;
	
	/**
	 * Creates a new instance of NPCType.
	 * @param name The name of the npc.
	 * @param level The level of the npc.
	 */
	public NPCType(String name, int level) {
		this.name = name;
		this.level = level;
	}
	
	/**
	 * Gets the name of the npc type.
	 * @return The name.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Gets the level of the npc type.
	 * @return The level.
	 */
	public int getLevel() {
		return level;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof NPCType) {
			NPCType nt = (NPCType) o;
			return nt.getName().equalsIgnoreCase(name) && nt.getLevel() == level;
		}
		return false;
	}

}

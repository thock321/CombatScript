package org.scripts.combat.util;

/**
 * A utilities class.
 * @author Albert's_Computer
 *
 */
public class Util {
	
	/**
	 * Prevents instantiation.
	 */
	private Util() {
		
	}
	
	/**
	 * Capitalizes the first letter of the provided string.
	 * @param string The string.
	 * @return The string with the first letter capitalized.
	 */
	public static String capitalize(String string) {
		return string.substring(0, 1).toUpperCase() + string.substring(1);
	}

}

package org.scripts.combat.util;

/**
 * A utilities class.
 * @author Albert's_Computer
 *
 */
public class Util {
	
	private Util() {
		
	}
	
	/**
	 * Capitalizes the first letter of the provided string.
	 * @param string The string.
	 * @return The string with the first letter capitalized.
	 */
	public static String capitalize(String string) {
		return string.toUpperCase().charAt(0) + string.substring(1);
	}

}

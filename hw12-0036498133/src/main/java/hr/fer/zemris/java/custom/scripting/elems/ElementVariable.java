package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class that inherits Element and represents a variable
 * 
 * @author Rafael Josip Penić
 * @version 1.0
 *
 */
public class ElementVariable extends Element {

	/**
	 * Read-only string property that represents the name of the variable
	 */
	private String name;

	/**
	 * Constructor for ElementVariable
	 * 
	 * @param name
	 *            name of the variable about to be allocated
	 * @throws IllegalArgumentException
	 *             if the given name of the variable doesn't follow variable-naming
	 *             rules(first character must be a letter followed by zero or more
	 *             digits, letters or underscores)
	 */
	public ElementVariable(String name) {
		if (!checkValidity(name))
			throw new IllegalArgumentException("Variable name is not valid.");
		this.name = name;
	}

	@Override
	public String asText() {
		return name;
	}

	/**
	 * Checks if the given string is a valid variable name
	 * 
	 * @param name
	 *            string about to be tested for variable name validity
	 * @return true if the given string is a valid variable name and false if not
	 */
	private static boolean checkValidity(String name) {

		if (!Character.isLetter(name.charAt(0)))
			return false;

		for (int i = 1; i < name.length(); i++) {
			char c = name.charAt(i);
			if (!(Character.isLetter(c) || Character.isDigit(c) || c == '_'))
				return false;
		}

		return true;
	}
}

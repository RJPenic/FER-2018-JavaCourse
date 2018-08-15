package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class that inherits Element and represents a function
 * 
 * @author Rafael Josip Penić
 * @version 1.0
 *
 */
public class ElementFunction extends Element {

	/**
	 * Read-only string property of the ElementFunction which represents the name of
	 * the function
	 */
	private String name;

	/**
	 * Constructor for ElementFunction
	 * 
	 * @param name
	 *            name of the function about to be allocated
	 * @throws IllegalArgumentException
	 *             if the given name of the function doesn't follow function-naming
	 *             rules(first character must be '@' followed by zero or more
	 *             digits, letters or underscores)
	 */
	public ElementFunction(String name) {
		if (!checkValidity(name))
			throw new IllegalArgumentException("Function name is not valid.");

		this.name = name;
	}

	@Override
	public String asText() {
		return name;
	}

	/**
	 * Checks if the given string is a valid function name
	 * 
	 * @param name
	 *            string about to be tested for function name validity
	 * @return true if the given string is a valid function name and false if not
	 */
	private static boolean checkValidity(String name) {

		if (!(name.charAt(0) == '@'))
			return false;

		for (int i = 1; i < name.length(); i++) {
			char c = name.charAt(i);
			if (!(Character.isLetter(c) || Character.isDigit(c) || c == '_'))
				return false;
		}

		return true;
	}
}

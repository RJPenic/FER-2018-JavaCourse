package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class that inherits Element and represents an operator
 * 
 * @author Rafael Josip Penić
 * @version 1.0
 *
 */
public class ElementOperator extends Element {

	/**
	 * Read-only string property of the ElementOperator which represents the string
	 * representation of the operator
	 */
	private String symbol;

	/**
	 * Constructor for ElementOperator
	 * 
	 * @param symbol
	 *            string representation of the specific operator about to be
	 *            allocated
	 * @throws IllegalArgumentException
	 *             if the given string doesn't follow operator-string rules(must be
	 *             one of these : "+", "-", "/", "^" or "*")
	 */
	public ElementOperator(String symbol) {
		if (!checkValidity(symbol))
			throw new IllegalArgumentException("Entered symbol is not valid.");

		this.symbol = symbol;
	}

	@Override
	public String asText() {
		return symbol;
	}

	/**
	 * Checks if the given string is a valid operator string
	 * 
	 * @param name
	 *            string about to be tested if it is a valid operator
	 * @return true if the given string is a valid operator and false if not
	 */
	private static boolean checkValidity(String name) {
		if (name.length() != 1)
			return false;

		if (!(name.equals("+") || name.equals("-") || name.equals("/") || name.equals("^") || name.equals("*")))
			return false;

		return true;
	}
}

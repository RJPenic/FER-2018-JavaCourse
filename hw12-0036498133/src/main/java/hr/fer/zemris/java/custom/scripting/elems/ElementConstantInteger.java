package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class that inherits Element and has single read-only integer property
 * 
 * @author Rafael Josip Penić
 * @version 1.0
 *
 */
public class ElementConstantInteger extends Element {

	/**
	 * Integer read-only element property.
	 */
	private int value;

	/**
	 * Constructor for ElementConstantInteger class
	 * 
	 * @param value
	 *            integer number on which the elements property will be initialized
	 */
	public ElementConstantInteger(int value) {
		this.value = value;
	}

	@Override
	public String asText() {
		return Integer.toString(value);
	}

	/**
	 * Getter for the integer read-only property of the element
	 * 
	 * @return integer value of the elements property
	 */
	public int getValue() {
		return value;
	}
}

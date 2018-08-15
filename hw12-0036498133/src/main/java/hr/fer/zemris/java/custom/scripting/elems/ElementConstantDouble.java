package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class that inherits Element and has single read-only double property
 * 
 * @author Rafael Josip Penić
 * @version 1.0
 *
 */
public class ElementConstantDouble extends Element {

	/**
	 * Double read-only element property.
	 */
	private double value;

	/**
	 * Constructor for ElementConstantDouble class
	 * 
	 * @param value
	 *            double number on which the elements property will be initialized
	 */
	public ElementConstantDouble(double value) {
		this.value = value;
	}

	@Override
	public String asText() {
		return Double.toString(value);
	}

	/**
	 * Getter for the double read-only property of the element
	 * 
	 * @return double value of the elements property
	 */
	public double getValue() {
		return this.value;
	}
}
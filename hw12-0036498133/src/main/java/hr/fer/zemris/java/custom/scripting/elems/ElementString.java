package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class that inherits Element and represents strings
 * 
 * @author Rafael Josip Penić
 * @version 1.0
 *
 */
public class ElementString extends Element {

	/**
	 * String read-only property of the element
	 */
	private String value;

	/**
	 * Constructor for ElementString class.
	 * 
	 * @param value
	 *            string on which the elements property will be initialized
	 */
	public ElementString(String value) {
		this.value = value;
	}

	@Override
	public String asText() {
		return value;
	}
}

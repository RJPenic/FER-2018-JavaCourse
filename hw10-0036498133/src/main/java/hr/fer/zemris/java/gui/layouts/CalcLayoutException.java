package hr.fer.zemris.java.gui.layouts;

/**
 * Exception thrown when there is an error while designing GUI with CalcLayout
 * layout manager.
 * 
 * @author Rafael Josip Penić
 *
 */
public class CalcLayoutException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor for CalcLayoutException
	 */
	public CalcLayoutException() {
		super();
	}

	/**
	 * Constructor for CalcLayoutException which constructs an exception with a
	 * given message
	 * 
	 * @param message
	 *            message that more closely describes what caused the exception
	 */
	public CalcLayoutException(String message) {
		super(message);
	}

}

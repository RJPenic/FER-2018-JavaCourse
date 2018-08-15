package hr.fer.zemris.java.custom.scripting.parser;

/**
 * Exception that will be thrown in case SmartScriptParser encounters an error
 * 
 * @author Rafael Josip Penić
 * @version 1.0
 *
 */
public class SmartScriptParserException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs an SmartScriptParserException with no detailed message.
	 */
	public SmartScriptParserException() {
		super();
	}

	/**
	 * Constructs an SmartScriptParserException with the specified detail message.
	 * 
	 * @param message
	 *            detailed message
	 */
	public SmartScriptParserException(String message) {
		super(message);
	}
}

package hr.fer.zemris.java.custom.scripting.tokens;

/**
 * Exception thrown in case SmarScriptLexer encounters an error while reading
 * 
 * @author Rafael Josip Penić
 * @version 1.0
 *
 */
public class SmartScriptLexerException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs an SmartScriptLexerException with no detailed message.
	 */
	public SmartScriptLexerException() {
		super();
	}
	
	/**
	 * Constructs an SmartScriptLexerException with the specified detail message.
	 * 
	 * @param message
	 *            detailed message
	 */
	public SmartScriptLexerException(String message) {
		super(message);
	}
}

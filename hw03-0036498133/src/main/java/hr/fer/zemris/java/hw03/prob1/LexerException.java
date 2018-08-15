package hr.fer.zemris.java.hw03.prob1;

/**
 * Exception thrown in case lexer encounters an error while reading
 * 
 * @author Rafael Josip Penić
 * @version 1.0
 *
 */
public class LexerException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs an LexerException with no detailed message.
	 */
	public LexerException() {
		super();
	}

	/**
	 * Constructs an LexerException with the specified detail message.
	 * 
	 * @param message
	 *            detailed message
	 */
	public LexerException(String message) {
		super(message);
	}
}

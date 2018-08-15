package hr.fer.zemris.java.hw03.prob1;

/**
 * Lexic unit that groups one or more characters from the text
 * 
 * @author Rafael Josip Penić
 * @version 1.0
 *
 */
public class Token {

	/**
	 * Type of the token.
	 */
	TokenType type;

	/**
	 * Object value stored in the token
	 */
	Object value;

	/**
	 * Constructor for token
	 * 
	 * @param type
	 *            type of the token about to be allocated
	 * @param value
	 *            value that will be stored in token about to be allocated
	 */
	public Token(TokenType type, Object value) {
		this.type = type;
		this.value = value;
	}

	/**
	 * Getter for the value of the token
	 * 
	 * @return value stored in the token
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Getter for the type of the token
	 * 
	 * @return type of the token
	 */
	public TokenType getType() {
		return type;
	}
}

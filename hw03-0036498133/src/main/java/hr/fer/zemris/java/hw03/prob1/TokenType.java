package hr.fer.zemris.java.hw03.prob1;

/**
 * Type of the token depending what is stored in it.
 * 
 * @author Rafael Josip Penić
 * @version 1.0
 *
 */
public enum TokenType {
	/**
	 * Type of token that will be returned when the end of the text is reached
	 */
	EOF,
	/**
	 * Type of token that stores the word
	 */
	WORD,
	/**
	 * Type of token that stores a number
	 */
	NUMBER,
	/**
	 * Type of token that stores a symbol
	 */
	SYMBOL
}

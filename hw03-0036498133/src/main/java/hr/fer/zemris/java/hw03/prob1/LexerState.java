package hr.fer.zemris.java.hw03.prob1;

/**
 * Enum that represent possible state of the lexer
 * 
 * @author Rafael Josip Penić
 * @version 1.0
 *
 */
public enum LexerState {
	/**
	 * Basic state where lexer differs numbers and words
	 */
	BASIC,

	/**
	 * Extended state where lexer sees all sequences(no matter if it is the sequence
	 * of numbers, letters or something else) as words
	 */
	EXTENDED
}

package hr.fer.zemris.java.custom.scripting.lexer;

import hr.fer.zemris.java.custom.scripting.elems.Element;

/**
 * Lexic unit that groups one or more characters from the text.
 * This class is used in SmartScriptLexer.
 * 
 * @author Rafael Josip Penić
 * @version 1.0
 *
 */
public class Token {
	
	/**
	 * Type of the token.
	 */
	private TokenType type;
	
	/**
	 * Element value stored in the token
	 */
	private Element value;
	
	/**
	 * Constructor for token
	 * 
	 * @param type
	 *            type of the token about to be allocated
	 * @param value
	 *            element value that will be stored in token about to be allocated
	 */
	public Token(TokenType type, Element value) {
		this.type = type;
		this.value = value;
	}
	
	/**
	 * Getter for the value of the token
	 * 
	 * @return element value stored in the token
	 */
	public Element getValue() {
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

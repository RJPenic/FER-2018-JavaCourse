package hr.fer.zemris.java.custom.scripting.tokens;

/**
 * Type of the token depending what is stored in it.
 * 
 * @author Rafael Josip Penić
 * @version 1.0
 *
 */
public enum TokenType {
	/**
	 * Token type of the token that stores the document text
	 */
	TEXT,
	/**
	 * Token type of the token that stores the arguments of the tag
	 */
	TAGARGUMENT, 
	/**
	 * Open-tag-definition expression
	 */
	OPENTAG, 
	/**
	 * Close-tag-definition expression
	 */
	CLOSETAG,
	/**
	 * Type of token that will be returned when the end of the text is reached
	 */
	EOF
}

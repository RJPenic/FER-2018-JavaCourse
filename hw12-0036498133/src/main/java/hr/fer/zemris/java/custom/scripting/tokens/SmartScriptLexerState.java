package hr.fer.zemris.java.custom.scripting.tokens;

/**
 * Enum that represent possible state of the SmartScriptLexer
 * 
 * @author Rafael Josip Penić
 * @version 1.0
 *
 */
public enum SmartScriptLexerState {
	/**
	 * State of lexer when reading text outside of tags
	 */
	BASIC,
	/**
	 * State of lexer when reading tokens in tag
	 */
	INTAG
}

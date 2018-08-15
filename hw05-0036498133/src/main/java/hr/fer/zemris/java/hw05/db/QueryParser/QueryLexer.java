package hr.fer.zemris.java.hw05.db.QueryParser;

/**
 * Lexer that is used for "lexing" the query
 * 
 * @author Rafael Josip Penić
 *
 */
public class QueryLexer {

	/**
	 * Character array that represents entry text
	 */
	private char[] data;

	/**
	 * Current token
	 */
	private String token;

	/**
	 * Index of the first unprocessed character
	 */
	private int currentIndex;

	/**
	 * Constructor for the QueryLexer
	 * 
	 * @param text
	 *            entry text that will be tokenized
	 */
	public QueryLexer(String text) {
		data = text.toCharArray();
	}

	/**
	 * Generates and return next token
	 * 
	 * @return token that was read
	 * 
	 */
	public String nextToken() {
		while (currentIndex < data.length && Character.isWhitespace(data[currentIndex])) { // skips all the whitespaces at the beginning
			currentIndex++;
		}

		if (currentIndex == data.length) { // if the end of the string is reached, null is returned
			token = null;
			return token;
		}

		StringBuilder sb = new StringBuilder();

		if (Character.isLetter(data[currentIndex])) { // reading a word
			while (currentIndex < data.length && Character.isLetter(data[currentIndex])) {
				sb.append(data[currentIndex++]);
			}
		} else if (data[currentIndex] == '"') { // reading a string(surrounded with quotation marks)
			sb.append(data[currentIndex++]);
			while (currentIndex < data.length && data[currentIndex] != '"') {
				sb.append(data[currentIndex++]);
			}
			if (currentIndex < data.length && data[currentIndex] == '"') { 	// Notice that currentIndex < data.length is
																		   	// necessary
				sb.append(data[currentIndex++]); 							// to avoid IndexOutOfBoundsException
			}
		} else { // reading something that is nor word nor string(E.g. operator)
			while (currentIndex < data.length && !(Character.isWhitespace(data[currentIndex])
					|| Character.isLetter(data[currentIndex]) || data[currentIndex] == '"')) {
				sb.append(data[currentIndex++]);
			}
		}

		token = sb.toString();
		return token;
	}

	/**
	 * Returns last generated token. It does not initiate generation of the next
	 * token.
	 * 
	 * @return last generated token
	 */
	public String getToken() {
		return token;
	}
}

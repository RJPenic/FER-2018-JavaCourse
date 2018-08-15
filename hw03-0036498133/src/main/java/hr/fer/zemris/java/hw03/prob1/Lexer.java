package hr.fer.zemris.java.hw03.prob1;

/**
 * Class that represents a lexer that can read token by token from the given
 * text.
 * 
 * @author Rafael Josip Penić
 * @version 1.0
 *
 */
public class Lexer {

	/**
	 * Character array that represents entry text
	 */
	private char[] data;

	/**
	 * Current token
	 */
	private Token token;

	/**
	 * Index of the first unprocessed character
	 */
	private int currentIndex;

	/**
	 * Current work-state of the lexer
	 */
	private LexerState state;

	/**
	 * Constructor for the lexer
	 * 
	 * @param text
	 *            entry text that will be tokenized
	 */
	public Lexer(String text) {
		if (text == null)
			throw new IllegalArgumentException("It is impossible to determine tokens if the given text is null.");
		state = LexerState.BASIC;
		data = text.toCharArray();
		token = null;
		currentIndex = 0;
	}

	/**
	 * Generates and return next token
	 * 
	 * @return token that was read
	 * @throws LexerException
	 *             in case of an error while reading
	 */
	public Token nextToken() {
		if (currentIndex > data.length)
			throw new LexerException("No more characters to read.");

		while (currentIndex < data.length && (data[currentIndex] == ' ' || data[currentIndex] == '\n'
				|| data[currentIndex] == '\r' || data[currentIndex] == '\t')) {
			currentIndex++;
		}

		if (currentIndex == data.length) {
			token = new Token(TokenType.EOF, null);
			currentIndex++;
			return token;
		}

		if (state == LexerState.BASIC) {
			if (Character.isLetter(data[currentIndex]) || data[currentIndex] == '\\') {
				StringBuilder sb = new StringBuilder();

				while (currentIndex < data.length
						&& (Character.isLetter(data[currentIndex]) || data[currentIndex] == '\\')) {
					if (data[currentIndex] == '\\') {
						currentIndex++;
						if (currentIndex >= data.length
								|| !(Character.isDigit(data[currentIndex]) || data[currentIndex] == '\\'))
							throw new LexerException("'\\' must be followed by either number or another '\\'.");
					}
					sb.append(data[currentIndex++]);
				}

				token = new Token(TokenType.WORD, sb.toString());
			} else if (Character.isDigit(data[currentIndex])) {
				StringBuilder sb = new StringBuilder();

				while (currentIndex < data.length && Character.isDigit(data[currentIndex])) {
					sb.append(data[currentIndex++]);
				}

				try {
					token = new Token(TokenType.NUMBER, Long.parseLong(sb.toString()));
				} catch (NumberFormatException ex) {
					throw new LexerException("Number cannot be shown as long.");
				}
			} else {
				if (data[currentIndex] == '#') {
					switchState();
				}
				token = new Token(TokenType.SYMBOL, data[currentIndex++]);
			}
		} else {
			if (data[currentIndex] == '#') {
				token = new Token(TokenType.SYMBOL, '#');
				switchState();
				currentIndex++;
			} else {
				StringBuilder sb = new StringBuilder();

				while (currentIndex < data.length && data[currentIndex] != ' ' && data[currentIndex] != '\n'
						&& data[currentIndex] != '\r' && data[currentIndex] != '\t' && data[currentIndex] != '#') {
					sb.append(data[currentIndex++]);
				}

				token = new Token(TokenType.WORD, sb.toString());
			}
		}
		return token;
	}

	/**
	 * Returns last generated token. It does not initiate generation of the next
	 * token.
	 * 
	 * @return last generated token
	 */
	public Token getToken() {
		return token;
	}

	/**
	 * Sets the work-state of the lexer
	 * 
	 * @param state
	 *            state in which the lexers state will be set
	 */
	public void setState(LexerState state) {
		if (state == null)
			throw new IllegalArgumentException("Null is not a lexer state.");

		this.state = state;
	}

	/**
	 * Switches lexers state. If the lexer was in BASIC state it set state to
	 * EXTENDED and vice versa.
	 */
	private void switchState() {
		if (LexerState.BASIC == state) {
			setState(LexerState.EXTENDED);
		} else {
			setState(LexerState.BASIC);
		}
	}
}

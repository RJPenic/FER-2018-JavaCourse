package hr.fer.zemris.java.custom.scripting.tokens;

import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;

/**
 * Class that represents a lexer that can read token by token from the given
 * text that will be parsed by SmartScriptParser
 * 
 * @author Rafael Josip Penić
 * @version 1.0
 *
 */
public class SmartScriptLexer {

	/**
	 * Character array that represents entry text
	 */
	private char[] data;
	
	/**
	 * Current token
	 */
	private Token token;
	
	/**
	 * Current work-state of the lexer
	 */
	private SmartScriptLexerState state;
	
	/**
	 * Index of the first unprocessed character
	 */
	private int currentIndex;

	/**
	 * Constructor for the SmartScriptLexer
	 * 
	 * @param text
	 *            entry text that will be tokenized
	 */
	public SmartScriptLexer(String text) {
		if (text == null)
			throw new IllegalArgumentException("It is impossible to determine tokens if the given text is null.");

		state = SmartScriptLexerState.BASIC;
		data = text.toCharArray();
		token = null;
		currentIndex = 0;
	}

	/**
	 * Generates and return next token
	 * 
	 * @return token that was read
	 * @throws SmartScriptLexerException
	 *             in case of an error while reading
	 */
	public Token nextToken() throws SmartScriptLexerException {	/* throws SmartScriptLexerException added because of tagArgumentIdentification and readText methods. */
		
		if (currentIndex > data.length) 							 /* if the currentIndex is greater than length of the character array*/ 
			throw new SmartScriptLexerException("No more characters to read."); /*	that means that the EOF is already read so we throw Exception */
																	 /*	because there is nothing else to read. */
		
		if (currentIndex == data.length) {							 /*	if currentIndex is equal to length of the character array that means */ 
			currentIndex++;											 /*	that all tokens have been read, except the last token : EOF token */
			token = new Token(TokenType.EOF, null);
			return token;
		}
		
		if (currentIndex + 1 < data.length) {
			
			if (data[currentIndex] == '{' && data[currentIndex + 1] == '$') {	/* If the answer is yes, that string is returned as token that is symbol for */
				currentIndex += 2;												/* opening of tag definition and the lexer state is changed. */
				setState(SmartScriptLexerState.INTAG);
				token = new Token(TokenType.OPENTAG, new ElementString("{$"));
				return token;
			}
		}

		if (state == SmartScriptLexerState.BASIC) {								/* if the lexer is in basic state it reads following characters as */
			readText();															/* document text(text that is not in tag) */

		} else {																/* if the program "enters" this else that means lexer is in INTAG state */

			while (currentIndex < data.length && Character.isWhitespace(data[currentIndex])) {				/* this while loop skips all blanks since blank spaces in tags are ignorable(unless they are in string) */
				currentIndex++;
			}

			if(currentIndex == data.length) {
				token = new Token(TokenType.EOF, null);
				return token;
			}
			
			if (currentIndex + 1 < data.length && (data[currentIndex] == '$' && data[currentIndex + 1] == '}')) { 	/* here, lexer checks if the next token is end-of-tag symbol. */
																													/* If so that symbol is read and token of type CLOSETAG will be returned */
				currentIndex += 2;
				setState(SmartScriptLexerState.BASIC);

				token = new Token(TokenType.CLOSETAG, new ElementString("$}"));
			} else if (currentIndex < data.length && data[currentIndex] == '=') { 								/* Now lexer checks if the next token is =-token. Lexer needs to check this case */
				currentIndex++;														/* specifically because character '=' could be a name of a tag.(e.g. there could be "{$=i$}" where */
				token = new Token(TokenType.TAGARGUMENT, new ElementString("="));	/* where '=' and 'i' are connected together and it is vital to prevent them to be read together. */
																					/* There is no need to worry if this if will cause problems in case there is a '=' somewhere deeper in tag */
																					/* because that means that one of the tag arguments is not valid and exception will be thrown anyways */
			} else {		
				boolean isString = false;

				if (data[currentIndex] == '"') { 	/* if we encounter " in a tag that means that the following argument is a string */
					currentIndex++;					/* so we set our "flag" isString on true since we know that we are working with a string. */
					isString = true;
				}

				StringBuilder sb = new StringBuilder();

				while ((!Character.isWhitespace(data[currentIndex]) || isString) && data[currentIndex] != '"') {				/* this loop will read characters until we encounter a blank or a "(that means we encountered a string). */
					if(isString && data[currentIndex] == '\\') {											/* Notice that blanks will be read in case we are in string because data[currentIndex] != ' ' || isString will be true */
						currentIndex++;																		/* because of isString flag. In case loop reaches end of text(currentIndex == data.length) or '$'(that means next token is close-tag-symbol because if it isn't, text is invalid). */
						switch(data[currentIndex]) {														/* '$' will be tolerated if the loop is reading a string(that means isString is true). */
							case 'n':	sb.append("\n");													/* Switch-case is used for a situation when a string is read and lexer encounters '\' so it checks if basic escaping rules are followed. */
										break;																/* If the rules are not followed, exception is thrown. */
																											
							case 'r':	sb.append("\r");
										break;
									
							case 't':	sb.append("\t");
										break;
										
							case '"':	sb.append("\"");
										break;
										
							case '\\':	sb.append("\\");
										break;
										
							default: throw new SmartScriptLexerException("'\\' in string must be followed by one of these characters: n, r, t, \", \\.");
						}
						
						currentIndex++;
					} else {
						sb.append(data[currentIndex++]);
						sb.toString();
						if (currentIndex == data.length || (data[currentIndex] == '$' && !isString))			
							break;																				
					}
				}

				if (currentIndex < data.length && data[currentIndex] == '"') {				/* If the string was read that means that after the previous loop currentIndex is on the last " so we have to move */
					currentIndex++;															/* index by one(so it shows on next token)(currentIndex < data.length is here because we want to prevent IndexOutOfBoundsException) */
				}																			/* If we didn't have currentIndex < data.length there could be a situation when there is a " without its pair and the string will read till */
																							/* the end of the text and currentIndex will be equal to length of the array and data[currentIndex] will not be valid index(it will throw IndexOutOfBoundsException. */
				tagArgumentIdentification(isString, sb);
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
	 * Sets the work-state of the SmartScriptLexer
	 * 
	 * @param state
	 *            state in which the SmartScriptLexers state will be set
	 */
	public void setState(SmartScriptLexerState state) {
		if (state == null)
			throw new IllegalArgumentException("Null is not a lexer state.");

		this.state = state;
	}

	/**
	 * Method that reads the document text(text not in the tag definition brackets)
	 * when the SmartScriptLexer is in BASIC state and stores it in the token.
	 * 
	 * @throws SmartScriptLexerException in case of an error while reading
	 */
	private void readText() {
		StringBuilder sb = new StringBuilder();

		while (currentIndex < data.length) {																			/* This loop reads document text(text that is not in the tag definition brackets). */
			if (data[currentIndex] == '\\') {																			/* Loop iterates until the very end of the text(it can be broken though). */
				currentIndex++;																							/* if '\' is encountered that means that the next character can only be another '\' or '{' (rule given in assignment) */
				if (currentIndex < data.length && (data[currentIndex] == '\\' || data[currentIndex] == '{')) {			/* if it is followed by anything other than those two an exception is thrown */
					sb.append(data[currentIndex++]);																	/* in case of "\{$", "{$" will not be seen as opening tag expression. */
				} else {																								/* Loop also checks for open-tag-expressions and in case it encounters one(without prefix '\') loop will be broken */
					throw new SmartScriptLexerException("In document text '\\' must be followed by either '{' or another '\\'.");
				}
			} else if (currentIndex + 1 < data.length && data[currentIndex] == '{' && data[currentIndex + 1] == '$') {
				break;
			} else {
				sb.append(data[currentIndex++]);
			}
		}

		token = new Token(TokenType.TEXT, new ElementString(sb.toString()));
	}

	/**
	 * Identifies string that was just read and stores it as an appropriate token.
	 * 
	 * @param isString
	 *            flag that is activated if the read string is surrounded by "
	 * @param sb
	 *            String Builder in which the read string is stored
	 * 
	 * @throws SmartScriptLexerException
	 *             in case of an error through the identification process
	 */
	private void tagArgumentIdentification(boolean isString, StringBuilder sb) {

		if (isString) {																		/* if the isString flag is activated that means that read token is a string */
			token = new Token(TokenType.TAGARGUMENT, new ElementString(sb.toString()));
		} else if (sb.toString().startsWith("@")) {											/* Valid function name starts with @ after which follows a letter and after than can follow zero or more letters so if the */
			token = new Token(TokenType.TAGARGUMENT, new ElementFunction(sb.toString()));	/* characters we previously read start with @ that means that function is read */
		
		} else if (Character.isLetter(sb.toString().charAt(0))) {									/* If the string we read starts with a letter it that means that the read token should be variable */
			try {																					/* because only variables can start with a letter(and strings but in this case isString flag is not activated) */
					token = new Token(TokenType.TAGARGUMENT, new ElementVariable(sb.toString()));
			} catch (IllegalArgumentException ex) {
				throw new SmartScriptLexerException(sb.toString() + " is not a valid variable name.");
			}
		} else if (Character.isDigit(sb.toString().charAt(0))) {																/* If the read string starts with number that means that the read token should be a number */
			try {																												/* (or a string, but same as with letters, isString is not activated). If it contains a dot(.) we have a double number */
				if(sb.toString().contains(".")) {																				/* if not we have an Integer(if it parses correctly) */
					token = new Token(TokenType.TAGARGUMENT, new ElementConstantDouble(Double.parseDouble(sb.toString())));
				} else {
					token = new Token(TokenType.TAGARGUMENT, new ElementConstantInteger(Integer.parseInt(sb.toString())));
				}
			} catch (NumberFormatException ex) {
				throw new SmartScriptLexerException(sb.toString() + " cannot be parsed into integer or double.");
			}
		} else if (sb.toString().length() == 1) {															/* all valid operators have length of 1 and here we know we are not working with nor number nor variable nor string nor function */
			try {
				token = new Token(TokenType.TAGARGUMENT, new ElementOperator(sb.toString()));				/* If an invalid operator is entered constructor will throw an IllegalArgumentException that will be caught */
			} catch (IllegalArgumentException ex) {
				throw new SmartScriptLexerException(sb.toString() + " is not a valid operator.");
			}
		} else {
			throw new SmartScriptLexerException("Sequence \"" + sb.toString() + "\" is not readable");					/* if this is reached that means that the loaded string is not recognizable so an exception is thrown with appropriate message */
		}
	}
}

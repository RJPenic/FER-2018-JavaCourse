package hr.fer.zemris.java.custom.scripting.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.tokens.SmartScriptLexer;
import hr.fer.zemris.java.custom.scripting.tokens.SmartScriptLexerException;
import hr.fer.zemris.java.custom.scripting.tokens.Token;
import hr.fer.zemris.java.custom.scripting.tokens.TokenType;



/**
 * Parser that parses given text to appropriate document "tree"
 * 
 * @author Rafael Josip Penić
 * @version 1.0
 *
 */
public class SmartScriptParser {

	/**
	 * Document node that will be "head" of the tree that will be result of text parsing
	 */
	private DocumentNode document;

	/**
	 * Constructor for SmartScriptParser
	 * 
	 * @param text
	 *            text that will be parsed into document tree
	 * 
	 * @throws IllegalArgumentException
	 *             in case the given string is null
	 */
	public SmartScriptParser(String text) {
		if(text == null)
			throw new IllegalArgumentException("String given to parser is not allowed to be null.");
			
		SmartScriptLexer lexer = new SmartScriptLexer(text);

		document = parse(lexer, document);
	}

	/**
	 * Method that parses tokens sent by the given lexer into the document tree
	 * 
	 * @param lexer
	 *            lexer that will send tokens to parser that will be parsed
	 * @param document
	 *            head of the document tree
	 * @return head of the document tree
	 */
	public DocumentNode parse(SmartScriptLexer lexer, DocumentNode document) {
		document = new DocumentNode();						/* First of all, document node is allocated and placed on newly allocated stack */
		Stack<Object> stack = new Stack<>();

		stack.push(document);
		
		try {
			Token t = lexer.nextToken();				/* This command "initiates" process of reading. It reads the first token. */
			
			while (t.getType() != TokenType.EOF) {		/* This while loop iterates until parser reaches end of the text(EOF)(Until token of type EOF is returned) */
	
				if(t.getType() == TokenType.CLOSETAG) {	/* In case close-tag-definition symbol is read, lexer changed its state back to basic so basically there is nothing important */
					t = lexer.nextToken();				/* parser must do. */
					
				} else if (t.getType() == TokenType.TEXT) {						/* If the read token is document text, that node is added as child node to a top node in the stack and after that is done */
					Node tempNode = (Node) stack.peek();						/* next token is read. */
					tempNode.addChildNode(new TextNode(t.getValue().asText()));
					t = lexer.nextToken();
	
				} else if (t.getType() == TokenType.OPENTAG) {					/* In case open-tag-definition expression is read, next token is read and that token defines the type of the just */
					t = lexer.nextToken();										/* opened tag. String of the read token is saved in tempString variable that is then lower cased(because parser should be caps-lock insensitive */
					String tempString = (t.getValue()).asText();
					tempString = tempString.toLowerCase();
	
					if (tempString.equals("for")) {					/* In case of FOR tag, token array of size 4 is allocated(arguments for ForLoopNode constructor will be stored here) and next token is read. */
						Token tempArray[] = new Token[4];			/* Variable i is initialised on 0 and it counts number of arguments in the tag definition. */
																	
						t = lexer.nextToken();
						int i = 0;
						while (t.getType() != TokenType.CLOSETAG && t.getType() != TokenType.EOF && i < 4) {	/* This loop reads "arguments" of FOR tag until the close-tag-expression is read or if 4 arguments are read. */
							tempArray[i++] = t;									/* While this loop iterates, it stores read arguments in previously allocated array. */
							t = lexer.nextToken();
						}
	
						if (t.getType() == TokenType.EOF)															/* In case last read token is of type EOF that means that the tag is not correctly close so the Exception is thrown */
							throw new SmartScriptParserException("Given entry is invalid. Tag is not defined.");
	
						if (t.getType() != TokenType.CLOSETAG || i < 3)											/* In case last read token is not close-tag-expression or the number of read arguments is lesser than 3 */
							throw new SmartScriptParserException("Invalid number of arguments in FOR tag.");	/* that means FOR tag doesn't have correct number of arguments(if the close-tag-expression is not read then we have too many */
																												/* arguments and if i < 3 that means we don't have enough arguments. */
						try {
							ForLoopNode tempForNode = new ForLoopNode(new ElementVariable((tempArray[0].getValue()).asText()),
									tempArray[1].getValue(), tempArray[2].getValue(),													/* If the number of read arguments is correct then the ForLoopNode will be allocated(unless there's an exception) */
									tempArray[3] != null ? tempArray[3].getValue() : null);												/* In tempArray are stored elements so we can easily get to them */
																																		/* However we cannot do that with the first argument because first argument is ElementVariable and the first element */
							Node topStack = (Node) stack.peek();																		/* in array isn't certainly an element variable so we use constructor which will throw IllegalArgumentException in case of an error */
							topStack.addChildNode(tempForNode);																			/* If we used cast, ClassCastException would be thrown(if tempArray[0].getValue() wasn't a variable) */
							stack.push(tempForNode);																					/* In the end newly allocated node is added as child node to the node on the top of the stack */
							t = lexer.nextToken();
						} catch (IllegalArgumentException ex) {
							throw new SmartScriptParserException("Invalid argument/s in FOR tag.");
						}
	
					} else if (tempString.equals("=")) {								/* In case of an =-tag(Echo tag) ArrayIndexedCollection is allocated that will keep elements of =-tag. */
						t = lexer.nextToken();
						List<Object> coll = new ArrayList<>();
	
						while (t.getType() != TokenType.CLOSETAG) {														/* This loop iterates "through" the tag definition and stores read tokens in the collection */
							if (t.getType() == TokenType.EOF)															/* In case EOF is read, that means that the tag definition doesn't have appropriate close-tag-definition-expression */
								throw new SmartScriptParserException("Given entry is invalid. Tag is not defined.");
	
							coll.add(t.getValue());
							t = lexer.nextToken();
						}
	
						Element[] elementArray = Arrays.copyOf(coll.toArray(), coll.size(), Element[].class);	/* Here are elements of the previous collection "transfered" to new temporary */
						EchoNode tempEchoNode = new EchoNode(elementArray);												/* array that will be sent as an argument to EchoTag constructor */
						Node topStack = (Node) stack.peek();
						topStack.addChildNode(tempEchoNode);
					} else if (tempString.equals("end")) {								/* This else is entered in case the end-tag is read */
						t = lexer.nextToken();
	
						if (t.getType() != TokenType.CLOSETAG)							/* If the token after the previous token that contained "END" isn't CLOSE-TAG expression then that means that there */
							throw new SmartScriptParserException("Invalid END tag.");	/* are some other arguments in the end-tag-definition which means that tag is invalid */
	
						stack.pop();		/* If we reached end-tag that that means that the previous non-empty tag is ended, and since that tag is non-empty that */
											/* means it is on the stack, so it is removed from stack(since it ended) */
						if (stack.size() < 1)
							throw new SmartScriptParserException(
									"Every END tag must have appropirate opening tag as a pair.");
					} else {
						throw new SmartScriptParserException("Tag " + tempString.toUpperCase() + " is not recognized."); /* In case tag is not recognised an exception is thrown(e.g. "{$ taga $}") */
					}
				}
			}
		} catch (SmartScriptLexerException ex) {								/* Whole previous block of commands is in try-catch because basically every nextToken command */
			throw new SmartScriptParserException(ex.getMessage());				/* could throw an exception. (e.g. in case of an unreadable token) */
		}

		if (stack.size() > 1)
			throw new SmartScriptParserException("Every non-empty tag must have an END tag.");

		return (DocumentNode) stack.pop();	/* Finally we remove the document node from the stack and return it as result */
	}

	/**
	 * Getter for document node of the document
	 * @return document node
	 */
	public DocumentNode getDocumentNode() {
		return document;
	}
}

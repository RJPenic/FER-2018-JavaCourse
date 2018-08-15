package hr.fer.zemris.java.hw03.prob2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexer;
import hr.fer.zemris.java.custom.scripting.lexer.Token;
import hr.fer.zemris.java.custom.scripting.lexer.TokenType;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexerException;

public class SmartScriptLexerTest {
	
	@Test
	public void testNotNull() {
		SmartScriptLexer lexer = new SmartScriptLexer("");
		
		assertNotNull("Token was expected but null was returned.", lexer.nextToken());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testNullInput() {
		new SmartScriptLexer(null);
	}
	
	@Test
	public void testEmpty() {
		SmartScriptLexer lexer = new SmartScriptLexer("");
		
		assertEquals("Empty input must generate only EOF token.", TokenType.EOF, lexer.nextToken().getType());
	}
	
	@Test
	public void testGetReturnsLastNext() {
		SmartScriptLexer lexer = new SmartScriptLexer("");
		
		Token token = lexer.nextToken();
		assertEquals("getToken returned different token than nextToken.", token, lexer.getToken());
		assertEquals("getToken returned different token than nextToken.", token, lexer.getToken());
	}
	
	@Test(expected=SmartScriptLexerException.class)
	public void testReadAfterEOF() {
		SmartScriptLexer lexer = new SmartScriptLexer("");

		lexer.nextToken();
		
		lexer.nextToken(); // throws
	}
	
	@Test
	public void testOnlyDocumentText() {
		SmartScriptLexer lexer = new SmartScriptLexer(" This is random text    on , ** | ");
		
		assertEquals(" This is random text    on , ** | ", lexer.nextToken().getValue().asText());
	}
	
	@Test
	public void testOnlyTag() {
		SmartScriptLexer lexer = new SmartScriptLexer("{$=i 35 \"str\"    $}");
		
		assertEquals(TokenType.OPENTAG, lexer.nextToken().getType());
		assertEquals("=", lexer.nextToken().getValue().asText());
		assertEquals("i", lexer.nextToken().getValue().asText());
		assertEquals("35", lexer.nextToken().getValue().asText());
		assertEquals("str", lexer.nextToken().getValue().asText());
		assertEquals(TokenType.CLOSETAG, lexer.nextToken().getType());
	}
	
	@Test
	public void testEscapingOnOpenTagExpression() {
		SmartScriptLexer lexer = new SmartScriptLexer("This is random "
														+ "\\{$   =   3  21  4$}");
		
		assertEquals("This is random {$   =   3  21  4$}", lexer.nextToken().getValue().asText());
	}
	
	@Test
	public void testWhenCloseTagExpressionInDocumentText() {
		SmartScriptLexer lexer = new SmartScriptLexer("  Randoasa ascxcvwe    $}  deasc");
		
		assertEquals("  Randoasa ascxcvwe    $}  deasc", lexer.nextToken().getValue().asText());
	}
	
	@Test(expected=SmartScriptLexerException.class)
	public void testWhenIllegalExpressionInTag() {
		SmartScriptLexer lexer = new SmartScriptLexer("{$   ###   $}");
		
		lexer.nextToken();
		
		lexer.nextToken(); //throws exception
	}
	
	@Test
	public void testEscapingOnBackslash() {
		SmartScriptLexer lexer = new SmartScriptLexer("  Randoasa ascxcvwe  \\\\  $}  deasc");
		
		assertEquals("  Randoasa ascxcvwe  \\  $}  deasc", lexer.nextToken().getValue().asText());
	}
	
	@Test(expected=SmartScriptLexerException.class)
	public void testEscapingOnWrongCharacterInDocumentText() {
		SmartScriptLexer lexer = new SmartScriptLexer("  Randoasa ascxcvwe  \\xasd  $}  deasc");

		lexer.nextToken(); //should throw an exception
	}
	
	@Test(expected=SmartScriptLexerException.class)
	public void testEscapingOnWrongCharacterInString() {
		SmartScriptLexer lexer = new SmartScriptLexer("{$ \"\\xasd\" Randoasa ascxcvwe  xasd  $}  deasc");

		lexer.nextToken();
		lexer.nextToken(); // should throw
	}
	
	@Test
	public void testEscapingInString() {
		SmartScriptLexer lexer = new SmartScriptLexer("{$ \" \\\" \\n \\t \\r \\\\ \" ");
		
		lexer.nextToken();
		
		assertEquals(" \" \n \t \r \\ ", lexer.nextToken().getValue().asText());
	}
}

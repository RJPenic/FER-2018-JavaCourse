package hr.fer.zemris.java.hw03.prob2;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.Test;

import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

public class SmartScriptParserTest {
	
	@Test
	public void testEmpty() {
		SmartScriptParser parser = new SmartScriptParser("");
		
		assertEquals(0, parser.getDocumentNode().numberOfChildren());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testNull() {
		SmartScriptParser parser = new SmartScriptParser(null); //throws
		parser.getDocumentNode();
	}
	
	@Test(expected=SmartScriptParserException.class)
	public void testWhenTagDefinitionNotClosed() {
		SmartScriptParser parser = new SmartScriptParser("{$ FOR i 1 3"); //throws
		parser.getDocumentNode();
	}
	
	@Test(expected=SmartScriptParserException.class)
	public void testWhenNonEmptyTagNotEnded() {
		SmartScriptParser parser = new SmartScriptParser(loader("test1.txt")); //throws
		parser.getDocumentNode();
	}
	
	@Test(expected=SmartScriptParserException.class)
	public void testWhenTooManyEnds() {
		SmartScriptParser parser = new SmartScriptParser(loader("test2.txt")); //throws
		parser.getDocumentNode();
	}
	
	@Test(expected=SmartScriptParserException.class)
	public void testWhenEndExpressionInvalid() {
		SmartScriptParser parser = new SmartScriptParser(loader("test3.txt")); //throws
		parser.getDocumentNode();
	}
	
	@Test(expected=SmartScriptParserException.class)
	public void testWhenUnidentifiedTag() {
		SmartScriptParser parser = new SmartScriptParser(loader("test4.txt")); //throws
		parser.getDocumentNode();
	}
	
	@Test
	public void testWhenEqualsInEchoTagConnectedWithVariable() {
		SmartScriptParser parser = new SmartScriptParser(loader("test5.txt"));
		
		assertEquals("ir", ((EchoNode)parser.getDocumentNode().getChild(1)).getElements()[0].asText());
	}
	
	@Test
	public void testWhen3ArgumentsInFor() {
		SmartScriptParser parser = new SmartScriptParser(loader("test8.txt"));
		
		assertEquals("i", ((ForLoopNode)(parser.getDocumentNode().getChild(1))).getVariable().asText());
		assertEquals("3.5", ((ForLoopNode)(parser.getDocumentNode().getChild(1))).getStartExpression().asText());
		assertEquals("21", ((ForLoopNode)(parser.getDocumentNode().getChild(1))).getEndExpression().asText());
	}
	
	@Test
	public void testWhen4ArgumentsInFor() {
		SmartScriptParser parser = new SmartScriptParser(loader("test11.txt"));
		
		assertEquals("i", ((ForLoopNode)(parser.getDocumentNode().getChild(2))).getVariable().asText());
		assertEquals("3.5", ((ForLoopNode)(parser.getDocumentNode().getChild(2))).getStartExpression().asText());
		assertEquals("21", ((ForLoopNode)(parser.getDocumentNode().getChild(2))).getEndExpression().asText());
		assertEquals("str", ((ForLoopNode)(parser.getDocumentNode().getChild(2))).getStepExpression().asText());
	}
	
	@Test(expected=SmartScriptParserException.class)
	public void testWhenTooManyArgInFor() {
		SmartScriptParser parser = new SmartScriptParser(loader("test12.txt"));
		parser.getDocumentNode();
	}
	
	@Test(expected=SmartScriptParserException.class)
	public void testWhenTooFewArgInFor() {
		SmartScriptParser parser = new SmartScriptParser(loader("test13.txt"));
		parser.getDocumentNode();
	}
	
	@Test
	public void testWhenEchoNodeEmpty() {
		SmartScriptParser parser = new SmartScriptParser(loader("test16.txt"));
		
		assertEquals(0, ((EchoNode)parser.getDocumentNode().getChild(1)).getElements().length);
	}
	
	@Test
	public void testWhenEchoNodeNotEmpty() {
		SmartScriptParser parser = new SmartScriptParser(loader("test18.txt"));

		assertEquals(9, ((EchoNode)parser.getDocumentNode().getChild(1)).getElements().length);
		assertEquals("3", ((EchoNode)parser.getDocumentNode().getChild(1)).getElements()[0].asText());
		assertEquals("*", ((EchoNode)parser.getDocumentNode().getChild(1)).getElements()[3].asText());
		assertEquals("@sin", ((EchoNode)parser.getDocumentNode().getChild(1)).getElements()[7].asText());
		assertEquals("rer", ((EchoNode)parser.getDocumentNode().getChild(1)).getElements()[8].asText());
	}
	
	@Test(expected=SmartScriptParserException.class)
	public void testWhenInvalidArgumentInFor() {
		SmartScriptParser parser = new SmartScriptParser(loader("test19.txt"));
		parser.getDocumentNode();
	}
	
	@Test
	public void testWhenStringConnectedWithAnotherArgument() {
		SmartScriptParser parser = new SmartScriptParser(loader("test21.txt"));
		
		assertEquals("irt", ((EchoNode)parser.getDocumentNode().getChild(1)).getElements()[0].asText());
		assertEquals("2", ((EchoNode)parser.getDocumentNode().getChild(1)).getElements()[1].asText());

		assertEquals("2", ((EchoNode)parser.getDocumentNode().getChild(3)).getElements()[0].asText());
		assertEquals("irt", ((EchoNode)parser.getDocumentNode().getChild(3)).getElements()[1].asText());
	}
	
	private String loader(String filename) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		
		try(InputStream is =
			this.getClass().getClassLoader().getResourceAsStream(filename)) {
			byte[] buffer = new byte[1024];
			
			while(true) {
				int read = is.read(buffer);
				if(read<1) break;
				bos.write(buffer, 0, read);
			}
			return new String(bos.toByteArray(), StandardCharsets.UTF_8);
		} catch(IOException ex) {
			return null;
		}
	}

}
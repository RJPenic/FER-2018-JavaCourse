package hr.fer.zemris.java.hw03.prob2;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

/**
 * Class that demonstrates how SmartScriptParser works
 * 
 * @author Rafael Josip Penić
 * @version 1.0
 *
 */
public class SmartScriptTester {

	/**
	 * Main method that reads text from the path give in the command line and
	 * demonstrates SmartScriptParser on it
	 * 
	 * @param args
	 *            command line arguments
	 * @throws IOException
	 *             in case of an error while reading string from the file
	 */
	public static void main(String[] args) throws IOException {

		String docBody = new String(Files.readAllBytes(Paths.get(args[0])), StandardCharsets.UTF_8);

		String originalDocumentBody = new String();
		String originalDocumentBody2 = new String();

		try {
			SmartScriptParser parser = new SmartScriptParser(docBody);
			DocumentNode document = parser.getDocumentNode();
			originalDocumentBody = createOriginalDocumentBody(document);

			SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
			DocumentNode document2 = parser2.getDocumentNode();
			originalDocumentBody2 = createOriginalDocumentBody(document2);
		} catch (SmartScriptParserException ex) {
			System.out.println("Error while parsing! Please check the validity of file!" + ex.getMessage());
			System.exit(-1);
		}

		System.out.println("Here are the results: ");
		System.out.println("---------------------------------------");
		System.out.println(originalDocumentBody);
		System.out.println("---------------------------------------");
		System.out.println(originalDocumentBody2);
	}

	/**
	 * Method that constructs the string from the document that when parsed will
	 * give the same document tree
	 * 
	 * @param document
	 *            document tree that will be "turned" into the string
	 * @return constructed string
	 */
	private static String createOriginalDocumentBody(Node document) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < document.numberOfChildren(); i++) {
			Node tempNode = document.getChild(i);

			if (tempNode instanceof ForLoopNode) {
				ForLoopNode tempForLoop = (ForLoopNode) tempNode;

				sb.append("{$ FOR ");

				sb.append(tempForLoop.getVariable().asText() + " ");

				if (tempForLoop.getStartExpression() instanceof ElementString) {
					sb.append(formStringFormat(tempForLoop.getStartExpression().asText()) + " ");
				} else {
					sb.append(tempForLoop.getStartExpression().asText() + " ");
				}

				if (tempForLoop.getEndExpression() instanceof ElementString) {
					sb.append(formStringFormat(tempForLoop.getEndExpression().asText()) + " ");
				} else {
					sb.append(tempForLoop.getEndExpression().asText() + " ");
				}

				if (tempForLoop.getStepExpression() != null) {
					if (tempForLoop.getStepExpression() instanceof ElementString) {
						sb.append(formStringFormat(tempForLoop.getStepExpression().asText()) + " ");
					} else {
						sb.append(tempForLoop.getStepExpression().asText() + " ");
					}
				}

				sb.append("$}");

				sb.append(createOriginalDocumentBody(tempForLoop));

				sb.append("{$END$}");
			} else if (tempNode instanceof EchoNode) {
				EchoNode tempEcho = (EchoNode) tempNode;
				sb.append("{$ = ");
				Element[] tempArray = tempEcho.getElements();

				for (int j = 0; j < tempArray.length; j++) {
					if (tempArray[j] instanceof ElementString) {
						sb.append(formStringFormat(tempArray[j].asText()) + " ");
					} else {
						sb.append(tempArray[j].asText() + " ");
					}
				}

				sb.append("$}");
			} else if (tempNode instanceof TextNode) {
				TextNode tempText = (TextNode) tempNode;
				String tempString = tempText.getText();
				tempString = tempString.replace("\\", "\\\\").replace("{$", "\\{$");
				sb.append(tempString);
			}
		}

		return sb.toString();
	}

	/**
	 * Method used to replace escaping sequences in the string.
	 * 
	 * @param s
	 *            string in which the escaping sequences will be replaced
	 * @return new string with correct escaping
	 */
	private static String replaceItAll(String s) {
		String tempString = s.replace("\\", "\\\\").replace("\"", "\\\"").replace("\r", "\\r").replace("\t", "\\t")
				.replace("\n", "\\n");

		return tempString;
	}

	/**
	 * Method that constructs string with " that will when parsed return
	 * ElementString
	 * 
	 * @param s
	 *            string that will be modified to correct string format
	 * @return newly constructed string
	 */
	private static String formStringFormat(String s) {
		return "\"" + replaceItAll(s) + "\"";
	}
}

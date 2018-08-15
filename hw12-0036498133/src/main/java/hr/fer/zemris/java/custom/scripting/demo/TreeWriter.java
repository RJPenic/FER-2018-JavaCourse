package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;

/**
 * Program which constructs and then prints document tree on the standard output
 * 
 * @author Rafael Josip Penić
 *
 */
public class TreeWriter {

	/**
	 * Main method
	 * 
	 * @param args
	 *            command line arguments
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Invalid number of arguments.");
			return;
		}

		Path tempPath;
		try {
			tempPath = Paths.get(args[0]);
		} catch (InvalidPathException ex) {
			System.out.println("Given string is not a valid path.");
			return;
		}

		String docBody;
		try {
			docBody = new String(Files.readAllBytes(tempPath), StandardCharsets.UTF_8);
		} catch (IOException ex) {
			System.out.println("Error while reading from " + tempPath.toAbsolutePath().normalize());
			return;
		}

		SmartScriptParser p = new SmartScriptParser(docBody);
		WriterVisitor visitor = new WriterVisitor();
		p.getDocumentNode().accept(visitor);
	}

	/**
	 * Implementation of INodeVisitor interface that prints content of the document
	 * tree
	 * 
	 * @author Rafael Josip Penić
	 *
	 */
	private static class WriterVisitor implements INodeVisitor {

		@Override
		public void visitTextNode(TextNode node) {
			String tempString = node.getText();
			tempString = tempString.replace("\\", "\\\\").replace("{$", "\\{$");
			System.out.print(tempString);
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			System.out.print("{$ FOR ");

			System.out.print(node.getVariable().asText() + " ");

			if (node.getStartExpression() instanceof ElementString) {
				System.out.print(formStringFormat(node.getStartExpression().asText()) + " ");
			} else {
				System.out.print(node.getStartExpression().asText() + " ");
			}

			if (node.getEndExpression() instanceof ElementString) {
				System.out.print(formStringFormat(node.getEndExpression().asText()) + " ");
			} else {
				System.out.print(node.getEndExpression().asText() + " ");
			}

			if (node.getStepExpression() != null) {
				if (node.getStepExpression() instanceof ElementString) {
					System.out.print(formStringFormat(node.getStepExpression().asText()) + " ");
				} else {
					System.out.print(node.getStepExpression().asText() + " ");
				}
			}

			System.out.print("$}");

			for (int i = 0; i < node.numberOfChildren(); i++) {
				node.getChild(i).accept(this);
			}

			System.out.print("{$END$}");
		}

		@Override
		public void visitEchoNode(EchoNode node) {
			System.out.print("{$ = ");
			Element[] tempArray = node.getElements();

			for (int j = 0; j < tempArray.length; j++) {
				if (tempArray[j] instanceof ElementString) {
					System.out.print(formStringFormat(tempArray[j].asText()) + " ");
				} else {
					System.out.print(tempArray[j].asText() + " ");
				}
			}

			System.out.print("$}");
		}

		@Override
		public void visitDocumentNode(DocumentNode node) {
			for (int i = 0; i < node.numberOfChildren(); i++) {
				node.getChild(i).accept(this);
			}
		}

		/**
		 * Method that adds quotation marks on the beginning and the end of the string
		 * 
		 * @param s
		 *            string to be formatted
		 * @return constructed string
		 */
		private static String formStringFormat(String s) {
			return "\"" + replaceItAll(s) + "\"";
		}

		/**
		 * Method that takes care of escaping characters in the string
		 * 
		 * @param s
		 *            string to be formatted
		 * @return result of the replacements with escape equivalents
		 */
		private static String replaceItAll(String s) {
			String tempString = s.replace("\\", "\\\\").replace("\"", "\\\"").replace("\r", "\\r").replace("\t", "\\t")
					.replace("\n", "\\n");

			return tempString;
		}
	}
}

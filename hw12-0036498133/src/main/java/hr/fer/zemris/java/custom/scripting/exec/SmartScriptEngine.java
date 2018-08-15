package hr.fer.zemris.java.custom.scripting.exec;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Objects;
import java.util.Stack;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Engine that can execute smart scripts(files with extension "smscr")
 * 
 * @author Rafael Josip Penić
 *
 */
public class SmartScriptEngine {

	/**
	 * Root document node of the engine
	 */
	private DocumentNode documentNode;

	/**
	 * request context used for storing parameters, writing and etc.
	 */
	private RequestContext requestContext;

	/**
	 * Engines multistack used as constants storage
	 */
	private ObjectMultistack multistack = new ObjectMultistack();

	/**
	 * Visitor used when "iterating" through nodes of the engine
	 */
	private INodeVisitor visitor = new INodeVisitor() {

		/**
		 * Defines single entry and stores variable itself and its value
		 * 
		 * @author Rafael Josip Penić
		 *
		 */
		class VariableStackEntry {

			/**
			 * Variable
			 */
			private ElementVariable var;

			/**
			 * Value of the variable
			 */
			private Object value;

			/**
			 * Constructor for a variable stack entry
			 * 
			 * @param var
			 *            variable
			 * @param value
			 *            value of the variable
			 */
			public VariableStackEntry(ElementVariable var, Object value) {
				Objects.requireNonNull(var, "Given variable reference is null.");
				Objects.requireNonNull(value, "Given value reference is null.");

				this.var = var;
				this.value = value;
			}

		}

		@Override
		public void visitTextNode(TextNode node) {
			try {
				requestContext.write(node.getText());
			} catch (IOException e) {
				throw new RuntimeException("Error while writing the content of the text node.");
			}
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			ElementVariable var = node.getVariable();
			Element start = node.getStartExpression();

			if (start instanceof ElementConstantDouble || start instanceof ElementConstantInteger
					|| start instanceof ElementString) {
				multistack.push("objectStack", new ValueWrapper(new VariableStackEntry(var, start.asText())));

			} else {
				throw new RuntimeException("Given start expression is not valid.");
			}

			Element end = node.getEndExpression();
			Object endValue;
			if (end instanceof ElementConstantDouble || end instanceof ElementConstantInteger
					|| end instanceof ElementString) {
				endValue = end.asText();

			} else {
				throw new RuntimeException("Given end expression is not valid.");
			}

			Element step = node.getStepExpression();
			Object stepValue;
			if (step instanceof ElementConstantDouble || step instanceof ElementConstantInteger
					|| step instanceof ElementString) {
				stepValue = step.asText();

			} else {
				throw new RuntimeException("Given end expression is not valid.");
			}

			while (true) {

				VariableStackEntry stackTopPeek = (VariableStackEntry) (multistack.peek("objectStack").getValue());
				if (new ValueWrapper(stackTopPeek.value).numCompare(endValue) > 0)
					break;

				for (int i = 0; i < node.numberOfChildren(); i++) {
					node.getChild(i).accept(this);
				}

				VariableStackEntry stackTop = (VariableStackEntry) (multistack.pop("objectStack").getValue());
				if (new ValueWrapper(stackTop.value).numCompare(endValue) > 0)
					break;

				ValueWrapper tempValWrapper = new ValueWrapper(stackTop.value);
				tempValWrapper.add(stepValue);
				multistack.push("objectStack",
						new ValueWrapper(new VariableStackEntry(stackTop.var, tempValWrapper.getValue())));
			}
		}

		@Override
		public void visitEchoNode(EchoNode node) {
			Stack<Object> tempStack = new Stack<>();

			Element[] elemArray = node.getElements();

			for (Element element : elemArray) {
				if (element instanceof ElementConstantDouble || element instanceof ElementConstantInteger
						|| element instanceof ElementString) {
					tempStack.push((element.asText()));

				} else if (element instanceof ElementVariable) {
					ElementVariable var = (ElementVariable) element;

					Stack<VariableStackEntry> searchStack = new Stack<>();

					while (true) {
						if (multistack.isEmpty("objectStack"))
							break;

						VariableStackEntry tempEntry = (VariableStackEntry) (multistack.pop("objectStack").getValue());
						searchStack.push(tempEntry);

						if (tempEntry.var.asText().equals(var.asText())) {
							tempStack.push(tempEntry.value);
							break;
						}
					}

					while (!searchStack.isEmpty()) {
						multistack.push("objectStack", new ValueWrapper(searchStack.pop()));
					}
				} else if (element instanceof ElementOperator) {
					ElementOperator op = (ElementOperator) element;

					ValueWrapper temp = new ValueWrapper(tempStack.pop());
					switch (op.asText()) {
					case ("+"):
						temp.add(tempStack.pop());
						tempStack.push(temp.getValue());
						break;

					case ("-"):
						temp.subtract(tempStack.pop());
						tempStack.push(temp.getValue());
						break;

					case ("*"):
						temp.multiply(tempStack.pop());
						tempStack.push(temp.getValue());
						break;

					case ("/"):
						temp.divide(tempStack.pop());
						tempStack.push(temp.getValue());
						break;

					default:
						throw new RuntimeException("Invalid operator.");
					}
				} else if (element instanceof ElementFunction) {
					identifyAndApply(element.asText(), tempStack);
				}
			}

			Stack<Object> tempStackReverse = new Stack<>();

			while (!tempStack.isEmpty()) {
				tempStackReverse.push(tempStack.pop());
			}

			while (!tempStackReverse.isEmpty()) {
				try {
					requestContext.write(tempStackReverse.pop().toString());
				} catch (IOException e) {
					throw new RuntimeException("Error while writing leftovers of stack.");
				}
			}
		}

		/**
		 * Method that identifies function from the given string and produces identified
		 * function using the given stack
		 * 
		 * @param functionName
		 *            name of the function
		 * @param stack
		 *            stack that is used to produce the result of the function
		 */
		private void identifyAndApply(String functionName, Stack<Object> stack) {
			switch (functionName) {
			case ("@sin"):
				Double x = Double.parseDouble(stack.pop().toString());
				double r = Math.sin(Math.toRadians(x));
				stack.push(r);
				break;

			case ("@decfmt"):
				DecimalFormat f = new DecimalFormat(stack.pop().toString());
				Double x1 = Double.parseDouble(stack.pop().toString());
				String res = f.format(x1);
				stack.push(res);
				break;

			case ("@dup"):
				Object a = stack.pop();
				stack.push(a);
				stack.push(a);
				break;

			case ("@swap"):
				Object a1 = stack.pop();
				Object a2 = stack.pop();

				stack.push(a1);
				stack.push(a2);
				break;

			case ("@setMimeType"):
				requestContext.setMimeType(stack.pop().toString());
				break;

			case ("@paramGet"):
				Object defValue = stack.pop();
				String name = stack.pop().toString();

				Object value = requestContext.getParameter(name);

				stack.push(value == null ? defValue : value);
				break;

			case ("@pparamGet"):
				Object defValue1 = stack.pop();
				String name1 = stack.pop().toString();

				Object value1 = requestContext.getPersistentParameter(name1);

				stack.push(value1 == null ? defValue1 : value1);
				break;

			case ("@pparamSet"):
				String name2 = stack.pop().toString();
				String value2 = stack.pop().toString();

				requestContext.setPersistentParameter(name2, value2);
				break;

			case ("@pparamdel"):
				String name3 = stack.pop().toString();

				requestContext.removePersistentParameter(name3);
				break;

			case ("@tparamGet"):
				Object defValue4 = stack.pop();
				String name4 = stack.pop().toString();

				Object value4 = requestContext.getTemporaryParameter(name4);

				stack.push(value4 == null ? defValue4 : value4);
				break;

			case ("@tparamSet"):
				String name5 = stack.pop().toString();
				String value5 = stack.pop().toString();

				requestContext.setTemporaryParameter(name5, value5);
				break;

			case ("@tparamDel"):
				String name6 = stack.pop().toString();

				requestContext.removeTemporaryParameter(name6);
				break;

			default:
				throw new RuntimeException(functionName + " is not a valid function.");
			}
		}

		@Override
		public void visitDocumentNode(DocumentNode node) {
			for (int i = 0; i < node.numberOfChildren(); i++) {
				node.getChild(i).accept(this);
			}
		}
	};

	/**
	 * Constructor for smart script engines
	 * 
	 * @param documentNode
	 *            root document node of the engine
	 * @param requestContext
	 *            engines request context
	 */
	public SmartScriptEngine(DocumentNode documentNode, RequestContext requestContext) {
		Objects.requireNonNull(documentNode, "Given document reference is null.");
		Objects.requireNonNull(requestContext, "Given request context reference is null.");

		this.requestContext = requestContext;
		this.documentNode = documentNode;
	}

	/**
	 * Starts visitation of nodes by visiting root node
	 */
	public void execute() {
		documentNode.accept(visitor);
	}
}

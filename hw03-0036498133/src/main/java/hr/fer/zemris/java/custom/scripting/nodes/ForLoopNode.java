package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;

/**
 * A node representing a single for-loop construct
 * 
 * @author Rafael Josip Penić
 * @version 1.0
 *
 */
public class ForLoopNode extends Node {

	/**
	 * Variable that will iterate in the for-loop construct
	 */
	private ElementVariable variable;

	/**
	 * Starting expression on which the iterating variable will be initialized
	 */
	private Element startExpression;

	/**
	 * Once the iterating variable contains equal value as the this expression, for
	 * loop ends
	 */
	private Element endExpression;

	/**
	 * Expression that represents how the variable will be incremented while
	 * iterating
	 */
	private Element stepExpression;

	/**
	 * Constructor for ForLoop node.
	 * 
	 * @param variable
	 *            value of variable to be allocated
	 * @param startExpression
	 *            value of start expression to be allocated
	 * @param endExpression
	 *            value of end expression to be allocated
	 * @param stepExpression
	 *            value of step expression to be allocated
	 * 
	 * @throws IllegalArgumentException
	 *             in case one or more arguments are invalid
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression,
			Element stepExpression) {
		if (startExpression instanceof ElementFunction || startExpression instanceof ElementOperator
				|| startExpression == null || endExpression instanceof ElementFunction
				|| endExpression instanceof ElementOperator || endExpression == null
				|| stepExpression instanceof ElementFunction || stepExpression instanceof ElementOperator
				|| variable == null)
			throw new IllegalArgumentException("Given arguments are not appropriate for this constructor.");

		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}

	/**
	 * Getter for the iterating variable
	 * 
	 * @return iterating variable
	 */
	public ElementVariable getVariable() {
		return variable;
	}

	/**
	 * Getter for start expression
	 * 
	 * @return start expression
	 */
	public Element getStartExpression() {
		return startExpression;
	}

	/**
	 * Getter for end expression
	 * 
	 * @return end expression
	 */
	public Element getEndExpression() {
		return endExpression;
	}

	/**
	 * Getter for step expression
	 * 
	 * @return step expression
	 */
	public Element getStepExpression() {
		return stepExpression;
	}

}

package hr.fer.zemris.java.hw05.db.ConditionalExpression;

import hr.fer.zemris.java.hw05.db.IComparisonOperator.IComparisonOperator;
import hr.fer.zemris.java.hw05.db.IFieldValueGetter.IFieldValueGetter;

/**
 * Class that represents conditional expression(e.g. firstName = "Ivan")
 * 
 * @author Rafael Josip Penić
 *
 */
public class ConditionalExpression {

	/**
	 * Field value getter of the expression
	 */
	private IFieldValueGetter fieldGetter;

	/**
	 * String literal with which the field value will be compared
	 */
	private String stringLiteral;

	/**
	 * Comparison operator that is used for comparing literal and the field value
	 */
	private IComparisonOperator comparisonOperator;

	/**
	 * Constructor for ConditionalExpression objects
	 * 
	 * @param fieldGetter
	 *            field value getter of conditional expression that will be
	 *            constructed
	 * @param stringLiteral
	 *            literal of conditional expression that will be constructed
	 * @param comparisonOperator
	 *            operator of conditional expression that will be constructed
	 */
	public ConditionalExpression(IFieldValueGetter fieldGetter, String stringLiteral,
			IComparisonOperator comparisonOperator) {
		super();
		this.fieldGetter = fieldGetter;
		this.stringLiteral = stringLiteral;
		this.comparisonOperator = comparisonOperator;
	}

	/**
	 * Getter for fieldGetter variable
	 * 
	 * @return expressions field value getter
	 */
	public IFieldValueGetter getFieldGetter() {
		return fieldGetter;
	}

	/**
	 * Getter for expression literal
	 * 
	 * @return expressions literal
	 */
	public String getStringLiteral() {
		return stringLiteral;
	}

	/**
	 * Getter for expression operator
	 * 
	 * @return expressions comparison operator
	 */
	public IComparisonOperator getComparisonOperator() {
		return comparisonOperator;
	}

}

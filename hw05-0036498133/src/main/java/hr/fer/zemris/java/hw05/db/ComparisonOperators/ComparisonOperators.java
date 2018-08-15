package hr.fer.zemris.java.hw05.db.ComparisonOperators;

import hr.fer.zemris.java.hw05.db.IComparisonOperator.IComparisonOperator;

/**
 * Class that defines comparison operators with its static variables
 * 
 * @author Rafael Josip Penić
 *
 */
public class ComparisonOperators {

	/**
	 * IComparisonOperator variable which represents operator for less('<')
	 */
	public static final IComparisonOperator LESS;

	/**
	 * IComparisonOperator variable which represents operator for less or
	 * equal('<=')
	 */
	public static final IComparisonOperator LESS_OR_EQUALS;

	/**
	 * IComparisonOperator variable which represents operator for greater('>')
	 */
	public static final IComparisonOperator GREATER;

	/**
	 * IComparisonOperator variable which represents operator for greater or
	 * equal('>=')
	 */
	public static final IComparisonOperator GREATER_OR_EQUALS;

	/**
	 * IComparisonOperator variable which represents operator for equals('=')
	 */
	public static final IComparisonOperator EQUALS;

	/**
	 * IComparisonOperator variable which represents operator for not equals('!=')
	 */
	public static final IComparisonOperator NOT_EQUALS;

	/**
	 * IComparisonOperator variable which represents operator which checks if the
	 * certain value has certain "format"
	 */
	public static final IComparisonOperator LIKE;

	static {
		LESS = (value1, value2) -> value1.compareTo(value2) < 0;
		LESS_OR_EQUALS = (value1, value2) -> value1.compareTo(value2) <= 0;
		GREATER = (value1, value2) -> value1.compareTo(value2) > 0;
		GREATER_OR_EQUALS = (value1, value2) -> value1.compareTo(value2) >= 0;
		EQUALS = (value1, value2) -> value1.compareTo(value2) == 0;
		NOT_EQUALS = (value1, value2) -> value1.compareTo(value2) != 0;
		LIKE = new IComparisonOperator() {

			@Override
			public boolean satisfied(String value1, String value2) {
				int index = value2.indexOf("*");

				if (index != value2.lastIndexOf("*"))
					throw new IllegalArgumentException("Only one wildcard symbol per string pattern is allowed.");

				if (index == -1) {
					return value1.equals(value2);
				} else if (index != value2.length() - 1) {
					return value1.startsWith(value2.substring(0, index))
							&& value1.endsWith(value2.substring(index + 1));
				} else {
					return value1.startsWith(value2.substring(0, index));
				}
			}
		};
	}

}

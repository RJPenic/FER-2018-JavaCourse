package hr.fer.zemris.java.hw05.db.IComparisonOperator;

/**
 * Interface that contains which contains only one method used by operators when
 * comparing two values
 * 
 * @author Rafael Josip Penić
 *
 */
public interface IComparisonOperator {

	/**
	 * Method that compares two values
	 * 
	 * @param value1
	 *            string of the first value
	 * @param value2
	 *            string of the second value
	 * @return true if the operator condition is fulfilled, false otherwise
	 */
	public boolean satisfied(String value1, String value2);
}

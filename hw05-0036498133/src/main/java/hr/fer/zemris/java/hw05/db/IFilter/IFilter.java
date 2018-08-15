package hr.fer.zemris.java.hw05.db.IFilter;

import hr.fer.zemris.java.hw05.db.StudentRecord.StudentRecord;

/**
 * Functional interface that represents data filter
 * 
 * @author Rafael Josip Penić
 *
 */
public interface IFilter {

	/**
	 * Method that says if the given record fulfills certain condition or not
	 * 
	 * @param record
	 *            student record that is tested
	 * @return true if the condition is fulfilled, false otherwise
	 */
	public boolean accepts(StudentRecord record);
}

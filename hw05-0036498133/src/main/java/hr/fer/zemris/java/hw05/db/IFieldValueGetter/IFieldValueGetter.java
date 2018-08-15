package hr.fer.zemris.java.hw05.db.IFieldValueGetter;

import hr.fer.zemris.java.hw05.db.StudentRecord.StudentRecord;

/**
 * Interface that contains only one method that is used for getting information
 * from the student record
 * 
 * @author Rafael Josip Penić
 *
 */
public interface IFieldValueGetter {

	/**
	 * Method that returns certain value from the record depending on the
	 * FieldValueGetter(e.g. FIRST_NAME would return first name)
	 * 
	 * @param record
	 *            student record from which the information will be taken
	 * @return information from the record
	 */
	public String get(StudentRecord record);
}

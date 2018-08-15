package hr.fer.zemris.java.hw05.db.FieldValueGetters;

import hr.fer.zemris.java.hw05.db.IFieldValueGetter.IFieldValueGetter;

/**
 * Class that defines getters for field values
 * 
 * @author Rafael Josip Penić
 *
 */
public class FieldValueGetters {

	/**
	 * IFieldValueGetter variable which represents first names
	 */
	public static final IFieldValueGetter FIRST_NAME;

	/**
	 * IFieldValueGetter variable which represents last names
	 */
	public static final IFieldValueGetter LAST_NAME;

	/**
	 * IFieldValueGetter variable which represents jmbags
	 */
	public static final IFieldValueGetter JMBAG;

	static {
		FIRST_NAME = (record) -> record.getFirstName();
		LAST_NAME = (record) -> record.getLastName();
		JMBAG = (record) -> record.getJmbag();
	}
}

package hr.fer.zemris.java.hw05.db.StudentDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import hr.fer.zemris.java.hw05.collections.SimpleHashtable;
import hr.fer.zemris.java.hw05.db.IFilter.IFilter;
import hr.fer.zemris.java.hw05.db.StudentRecord.StudentRecord;

/**
 * Class that represents student database which stores student records and
 * provides useful methods for manipulating with the database
 * 
 * @author Rafael Josip Penić
 *
 */
public class StudentDatabase {

	/**
	 * List where the student records are stored
	 */
	private List<StudentRecord> studentList;

	/**
	 * Hash table where student records are stored for quicker getting
	 */
	private SimpleHashtable<String, StudentRecord> index;

	/**
	 * Constructor for student database
	 * 
	 * @param lineList
	 *            string list from which the database will be constructed
	 * @throws IllegalArgumentException
	 *             if the given list is not valid
	 * @throws NullPointerException
	 *             if the given list is null
	 */
	public StudentDatabase(List<String> lineList) {
		Objects.requireNonNull(lineList, "Given string list must not be null.");

		studentList = new ArrayList<>();
		for (String temp : lineList) {
			String[] tempStringArray = temp.split("\t+");
			if (tempStringArray.length != 4)
				throw new IllegalArgumentException("\"" + temp + "\" has invalid number of atributes.");
			try {
				studentList.add(new StudentRecord(tempStringArray[0].trim(), tempStringArray[1].trim(),
						tempStringArray[2].trim(), Integer.parseInt(tempStringArray[3].trim())));
			} catch (NumberFormatException ex) {
				throw new IllegalArgumentException("\"" + tempStringArray[3] + "\" is not a valid grade.");
			}
		}

		index = new SimpleHashtable<>();

		for (StudentRecord tempRecord : studentList) {
			index.put(tempRecord.getJmbag(), tempRecord);
		}
	}

	/**
	 * Method that gets record with the given jmbag from the hash table
	 * 
	 * @param jmbag
	 *            jmbag of requested student entry
	 * @return Student record with the given jmbag
	 */
	public StudentRecord forJMBAG(String jmbag) {
		return index.get(jmbag);
	}

	/**
	 * Method that filters students from the database and stores them in a new list
	 * that will be returned as result
	 * 
	 * @param filter
	 *            IFilter that will be used as filtering condition
	 * @return list consisting of filtered student records
	 */
	public List<StudentRecord> filter(IFilter filter) {
		List<StudentRecord> resultList = new ArrayList<>();

		for (StudentRecord record : studentList) {
			if (filter.accepts(record)) {
				resultList.add(record);
			}
		}

		return resultList;
	}
}

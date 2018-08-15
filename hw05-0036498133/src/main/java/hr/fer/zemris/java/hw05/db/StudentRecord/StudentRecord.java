package hr.fer.zemris.java.hw05.db.StudentRecord;

/**
 * Class representing student record with basic informations about the student
 * 
 * @author Rafael Josip Penić
 *
 */
public class StudentRecord {
	
	/**
	 * Unique code for each student
	 */
	private String jmbag;
	
	/**
	 * Students last name
	 */
	private String lastName;
	
	/**
	 * Students first name
	 */
	private String firstName;
	
	/**
	 * Students grade
	 */
	private int grade;

	/**
	 * Constructor for student records
	 * @param jmbag jmbag of the record that will be constructed
	 * @param lastName last name of the record that will be constructed
	 * @param firstName first name of the record that will be constructed
	 * @param grade grade of the record that will be constructed
	 */
	public StudentRecord(String jmbag, String lastName, String firstName, int grade) {
		super();
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.grade = grade;
	}

	/**
	 * Getter for jmbag
	 * @return value of jmbag variable
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * Getter for last name
	 * @return records last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Getter for first name
	 * @return records first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Getter for grade
	 * @return records grade
	 */
	public int getGrade() {
		return grade;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jmbag == null) ? 0 : jmbag.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudentRecord other = (StudentRecord) obj;
		if (jmbag == null) {
			if (other.jmbag != null)
				return false;
		} else if (!jmbag.equals(other.jmbag))
			return false;
		return true;
	}
}

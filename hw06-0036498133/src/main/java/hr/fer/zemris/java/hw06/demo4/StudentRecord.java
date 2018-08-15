package hr.fer.zemris.java.hw06.demo4;

/**
 * Class representing a single student record
 * 
 * @author Rafael Josip Penić
 *
 */
public class StudentRecord {

	/**
	 * students unique personal identification number
	 */
	private String jmbag;

	/**
	 * students last name
	 */
	private String lastName;

	/**
	 * students first name
	 */
	private String firstName;

	/**
	 * students score at the first exam
	 */
	private double scoreMI;

	/**
	 * students score at the last exam
	 */
	private double scoreZI;

	/**
	 * students score at the laboratories
	 */
	private double scoreLAB;

	/**
	 * students grade
	 */
	private int grade;

	/**
	 * Constructor for student record
	 * 
	 * @param jmbag
	 *            students identification number
	 * @param lastName
	 *            students last name
	 * @param firstName
	 *            students first name
	 * @param scoreMI
	 *            students score on the first exam
	 * @param scoreZI
	 *            students score on the second exam
	 * @param scoreLAB
	 *            students score on the laboratories
	 * @param grade
	 *            students grade
	 */
	public StudentRecord(String jmbag, String lastName, String firstName, double scoreMI, double scoreZI,
			double scoreLAB, int grade) {
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.scoreMI = scoreMI;
		this.scoreZI = scoreZI;
		this.scoreLAB = scoreLAB;
		this.grade = grade;
	}

	/**
	 * Getter for students jmbag
	 * 
	 * @return students jmbag
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * Getter for students last name
	 * 
	 * @return students last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Getter for students first name
	 * 
	 * @return students first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Getter for students score on first exam
	 * 
	 * @return students score on first exam
	 */
	public double getScoreMI() {
		return scoreMI;
	}

	/**
	 * Getter for students score on last exam
	 * 
	 * @return students score on last exam
	 */
	public double getScoreZI() {
		return scoreZI;
	}

	/**
	 * Getter for students score on laboratories
	 * 
	 * @return students score on laboratories
	 */
	public double getScoreLAB() {
		return scoreLAB;
	}

	/**
	 * Getter for students grade
	 * 
	 * @return students grade
	 */
	public int getGrade() {
		return grade;
	}

	@Override
	public String toString() {
		return jmbag + ", " + firstName + ", " + lastName + ", " + scoreMI + ", " + scoreZI + ", " + scoreLAB;
	}
}

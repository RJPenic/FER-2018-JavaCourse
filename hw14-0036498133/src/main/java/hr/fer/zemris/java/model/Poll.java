package hr.fer.zemris.java.model;

/**
 * Class representing a simple poll model on which the user can vote
 * 
 * @author Rafael Josip Penić
 *
 */
public class Poll {

	/**
	 * polls ID
	 */
	private long id;

	/**
	 * poll title
	 */
	private String title;

	/**
	 * poll message
	 */
	private String message;

	/**
	 * Getter for poll ID
	 * 
	 * @return poll ID
	 */
	public long getId() {
		return id;
	}

	/**
	 * Setter for poll ID
	 * 
	 * @param id
	 *            poll ID
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Getter for poll title
	 * 
	 * @return poll title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Setter for poll title
	 * 
	 * @param title
	 *            poll title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Getter for poll message
	 * 
	 * @return poll message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Setter for poll message
	 * 
	 * @param message
	 *            poll message
	 */
	public void setMessage(String message) {
		this.message = message;
	}
}

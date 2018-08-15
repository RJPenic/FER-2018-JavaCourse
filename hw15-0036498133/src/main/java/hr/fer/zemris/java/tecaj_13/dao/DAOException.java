package hr.fer.zemris.java.tecaj_13.dao;

/**
 * Exception thrown in case of an error when reaching data in database
 * 
 * @author Rafael Josip Penić
 *
 */
public class DAOException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor with message and a cause
	 */
	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor with a message
	 * @param message message explaining why exception was thrown
	 */
	public DAOException(String message) {
		super(message);
	}
}
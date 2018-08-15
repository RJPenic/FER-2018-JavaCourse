package hr.fer.zemris.java.custom.collections;

/**
 * This class defines exception which will be thrown when trying to remove
 * object from empty stack.
 * 
 * @author Rafael Josip Penić
 * @version 1.0
 * 
 */
public class EmptyStackException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a EmptyStackException with no detail message.
	 */
	public EmptyStackException() {
		super();
	}

	/**
	 * Constructs a EmptyStackException with the specified detail message
	 * 
	 * @param message
	 *            string that contains details about the exception
	 */
	public EmptyStackException(String message) {
		super(message);
	}
}

package hr.fer.zemris.java.hw06.shell;

/**
 * Exception thrown in case of an error when writing or reading from the shell
 * 
 * @author Rafael Josip Penić
 *
 */
public class ShellIOException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Basic constructor for ShellIOException with no message.
	 */
	public ShellIOException() {
		super();
	}

	/**
	 * Constructor for ShellIOException with message
	 * 
	 * @param message
	 *            message that more closely describes the cause of the exception
	 */
	public ShellIOException(String message) {
		super(message);
	}
}

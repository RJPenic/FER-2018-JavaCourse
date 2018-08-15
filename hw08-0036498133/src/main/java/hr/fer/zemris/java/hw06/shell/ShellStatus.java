package hr.fer.zemris.java.hw06.shell;

/**
 * Enum representing the shell state. If the shell should be terminated
 * execution methods return TERMINATE and CONTNUE otherwise
 * 
 * @author Rafael Josip Penić
 *
 */
public enum ShellStatus {
	/**
	 * Status of the shell when it should not be terminated
	 */
	CONTINUE,
	/**
	 * Status of the shell when the shell should be terminated
	 */
	TERMINATE
}

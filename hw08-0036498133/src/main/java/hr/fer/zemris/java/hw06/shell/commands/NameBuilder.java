package hr.fer.zemris.java.hw06.shell.commands;

/**
 * Name builders generate pieces of the name by appending them to the string
 * builder of the NameBuilderInfo object.
 * 
 * @author Rafael Josip Penić
 *
 */
public interface NameBuilder {
	/**
	 * Method that executes action of appending a generated piece of name to a
	 * string builder
	 * 
	 * @param info
	 *            NameBuilderInfo object where the string builder is stored
	 */
	void execute(NameBuilderInfo info);
}

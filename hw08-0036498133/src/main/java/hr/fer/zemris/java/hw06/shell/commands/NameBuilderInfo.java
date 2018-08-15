package hr.fer.zemris.java.hw06.shell.commands;

/**
 * NameBuilderInfo interface provides needed resources when constructing a name.
 * 
 * @author Rafael Josip Penić
 *
 */
public interface NameBuilderInfo {

	/**
	 * Getter for a string builder
	 * 
	 * @return string builder
	 */
	StringBuilder getStringBuilder();

	/**
	 * Getter for a regex group with the given index
	 * 
	 * @param index
	 *            index of the searched group
	 * @return group with the given index
	 */
	String getGroup(int index);
}

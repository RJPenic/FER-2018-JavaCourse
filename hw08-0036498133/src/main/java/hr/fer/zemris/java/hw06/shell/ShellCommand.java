package hr.fer.zemris.java.hw06.shell;

import java.util.List;

/**
 * Interface implemented by classes which represent shell commands and provide
 * methods for execution of said commands
 * 
 * @author Rafael Josip Penić
 *
 */
public interface ShellCommand {
	/**
	 * Method that executes the command
	 * 
	 * @param env
	 *            current environment
	 * @param arguments
	 *            arguments of the command
	 * @return status of shell according on a result of the method
	 */
	ShellStatus executeCommand(Environment env, String arguments);

	/**
	 * Getter for the command name
	 * 
	 * @return command name
	 */
	String getCommandName();

	/**
	 * Method that constructs list with basic informations about the command
	 * 
	 * @return list containing strings describing the method
	 */
	List<String> getCommandDescription();
}

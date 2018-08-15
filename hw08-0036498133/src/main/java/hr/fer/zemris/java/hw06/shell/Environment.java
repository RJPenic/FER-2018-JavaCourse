package hr.fer.zemris.java.hw06.shell;

import java.nio.file.Path;
import java.util.SortedMap;

/**
 * Interface that defines basic communication between shell and the user
 * 
 * @author Rafael Josip Penić
 *
 */
public interface Environment {

	/**
	 * Allows user to enter wanted values when necessary
	 * 
	 * @return String string that user entered
	 * @throws ShellIOException
	 *             in case of an error when communicating with user
	 */
	String readLine() throws ShellIOException;

	/**
	 * Writes message
	 * 
	 * @param text
	 *            message to be written
	 * @throws ShellIOException
	 *             in case of an error when writing
	 */
	void write(String text) throws ShellIOException;

	/**
	 * Writes message on a certain output and adds '\n' symbol on the end of the
	 * given message
	 * 
	 * @param text
	 *            message to be written
	 * @throws ShellIOException
	 *             in case of an error when writing
	 */
	void writeln(String text) throws ShellIOException;

	/**
	 * Method that returns unmodifiable map that contains valid commands that user
	 * can use
	 * 
	 * @return map with commands
	 */
	SortedMap<String, ShellCommand> commands();

	/**
	 * Getter for multiline symbol
	 * 
	 * @return multiline symbol
	 */
	Character getMultilineSymbol();

	/**
	 * Setter for multiline symbol
	 * 
	 * @param symbol
	 *            new multiline symbol
	 */
	void setMultilineSymbol(Character symbol);

	/**
	 * Getter for prompt symbol
	 * 
	 * @return current prompt symbol
	 */
	Character getPromptSymbol();

	/**
	 * Setter for prompt symbol
	 * 
	 * @param symbol
	 *            new prompt symbol
	 */
	void setPromptSymbol(Character symbol);

	/**
	 * Getter for morelines symbol
	 * 
	 * @return morelines symbol
	 */
	Character getMorelinesSymbol();

	/**
	 * Setter for multilines symbol
	 * 
	 * @param symbol
	 *            new multilines symbol
	 */
	void setMorelinesSymbol(Character symbol);

	/**
	 * Getter for current directory of the environment
	 * 
	 * @return current directory
	 */
	Path getCurrentDirectory();

	/**
	 * Setter for current directory of the environment
	 * 
	 * @param path
	 *            new current directory
	 */
	void setCurrentDirectory(Path path);

	/**
	 * Method that gets entry from the shared data map with a given key
	 * 
	 * @param key
	 *            key of the entry
	 * @return value of the entry stored with the given key
	 */
	Object getSharedData(String key);

	/**
	 * Method that puts certain data into environments shared data map
	 * 
	 * @param key
	 *            key of the entry
	 * @param value
	 *            value of the entry
	 */
	void setSharedData(String key, Object value);
}

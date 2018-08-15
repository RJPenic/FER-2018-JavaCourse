package hr.fer.zemris.java.hw06.shell;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Implementation of the Environment interface that is used by MyShell class
 * 
 * @author Rafael Josip Penić
 *
 */
public class EnvironmentImpl implements Environment {

	private char promptSymbol = '>';
	private char moreLinesSymbol = '\\';
	private char multiLinesSymbol = '|';
	
	/**
	 * Scanner that allows user to enter the values throught the console
	 */
	private Scanner sc = new Scanner(System.in);
	
	/**
	 * Map used as storage for commands
	 */
	private SortedMap<String, ShellCommand> commands = new TreeMap<>();
	
	/**
	 * Current directory of the environment
	 */
	private Path currentDirectory = Paths.get(".").toAbsolutePath().normalize();
	
	/**
	 * Map that stores informations that are shared by commands
	 */
	private Map<String, Object> sharedData = new HashMap<>();

	@Override
	public String readLine() throws ShellIOException {
		try {
			String s = sc.nextLine();
			return s;
		} catch(Exception ex) {
			throw new ShellIOException("Error while reading.");
		}
	}

	@Override
	public void write(String text) throws ShellIOException {
		try {
			System.out.print(text);
		} catch(Exception ex) {
			throw new ShellIOException("Error while writing.");
		}
	}

	@Override
	public void writeln(String text) throws ShellIOException {
		try {
			System.out.println(text);
		} catch(Exception ex) {
			throw new ShellIOException("Error while writing.");
		}
	}

	@Override
	public SortedMap<String, ShellCommand> commands() {
		return Collections.unmodifiableSortedMap(commands);
	}

	@Override
	public Character getMultilineSymbol() {
		return multiLinesSymbol;
	}

	@Override
	public void setMultilineSymbol(Character symbol) {
		multiLinesSymbol = symbol;
	}

	@Override
	public Character getPromptSymbol() {
		return promptSymbol;
	}

	@Override
	public void setPromptSymbol(Character symbol) {
		promptSymbol = symbol;
	}

	@Override
	public Character getMorelinesSymbol() {
		return moreLinesSymbol;
	}

	@Override
	public void setMorelinesSymbol(Character symbol) {
		moreLinesSymbol = symbol;
	}

	/**
	 * Method that puts given pair in the command map
	 * @param key 
	 * @param comm
	 */
	public void putCommand(String key, ShellCommand comm) {
		commands.put(key, comm);
	}

	/**
	 * Method used for closing the environment scanner
	 */
	public void closeScanner() {
		sc.close();
	}

	@Override
	public Path getCurrentDirectory() {
		return currentDirectory;
	}

	@Override
	public void setCurrentDirectory(Path path) {
		if(!path.toFile().exists()) 
			throw new IllegalArgumentException("Given path does not exist.");
		if(!path.toFile().isDirectory()) 
			throw new IllegalArgumentException("Given path is not a directory.");
		
		currentDirectory = path.toAbsolutePath().normalize();
	}

	@Override
	public Object getSharedData(String key) {
		return sharedData.get(key);
	}

	@Override
	public void setSharedData(String key, Object value) {
		sharedData.put(key, value);
	}
}

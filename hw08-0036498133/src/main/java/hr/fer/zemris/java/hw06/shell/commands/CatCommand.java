package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * This class represents a command which opens
 * given file and writes its content to console.
 * 
 * @author Rafael Josip Penić
 *
 */
public class CatCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> args = UtilSeparator.separate(arguments);
		
		if(args.size() != 1 && args.size() != 2) {
			env.writeln("Invalid number of arguments for cat command.");
			return ShellStatus.CONTINUE;
		}
		
		Path origin = env.getCurrentDirectory().resolve(Paths.get(args.get(0)));
		BufferedReader br = null;
		
		try {
			if(args.size() == 2) {
				br = Files.newBufferedReader(origin, Charset.forName(args.get(1)));
			} else {
				br = Files.newBufferedReader(origin);
			}
		} catch (IllegalCharsetNameException|UnsupportedCharsetException ex) {
			env.writeln("Entered charset does not exist or is not supported by JVM.");
			return ShellStatus.CONTINUE;
		} catch(IOException ex) {
			env.writeln("Error while opening " + args.get(0) +".");
			return ShellStatus.CONTINUE;
		}
		
		try {
			String line;
			while((line = br.readLine()) != null) {
				env.writeln(line);
			}
			br.close();
		} catch (IOException ex) {
			env.writeln("Error while reading from " + args.get(0));
		}

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "cat";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> resultList = new ArrayList<>();
		resultList.add("Cat command takes one or two arguments and");
		resultList.add("opens given file and writes its content to console.");
		return Collections.unmodifiableList(resultList);
	}

}

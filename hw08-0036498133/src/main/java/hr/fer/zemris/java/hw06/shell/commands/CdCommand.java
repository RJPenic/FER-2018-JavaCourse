package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Class that represents cd shell command which is used for changing current
 * directory of the shell
 * 
 * @author Rafael Josip Penić
 *
 */
public class CdCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> args = UtilSeparator.separate(arguments);

		if (args.size() != 1) {
			env.writeln("Cd command expects only one argument.");
			return ShellStatus.CONTINUE;
		}

		try {
			env.setCurrentDirectory(env.getCurrentDirectory().resolve(Paths.get(args.get(0))));
			env.writeln("Current directory has been changed.");
		} catch (IllegalArgumentException ex) {
			env.writeln("Given path does not exist or is not a directory.");
		}

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "cd";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> resultList = new ArrayList<>();
		resultList.add("Cd command takes one argument and");
		resultList.add("is used for changing current directory of the environment.");
		return Collections.unmodifiableList(resultList);
	}

}

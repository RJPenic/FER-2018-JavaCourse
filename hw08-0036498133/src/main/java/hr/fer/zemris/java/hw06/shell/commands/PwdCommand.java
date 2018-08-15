package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Class representing a pwd command which is used for printing the current
 * directory
 * 
 * @author Rafael Josip Penić
 *
 */
public class PwdCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> args = UtilSeparator.separate(arguments);

		if (args.size() != 0) {
			env.writeln("Pwd command expects no arguments.");
			return ShellStatus.CONTINUE;
		}

		env.writeln("  " + env.getCurrentDirectory().toString());
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "pwd";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> resultList = new ArrayList<>();
		resultList.add("Pwd command takes no arguments and");
		resultList.add("is used for printing current directory of the environment.");
		return Collections.unmodifiableList(resultList);
	}

}

package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Class that represents a command which offers user useful information about
 * the commands
 * 
 * @author Rafael Josip Penić
 *
 */
public class HelpCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> args = UtilSeparator.separate(arguments);

		if (args.size() > 1) {
			env.writeln("Help command requires one or none arguments.");
			return ShellStatus.CONTINUE;
		}

		SortedMap<String, ShellCommand> map = env.commands();

		if (args.size() == 0) {
			map.forEach((k, v) -> env.writeln(k));
		} else {
			ShellCommand c = map.get(args.get(0));

			if (c == null) {
				env.writeln(args.get(1) + " command does not exist.");
				return ShellStatus.CONTINUE;
			}

			env.writeln(c.getCommandName());
			List<String> list = c.getCommandDescription();

			for (String temp : list) {
				env.writeln(temp);
			}
		}

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "help";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> resultList = new ArrayList<>();
		resultList.add("Help command takes one or no arguments and");
		resultList.add("gives useful information about shell commands");
		return Collections.unmodifiableList(resultList);
	}

}

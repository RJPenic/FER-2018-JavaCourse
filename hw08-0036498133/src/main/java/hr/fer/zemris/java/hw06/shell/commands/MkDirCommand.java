package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Class representing shell command used for creating directories
 * 
 * @author Rafael Josip Penić
 *
 */
public class MkDirCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> args = UtilSeparator.separate(arguments);

		if (args.size() != 1) {
			env.writeln("MkDir command expects only one argument.");
			return ShellStatus.CONTINUE;
		}

		Path p = env.getCurrentDirectory().resolve(Paths.get(args.get(0)));
		if (!p.toFile().mkdirs()) {
			env.writeln("Given directory tree cannot be created.");
		}

		env.writeln("Directory successfully created.");
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "mkdir";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> resultList = new ArrayList<>();
		resultList.add("Copy command takes one argument and");
		resultList.add("is used for creating given directory tree.");
		return Collections.unmodifiableList(resultList);
	}

}

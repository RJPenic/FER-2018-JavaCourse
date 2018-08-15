package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Class representing a push command which pushes current directory on the stack
 * and puts the given directory as the current directory
 * 
 * @author Rafael Josip Penić
 *
 */
public class PushdCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> args = UtilSeparator.separate(arguments);

		if (args.size() != 1) {
			env.writeln("Pushd command expects only one argument.");
			return ShellStatus.CONTINUE;
		}

		if (env.getSharedData("cdstack") == null) {
			env.setSharedData("cdstack", new Stack<Path>());
		}

		Path oldPath = env.getCurrentDirectory();

		try {
			env.setCurrentDirectory(env.getCurrentDirectory().resolve(Paths.get(args.get(0))));
		} catch (IllegalArgumentException ex) {
			env.writeln("Given path is not a valid directory.");
			return ShellStatus.CONTINUE;
		}

		@SuppressWarnings("unchecked")
		Stack<Path> stack = (Stack<Path>) env.getSharedData("cdstack");
		stack.push(oldPath);

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "pushd";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> resultList = new ArrayList<>();
		resultList.add("Pushd command takes one argument.");
		resultList.add("It sets given directory as current directory and stores");
		resultList.add("old current directory on the stack.");
		return Collections.unmodifiableList(resultList);
	}

}

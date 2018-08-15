package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Class representing list command which lists all directories stored on the
 * shared data stack
 * 
 * @author Rafael Josip Penić
 *
 */
public class ListdCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> args = UtilSeparator.separate(arguments);

		if (args.size() != 0) {
			env.writeln("Listd command expects no arguments.");
			return ShellStatus.CONTINUE;
		}

		@SuppressWarnings("unchecked")
		Stack<Path> stack = (Stack<Path>) env.getSharedData("cdstack");

		if (!(stack == null || stack.size() == 0)) {
			env.writeln("Stack:");
			for (int i = stack.size() - 1; i >= 0; i--) {
				env.writeln("  " + stack.get(i).toString());
			}
		} else {
			env.writeln("There are no stored paths on the stack.");
		}

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "listd";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> resultList = new ArrayList<>();
		resultList.add("Listd command takes no arguments.");
		resultList.add("It prints paths that are currently stored on the stack.");
		return Collections.unmodifiableList(resultList);
	}

}

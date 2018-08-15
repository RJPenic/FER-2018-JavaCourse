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
 * Class that represents drop command which removes last added directory from
 * the stack in the shared data map
 * 
 * @author Rafael Josip Penić
 *
 */
public class DropdCommand implements ShellCommand {

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
			env.writeln("Path " + stack.pop() + " has been removed from the stack.");
		} else {
			env.writeln("Stack is empty or it is not created.");
		}

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "dropd";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> resultList = new ArrayList<>();
		resultList.add("Dropd command takes no arguments.");
		resultList.add("It removes element from the top of the path stack but");
		resultList.add("it does not change the current directory.");
		return Collections.unmodifiableList(resultList);
	}

}

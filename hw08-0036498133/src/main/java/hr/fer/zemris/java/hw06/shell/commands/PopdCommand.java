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
 * Class representing a pop command used for removing last added directory from
 * the shared data stack and putting it as the current directory
 * 
 * @author Rafael Josip Penić
 *
 */
public class PopdCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> args = UtilSeparator.separate(arguments);

		if (args.size() != 0) {
			env.writeln("Popd command expects no arguments.");
			return ShellStatus.CONTINUE;
		}

		@SuppressWarnings("unchecked")
		Stack<Path> stack = (Stack<Path>) env.getSharedData("cdstack");

		if (stack == null) {
			env.writeln("Stack is not created.");
			return ShellStatus.CONTINUE;
		}

		if (stack.size() != 0) {
			env.setCurrentDirectory(stack.pop());
		} else {
			env.writeln("Stack is empty.");
		}

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "popd";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> resultList = new ArrayList<>();
		resultList.add("Pushd command takes no arguments.");
		resultList.add("It removes path from the top of the stack");
		resultList.add("and sets it as current directory.");
		return Collections.unmodifiableList(resultList);
	}

}

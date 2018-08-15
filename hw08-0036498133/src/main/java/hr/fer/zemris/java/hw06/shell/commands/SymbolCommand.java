package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Class representing a symbol command which is used for changing environment
 * variables
 * 
 * @author Rafael Josip Penić
 *
 */
public class SymbolCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> list = UtilSeparator.separate(arguments);

		if (list.size() != 1 && list.size() != 2) {
			env.writeln("Invalid number of arguments for symbol command.");
			return ShellStatus.CONTINUE;
		}

		String temp = list.get(0);
		if (list.size() == 1) {
			switch (temp) {
			case ("PROMPT"):
				env.writeln("Symbol for PROMPT is '" + Character.toString(env.getPromptSymbol()) + "'");
				break;

			case ("MULTILINE"):
				env.writeln("Symbol for MULTILINE is '" + Character.toString(env.getMultilineSymbol()) + "'");
				break;

			case ("MORELINES"):
				env.writeln("Symbol for MORELINES is '" + Character.toString(env.getMorelinesSymbol()) + "'");
				break;

			default:
				env.writeln("Invalid directive.");
			}
		} else {
			if (list.get(1).length() != 1) {
				env.writeln("Second argument should be character.");
				return ShellStatus.CONTINUE;
			}

			switch (temp) {
			case ("PROMPT"):
				env.writeln("Symbol for PROMPT changed from '" + env.getPromptSymbol() + "' to '"
						+ list.get(1).charAt(0) + "'");

				env.setPromptSymbol(list.get(1).charAt(0));
				break;

			case ("MULTILINE"):
				env.writeln("Symbol for MULTILINE changed from '" + env.getMultilineSymbol() + "' to '"
						+ list.get(1).charAt(0) + "'");

				env.setMultilineSymbol(list.get(1).charAt(0));
				break;

			case ("MORELINES"):
				env.writeln("Symbol for MORELINES changed from '" + env.getMorelinesSymbol() + "' to '"
						+ list.get(1).charAt(0) + "'");

				env.setMorelinesSymbol(list.get(1).charAt(0));
				break;

			default:
				env.writeln("Unknow directive.");
			}
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "symbol";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> resultList = new ArrayList<>();
		resultList.add("Symbol command is used for changing environment variables");
		resultList.add("like prompt, multiline and morelines characters.");
		return Collections.unmodifiableList(resultList);
	}

}

package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Class represents command that lists names of supported charsets for your Java
 * platform.
 * 
 * @author Rafael Josip Penić
 *
 */
public class CharsetsCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> args = UtilSeparator.separate(arguments);

		if (args.size() != 0) {
			env.writeln("Charsets command expects no arguments!");
			return ShellStatus.CONTINUE;
		}

		SortedMap<String, Charset> map = Charset.availableCharsets();

		map.forEach((k, v) -> env.writeln(k));

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "charsets";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> resultList = new ArrayList<>();
		resultList.add("Charset command takes no arguments and");
		resultList.add("lists names of supported charsets for Java platform");
		return Collections.unmodifiableList(resultList);
	}

}

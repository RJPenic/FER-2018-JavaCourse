package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Class representing a shell command which removes directory and its content
 * 
 * @author Rafael Josip Penić
 *
 */
public class RmTreeCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> args = UtilSeparator.separate(arguments);

		if (args.size() != 1) {
			env.writeln("RmTree command expects only one argument.");
			return ShellStatus.CONTINUE;
		}

		Path p = env.getCurrentDirectory().resolve(Paths.get(args.get(0)));

		if (!p.toFile().exists()) {
			env.writeln("Given path does not exist.");
			return ShellStatus.CONTINUE;
		}

		if (!p.toFile().isDirectory()) {
			env.writeln("Given path is not a directory.");
			return ShellStatus.CONTINUE;
		}

		deleteFiles(p);

		if (p.toFile().delete()) {
			env.writeln(p.toString() + " directory has been deleted.");
		} else {
			env.writeln(p.toString() + " could not be deleted.");
		}

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "rmtree";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> resultList = new ArrayList<>();
		resultList.add("RmTree command takes one argument and");
		resultList.add("it deletes given directory and its content.");
		return Collections.unmodifiableList(resultList);
	}

	/**
	 * Method used for deleting files from the given directory
	 * 
	 * @param p
	 *            directory from which the files will be deleted
	 */
	private void deleteFiles(Path p) {
		File[] fileArr = p.toFile().listFiles();

		for (File f : fileArr) {
			if (f.isFile()) {
				if (!f.delete())
					return;
			} else {
				deleteFiles(f.toPath());
				if (!f.delete()) {
					return;
				}
			}
		}
	}
}

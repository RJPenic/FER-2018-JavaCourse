package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Class representing shell command for printing current files in the given
 * directory and basic info about those files
 * 
 * @author Rafael Josip Penić
 *
 */
public class LsCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> args = UtilSeparator.separate(arguments);

		if (args.size() != 1) {
			env.writeln("Ls command expects one argument.");
			return ShellStatus.CONTINUE;
		}

		Path p = env.getCurrentDirectory().resolve(Paths.get(args.get(0)));

		if (!p.toFile().isDirectory()) {
			env.writeln(args.get(0) + " is not a directory.");
			return ShellStatus.CONTINUE;
		}

		File[] files = p.toFile().listFiles();

		for (File temp : files) {
			String basicAttr = constructAttrString(temp);

			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Path path = temp.toPath();

				BasicFileAttributeView faView = Files.getFileAttributeView(path, BasicFileAttributeView.class,
						LinkOption.NOFOLLOW_LINKS);

				BasicFileAttributes attributes = faView.readAttributes();
				FileTime fileTime = attributes.creationTime();
				String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));

				env.writeln(
						String.format("%s %12s %s %s", basicAttr, temp.length(), formattedDateTime, temp.getName()));
			} catch (IOException ex) {
				env.writeln("Error while listing!");
				return ShellStatus.CONTINUE;
			}
		}

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "ls";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> resultList = new ArrayList<>();
		resultList.add("Ls command takes a single argument and");
		resultList.add("writes a directory listing.");
		return Collections.unmodifiableList(resultList);
	}

	/**
	 * Method that constructs string containing basic info about file attributes
	 * 
	 * @param file
	 *            file which attributes will be taken in consideration
	 * @return string containing basic info about file attributes
	 */
	private static String constructAttrString(File file) {
		return (file.isDirectory() ? "d" : "-") + (file.canRead() ? "r" : "-") + (file.canWrite() ? "w" : "-")
				+ (file.canExecute() ? "x" : "-");
	}

}

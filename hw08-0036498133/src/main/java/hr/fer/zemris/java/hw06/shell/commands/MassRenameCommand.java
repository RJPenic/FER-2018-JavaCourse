package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Class representing massrename command which is used for mass renaming and
 * moving files from one directory to another
 * 
 * @author Rafael Josip Penić
 *
 */
public class MassRenameCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> args = UtilSeparator.separate(arguments);

		if (args.size() != 4 && args.size() != 5) {
			env.writeln("MassRename command expects 4 or 5 arguments");
			return ShellStatus.CONTINUE;
		}

		Path dir1 = env.getCurrentDirectory().resolve(Paths.get(args.get(0)));
		Path dir2 = env.getCurrentDirectory().resolve(Paths.get(args.get(1)));

		if (!(dir1.toFile().exists() && dir1.toFile().isDirectory())) {
			env.writeln("First given path is not a directory or it does not exist.");
			return ShellStatus.CONTINUE;
		}

		if (!(dir2.toFile().exists() && dir2.toFile().isDirectory())) {
			env.writeln("Second given path is not a directory or it does not exist.");
			return ShellStatus.CONTINUE;
		}

		Pattern patt = null;
		try {
			patt = Pattern.compile(args.get(3));
		} catch (PatternSyntaxException ex) {
			env.writeln("Given pattern expression is not valid.");
			return ShellStatus.CONTINUE;
		}

		switch (args.get(2)) {
		case ("filter"):
			if (args.size() != 4) {
				env.writeln("For CMD filter, massrename expects 4 arguments.");
				break;
			}

			File[] fileArray = dir1.toFile().listFiles();

			for (File f : fileArray) {
				if (patt.matcher(f.getName()).matches()) {
					env.writeln("  " + f.getName());
				}
			}

			break;

		case ("groups"):
			if (args.size() != 4) {
				env.writeln("For CMD groups, massrename expects 4 arguments.");
				break;
			}

			File[] fileArr = dir1.toFile().listFiles();

			for (File f : fileArr) {
				Matcher m = patt.matcher(f.getName());

				if (m.matches()) {
					env.write(f.getName() + " ");

					for (int i = 0; i <= m.groupCount(); i++) {
						env.write(i + ":  " + m.group(i) + " ");
					}

					env.write("\n");
				}
			}

			break;

		case ("show"):
			if (args.size() != 5) {
				env.writeln("For CMD show, massrename expects 5 arguments.");
				break;
			}

			try {
				parseAndUse(args, patt, env, dir1, dir2,
						(p1, p2) -> env.writeln(p1.normalize() + " => " + p2.normalize()));
			} catch (IndexOutOfBoundsException ex) {
				env.writeln("Group index exceeded allowed limit.");
			}
			break;

		case ("execute"):
			if (args.size() != 5) {
				env.writeln("For CMD execute, massrename expects 5 arguments.");
				break;
			}

			try {
				parseAndUse(args, patt, env, dir1, dir2, (p1, p2) -> {
					env.writeln(p1.normalize() + " => " + p2.normalize());
					try {
						Files.move(p1, p2);
					} catch (IOException e) {
						env.writeln("Error while moving files.");
					}
				});
			} catch (IndexOutOfBoundsException ex) {
				env.writeln("Group index exceeded allowed limit.");
			}
			break;

		default:
			env.writeln("Unrecognized CMD.");
		}

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "massrename";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> resultList = new ArrayList<>();
		resultList.add("MassRename command takes 4 or 5 arguments.");
		resultList.add("It is used for massive renaming and moving files.");
		return Collections.unmodifiableList(resultList);
	}

	/**
	 * Method parses given entry and moves files from one directory and another or
	 * generates new names according to biconsumer
	 * 
	 * @param args
	 *            arguments of the command
	 * @param patt
	 *            pattern
	 * @param env
	 *            current environment of the shell
	 * @param directory1
	 *            origin directory
	 * @param directory2
	 *            target directory
	 * @param bicon
	 *            biconsumer which determines what should be done
	 */
	private void parseAndUse(List<String> args, Pattern patt, Environment env, Path directory1, Path directory2,
			BiConsumer<Path, Path> bicon) {
		NameBuilderParser parser = new NameBuilderParser(args.get(4));
		NameBuilder builder = null;

		try {
			builder = parser.getNameBuilder();
		} catch (IllegalArgumentException ex) {
			env.writeln("Given entry is not valid.");
			return;
		}

		File[] fileArr = directory1.toFile().listFiles();

		for (File f : fileArr) {
			Matcher matcher = patt.matcher(f.getName());
			if (!matcher.matches())
				continue;
			NameBuilderInfo info = new NameBuilderInfoImpl(matcher);
			builder.execute(info);
			String novoIme = info.getStringBuilder().toString();

			Path origin = f.toPath();
			Path target = Paths.get(directory2.toString() + File.separator + novoIme);

			bicon.accept(origin, target);
		}
	}

}

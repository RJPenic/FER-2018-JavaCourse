package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Class representing tree shell command which prints file tree
 * 
 * @author Rafael Josip Penić
 *
 */
public class TreeCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> args = UtilSeparator.separate(arguments);

		if (args.size() != 1) {
			env.writeln("Command tree expects only one argument.");
			return ShellStatus.CONTINUE;
		}

		Path p = env.getCurrentDirectory().resolve(Paths.get(args.get(0)));

		if(!p.toFile().exists()) {
			env.writeln("Given path does not exist.");
			return ShellStatus.CONTINUE;
		}
		
		if (!p.toFile().isDirectory()) {
			env.writeln("Given file is not a directory.");
			return ShellStatus.CONTINUE;
		}

		Visitor vis = new Visitor(env);

		try {
			Files.walkFileTree(p, vis);
		} catch (IOException e) {
			env.writeln("Error while walking throught the directory tree.");
		}

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "tree";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> resultList = new ArrayList<>();
		resultList.add("Tree command takes a single argument and");
		resultList.add("prints a tree.");
		return Collections.unmodifiableList(resultList);
	}

	/**
	 * Implementation of the FileVisitor which prints the file tree
	 * 
	 * @author Rafael Josip Penić
	 *
	 */
	private static class Visitor extends SimpleFileVisitor<Path> {
		/**
		 * number of spaces in front of the file string when printed
		 */
		private int shift = 1;

		/**
		 * Current environment of the shell
		 */
		private Environment env;

		public Visitor(Environment env) {
			this.env = env;
		}

		@Override
		public FileVisitResult visitFile(Path dir, BasicFileAttributes attr) {
			env.writeln(String.format("%" + shift + "s %s", "", dir.getFileName()));
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
			shift -= 2;
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attr) {
			String s = String.format("%" + shift + "s", "");
			env.writeln(String.format("%s %s", s, dir.getFileName()));
			shift += 2;
			return FileVisitResult.CONTINUE;
		}

	}
}

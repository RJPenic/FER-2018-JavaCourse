package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Represents a cptree command which is used for for copying certain file tree
 * into another one.
 * 
 * @author Rafael Josip Penić
 *
 */
public class CpTreeCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> args = UtilSeparator.separate(arguments);

		if (args.size() != 2) {
			env.writeln("CpTree command expects two arguments.");
			return ShellStatus.CONTINUE;
		}

		Path path1 = env.getCurrentDirectory().resolve(Paths.get(args.get(0)));
		Path path2 = env.getCurrentDirectory().resolve(Paths.get(args.get(1)));

		if (!path1.toFile().isDirectory()) {
			env.writeln("First given path is not a directory.");
			return ShellStatus.CONTINUE;
		}

		if (!path2.toFile().isDirectory()) {
			env.writeln("Second given path is not a directory.");
			return ShellStatus.CONTINUE;
		}

		try {
			copyTree(path1, path2);
			env.writeln("Tree successfully copied.");
		} catch (IOException | IllegalArgumentException ex) {
			env.writeln("Error while copying the tree.");
		}

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "cptree";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> resultList = new ArrayList<>();
		resultList.add("CpTree command takes two arguments and");
		resultList.add("copies first tree into another.");
		return Collections.unmodifiableList(resultList);
	}

	/**
	 * Method that copies one tree to another
	 * 
	 * @param p1
	 *            origin tree
	 * @param p2
	 *            tree in which the origin tree will be copied
	 * @throws IOException
	 *             in case of an error while copying files
	 */
	private void copyTree(Path p1, Path p2) throws IOException {
		if (p2.toFile().exists()) {
			Path dir = Paths.get(p2.toString() + File.separator + p1.getFileName().toString());

			if (!dir.toFile().mkdir()) {
				throw new IOException(dir + " directory could not be created.");
			}

			iterateThroughFilesList(p1, dir);
		} else if (p2.getParent().toFile().exists() && !p2.toFile().exists()) {

			if (!p2.toFile().mkdir()) {
				throw new IOException(p2 + " directory could not be created.");
			}

			iterateThroughFilesList(p1, p2);
		} else {
			throw new IllegalArgumentException(
					"It is not possible to do copyTree " + "command because target directories do not exist.");
		}
	}

	/**
	 * Copies given file to a target directory
	 * 
	 * @param file
	 *            file that will copied
	 * @param target
	 *            target path where the file will be copied
	 * @throws IOException
	 *             thrown in case of an error when copying
	 */
	private void copyFile(Path file, Path target) throws IOException {
		InputStream is = new BufferedInputStream(Files.newInputStream(file, StandardOpenOption.READ));
		OutputStream os = new BufferedOutputStream(
				Files.newOutputStream(Paths.get(target.toString() + File.separator + file.getFileName().toString()),
						StandardOpenOption.CREATE));

		byte[] buffer = new byte[1024];
		while (true) {
			int r = is.read(buffer);
			if (r < 1)
				break;
			os.write(buffer, 0, r);
		}

		os.close();
		is.close();
	}

	/**
	 * Method that copies directory into another one
	 * 
	 * @param dir
	 *            directory that will be copied
	 * @param target
	 *            target directory
	 * @throws IOException
	 *             thrown in case of an error when copying
	 */
	private void copyDirectory(Path dir, Path target) throws IOException {
		Path target1 = Paths.get(target.toString() + File.separator + dir.getFileName());

		if (!target1.toFile().mkdir()) {
			throw new IOException(target1 + " directory could not be created.");
		}

		iterateThroughFilesList(dir, target1);
	}

	/**
	 * Method that iterates through the files list and copies them in the given
	 * target path
	 * 
	 * @param p1
	 *            directory to be copied
	 * @param p2
	 *            target directory
	 * @throws IOException
	 *             when error while copying
	 */
	private void iterateThroughFilesList(Path p1, Path p2) throws IOException {
		File[] fileArray = p1.toFile().listFiles();

		for (File f : fileArray) {
			if (f.isFile()) {
				copyFile(f.toPath(), p2);
			} else {
				copyDirectory(f.toPath(), p2);
			}
		}
	}
}

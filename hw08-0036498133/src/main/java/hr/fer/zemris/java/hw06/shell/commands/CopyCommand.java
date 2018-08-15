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
 * Class that represents command which allows user to copy and overwrite files
 * 
 * @author Rafael Josip Penić
 *
 */
public class CopyCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> args = UtilSeparator.separate(arguments);

		if (args.size() != 2) {
			env.writeln("Command copy expects 2 arguments.");
			return ShellStatus.CONTINUE;
		}

		Path origin = env.getCurrentDirectory().resolve(Paths.get(args.get(0)));
		Path target = env.getCurrentDirectory().resolve(Paths.get(args.get(1)));

		if(origin.toFile().isDirectory()) {
			env.writeln("Copy command does not work with directories.");
			return ShellStatus.CONTINUE;
		}
		
		if (target.toFile().isDirectory()) {
			target = env.getCurrentDirectory().resolve(Paths.get(args.get(1) +File.separator + origin.toFile().getName()));
		}

		if (origin.normalize().equals(target.normalize())) {
			env.writeln("Given paths are equal.");
			return ShellStatus.CONTINUE;
		}

		if (Files.exists(target)) {
			env.writeln("Entered target file already exists. Do you want to overwrite it?(y/n)");
			String s = env.readLine();
			if (!s.equals("y")) {
				env.writeln("Copying aborted.");
				return ShellStatus.CONTINUE;
			}
		}

		InputStream is = null;
		OutputStream os = null;

		try {
			is = new BufferedInputStream(Files.newInputStream(origin, StandardOpenOption.READ));
			os = new BufferedOutputStream(
					Files.newOutputStream(target, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING));
		} catch (IOException ex) {
			env.writeln("Error while opening files.");
			return ShellStatus.CONTINUE;
		}

		try {
			byte[] buffer = new byte[1024];
			while (true) {
				int r = is.read(buffer);
				if (r < 1)
					break;
				os.write(buffer, 0, r);
			}
			os.close();
			is.close();
			env.writeln("File has been successfully copied.");
		} catch (IOException ex) {
			env.writeln("Error while copying.");
		}

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "copy";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> resultList = new ArrayList<>();
		resultList.add("Copy command takes two arguments and");
		resultList.add("is used for copying files.");
		return Collections.unmodifiableList(resultList);
	}

}

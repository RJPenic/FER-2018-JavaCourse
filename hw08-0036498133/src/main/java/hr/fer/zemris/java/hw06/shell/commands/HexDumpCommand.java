package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw07.crypto.Util;

/**
 * Class that represents a command which is used to show hexadecimal
 * interpretation of the file
 * 
 * @author Rafael Josip Penić
 * 
 */
public class HexDumpCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> args = UtilSeparator.separate(arguments);

		if (args.size() != 1) {
			env.writeln("HexDump command expects only one argument.");
			return ShellStatus.CONTINUE;
		}

		Path p = env.getCurrentDirectory().resolve(Paths.get(args.get(0)));

		if (p.toFile().isDirectory()) {
			env.writeln("Given entry is directory.");
			return ShellStatus.CONTINUE;
		}

		try {
			InputStream is = new BufferedInputStream(Files.newInputStream(p));

			int k = 0;
			while (true) {
				byte[] buff = new byte[16];
				int r = is.read(buff);
				if (r < 1)
					break;
				env.write(addZeros(Integer.toHexString(k)) + ": ");
				printHexDump(env, buff);
				printStringBasic(env, buff, r);
				k += 16;
			}

			is.close();
		} catch (IOException ex) {
			env.writeln("Error while reading from file.");
			return ShellStatus.CONTINUE;
		}

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "hexdump";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> resultList = new ArrayList<>();
		resultList.add("Copy command takes one argument and");
		resultList.add("produces hex output.");
		return Collections.unmodifiableList(resultList);
	}

	/**
	 * Method that constructs a hex string with the length of 8 by adding zeros in
	 * front of the number(if necessary)
	 * 
	 * @param hex
	 *            hexadecimal string on which the zeros will be added
	 * @return string with zeros in front and length of 8
	 */
	private static String addZeros(String hex) {
		int len = hex.length();

		StringBuilder sb = new StringBuilder();

		for (int i = len; i < 8; i++) {
			sb.append("0");
		}

		return sb.append(hex).toString();
	}

	/**
	 * Method that prints result of hexdump command
	 * @param env current environment
	 * @param array byte of arrays to be used when printing
	 */
	private static void printHexDump(Environment env, byte[] array) {
		for (int i = 0; i < array.length; i++) {
			byte[] tempArr = { array[i] };
			env.write(String.format("%2s", array[i] == 0 ? "  " : Util.byteToHex(tempArr)));
			if ((i + 1) % 8 == 0) {
				env.write("|");
			} else {
				env.write(" ");
			}
		}

		env.write(" ");
	}

	/**
	 * Method that prints string with only basic characters while other characters
	 * are portrayed as "." character
	 * 
	 * @param env
	 *            current environment
	 * @param array
	 *            array of bytes to be printed
	 * @param r
	 *            number that represents how much the array is "filled"
	 */
	private static void printStringBasic(Environment env, byte[] array, int r) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < r; i++) {
			if (array[i] < 32 || array[i] > 127) {
				sb.append(".");
			} else {
				sb.append((char) array[i]);
			}
		}

		env.writeln(sb.toString());
	}
}

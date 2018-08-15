package hr.fer.zemris.java.hw06.shell;


import hr.fer.zemris.java.hw06.shell.commands.CatCommand;
import hr.fer.zemris.java.hw06.shell.commands.CdCommand;
import hr.fer.zemris.java.hw06.shell.commands.CharsetsCommand;
import hr.fer.zemris.java.hw06.shell.commands.CopyCommand;
import hr.fer.zemris.java.hw06.shell.commands.CpTreeCommand;
import hr.fer.zemris.java.hw06.shell.commands.DropdCommand;
import hr.fer.zemris.java.hw06.shell.commands.ExitCommand;
import hr.fer.zemris.java.hw06.shell.commands.HelpCommand;
import hr.fer.zemris.java.hw06.shell.commands.HexDumpCommand;
import hr.fer.zemris.java.hw06.shell.commands.ListdCommand;
import hr.fer.zemris.java.hw06.shell.commands.LsCommand;
import hr.fer.zemris.java.hw06.shell.commands.MassRenameCommand;
import hr.fer.zemris.java.hw06.shell.commands.MkDirCommand;
import hr.fer.zemris.java.hw06.shell.commands.PopdCommand;
import hr.fer.zemris.java.hw06.shell.commands.PushdCommand;
import hr.fer.zemris.java.hw06.shell.commands.PwdCommand;
import hr.fer.zemris.java.hw06.shell.commands.RmTreeCommand;
import hr.fer.zemris.java.hw06.shell.commands.SymbolCommand;
import hr.fer.zemris.java.hw06.shell.commands.TreeCommand;

/**
 * Class representing basic shell offering some basic commands like copy, mkdir
 * and etc.
 * 
 * @author Rafael Josip Penić
 *
 */
public class MyShell {

	/**
	 * Environment of the shell
	 */
	private static EnvironmentImpl env = new EnvironmentImpl();
	
	static {
		env.putCommand("exit", new ExitCommand());
		env.putCommand("ls", new LsCommand());
		env.putCommand("copy", new CopyCommand());
		env.putCommand("mkdir", new MkDirCommand());
		env.putCommand("charsets", new CharsetsCommand());
		env.putCommand("hexdump", new HexDumpCommand());
		env.putCommand("tree", new TreeCommand());
		env.putCommand("cat", new CatCommand());
		env.putCommand("help", new HelpCommand());
		env.putCommand("pwd", new PwdCommand());
		env.putCommand("cd", new CdCommand());
		env.putCommand("pushd", new PushdCommand());
		env.putCommand("popd", new PopdCommand());
		env.putCommand("listd", new ListdCommand());
		env.putCommand("dropd", new DropdCommand());
		env.putCommand("rmtree", new RmTreeCommand());
		env.putCommand("massrename", new MassRenameCommand());
		env.putCommand("cptree", new CpTreeCommand());
		env.putCommand("symbol", new SymbolCommand());
	}
	
	/**
	 * Main method
	 * 
	 * @param args
	 *            command line arguments
	 */
	public static void main(String[] args) {
		
		ShellStatus status = null;
		env.writeln("Welcome to MyShell v 1.0");
		
		do {
			env.write(env.getCurrentDirectory().toString() + Character.toString(env.getPromptSymbol()) + " ");
			StringBuilder sb = new StringBuilder();
			String line;
			boolean first = true;
			do {
				if (!first) {
					env.write(env.getMultilineSymbol() + " ");
				}
				first = false;
				line = env.readLine().trim();

				if (line.endsWith(env.getMorelinesSymbol().toString())) {
					sb.append(line.substring(0, line.length() - 1));
				} else {
					sb.append(line);
				}
			} while (line.trim().endsWith(env.getMorelinesSymbol().toString()));

			String tempLine = sb.toString().trim().replace("\t", " ");
			String commString = tempLine.split("\\s+")[0];
			String arguments = tempLine
					.substring(tempLine.indexOf(" ") == -1 ? commString.length() : tempLine.indexOf(" "));

			ShellCommand comm = env.commands().get(commString);

			if (comm == null) {
				env.writeln("Unknown command.");
				continue;
			}

			status = comm.executeCommand(env, arguments);
				
		} while (status != ShellStatus.TERMINATE);

		env.closeScanner();
	}
}

package hr.fer.zemris.java.hw06.shell.commands;

/**
 * Name builder which appends constant string to a string builder
 * 
 * @author Rafael Josip Penić
 *
 */
public class NameBuilderConstantString implements NameBuilder {

	/**
	 * String to be appended
	 */
	private String s;

	/**
	 * Constructor for NameBuilders with a constant string
	 * 
	 * @param s
	 *            string that will be appended
	 */
	public NameBuilderConstantString(String s) {
		this.s = s;
	}

	@Override
	public void execute(NameBuilderInfo info) {
		info.getStringBuilder().append(s);
	}

}

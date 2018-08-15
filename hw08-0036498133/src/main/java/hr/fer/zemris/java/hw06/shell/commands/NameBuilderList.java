package hr.fer.zemris.java.hw06.shell.commands;

import java.util.List;

/**
 * Name builder which constructs a name by executing all name builders from the
 * list
 * 
 * @author Rafael Josip Penić
 *
 */
public class NameBuilderList implements NameBuilder {

	/**
	 * List of name builders
	 */
	private List<NameBuilder> list;

	/**
	 * Constructor for NameBuilderList object
	 * 
	 * @param list
	 *            list of the name builders that will be stored into NameBuilderList
	 *            object
	 */
	public NameBuilderList(List<NameBuilder> list) {
		this.list = list;
	}

	@Override
	public void execute(NameBuilderInfo info) {
		for (NameBuilder nb : list) {
			nb.execute(info);
		}
	}

}

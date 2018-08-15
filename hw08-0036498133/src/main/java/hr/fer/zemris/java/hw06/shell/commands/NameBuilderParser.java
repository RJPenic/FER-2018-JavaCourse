package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.List;

/**
 * Parser that constructs a name builder used for constructing a new name
 * 
 * @author Rafael Josip Penić
 *
 */
public class NameBuilderParser {

	/**
	 * Character array that is parsed
	 */
	private char[] array;

	/**
	 * Constructor for parser which "translates" given string into char array
	 * 
	 * @param izraz
	 *            string to be parsed
	 */
	public NameBuilderParser(String izraz) {
		array = izraz.toCharArray();
	}

	/**
	 * Method that constructs a name builder which executes all name builders of the
	 * constructed list.
	 * 
	 * @return name builder which constructs the new name
	 */
	public NameBuilder getNameBuilder() {
		List<NameBuilder> list = new ArrayList<>();
		int i = 0;
		while (i < array.length) {

			while (Character.isWhitespace(array[i])) {
				i++;
			}

			StringBuilder sb = new StringBuilder();

			if (i + 1 < array.length && array[i] == '$' && array[i + 1] == '{') {
				i += 2;
				while (i < array.length) {
					if (array[i] == '}') {
						i++;
						break;
					}
					sb.append(array[i++]);
				}

				try {
					list.add(new NameBuilderGroup(sb.toString()));
				} catch (NumberFormatException ex) {
					throw new IllegalArgumentException("Given entry is not valid.");
				}
			} else {
				while (i < array.length) {
					if (i + 1 < array.length && array[i] == '$' && array[i + 1] == '{')
						break;
					sb.append(array[i++]);
				}

				list.add(new NameBuilderConstantString(sb.toString()));
			}
		}

		return new NameBuilderList(list);
	}
}

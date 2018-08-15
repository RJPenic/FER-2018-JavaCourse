package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class containing method for separation of the string containing
 * command arguments
 * 
 * @author Rafael Josip Penić
 *
 */
public class UtilSeparator {

	/**
	 * Constructs a list that contains command arguments
	 * 
	 * @param s
	 *            string from which the arguments will be extracted
	 * @return list with the arguments
	 */
	public static List<String> separate(String s) {
		String temp = s.trim();
		List<String> tempList = new ArrayList<>();

		for (int i = 0; i < temp.length(); i++) {
			StringBuilder sb = new StringBuilder();

			while (i < temp.length() && Character.isWhitespace(temp.charAt(i))) {
				i++;
			}

			if (i >= temp.length())
				break;

			if (temp.charAt(i) != '"') {
				while (i < temp.length() && !(temp.charAt(i) == '"' && temp.charAt(i - 1) != '\\')
						&& !Character.isWhitespace(temp.charAt(i))) {
					sb.append(temp.charAt(i++));
				}
			} else {
				i++;
				while (i < temp.length() && !(temp.charAt(i) == '"' && temp.charAt(i - 1) != '\\')) {
					sb.append(temp.charAt(i++));
				}

				i++;
			}

			tempList.add(sb.toString().replaceAll("\\\"", "\"").replace("\\\\", "\\").trim());
		}

		return tempList;
	}
}

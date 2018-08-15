package hr.fer.zemris.java.visitors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Utility class used when reading from data source. It provides methods like
 * reading lines to a map, list and similar.
 * 
 * @author Rafael Josip Penić
 *
 */
public class UtilReader {

	/**
	 * Reads given list and writes its content into given map(dictionary)
	 * 
	 * @param tempList
	 *            list containing, for example, lines of a document
	 * @param tempMap
	 *            dictionary in which the words will be loaded
	 */
	public static void readListToMap(List<String> tempList, Map<String, Integer> tempMap) {
		for (String word : tempList) {
			word = word.toLowerCase();
			if (tempMap.get(word) == null) {
				tempMap.put(word, 1);
			} else {
				tempMap.put(word, 1 + tempMap.get(word));
			}
		}
	}

	/**
	 * Loads given line into the given map(which usually represents a dictionary)
	 * 
	 * @param line
	 *            line that will be loaded
	 * @param tempMap
	 *            map into which words of the line will be loaded
	 */
	public static void readLineToMap(String line, Map<String, Integer> tempMap) {
		readListToMap(readLineToList(line), tempMap);
	}

	/**
	 * Loads words of a given line into a set
	 * 
	 * @param line
	 *            line which words will be loaded into set
	 * @param tempSet
	 *            set into which the words will be loaded
	 */
	public static void readLineToSet(String line, Set<String> tempSet) {
		tempSet.addAll(readLineToList(line));
	}

	/**
	 * Loads given line into a newly constructed list
	 * 
	 * @param line
	 *            line to be loaded
	 * @return list containing words of the line
	 */
	public static List<String> readLineToList(String line) {
		int lineLength = line.length();
		char[] lineArr = line.toCharArray();

		List<String> tempList = new ArrayList<>();

		for (int i = 0; i < lineLength; i++) {
			StringBuilder sb = new StringBuilder();

			for (int j = i; j < lineLength; j++) {
				if (Character.isAlphabetic(lineArr[j])) {
					sb.append(lineArr[j]);
					i++;
				} else {
					break;
				}
			}

			String word = sb.toString().toLowerCase();
			if (word.isEmpty())
				continue;
			tempList.add(word);
		}

		return tempList;
	}
}

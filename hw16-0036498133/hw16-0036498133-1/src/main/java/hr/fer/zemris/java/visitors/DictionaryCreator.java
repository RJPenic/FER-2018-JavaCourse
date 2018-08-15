package hr.fer.zemris.java.visitors;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Creator of the dictionary. It iterates through the file tree of the data
 * source and it constructs a dictionary while doing so.
 * 
 * @author Rafael Josip Penić
 *
 */
public class DictionaryCreator extends SimpleFileVisitor<Path> {

	/**
	 * Map representing a word dictionary. Words are paired with number of file they
	 * appear in.
	 */
	private Map<String, Integer> dictionary = new HashMap<>();

	/**
	 * Set containing stop words
	 */
	private Set<String> stopWordsSet;

	/**
	 * Number representing number of files in the data source
	 */
	private int numberOfFiles = 0;

	/**
	 * Constructor which initializes stopWordsSet
	 * 
	 * @param stopWordsSet
	 *            set containing words that can be ignored
	 */
	public DictionaryCreator(Set<String> stopWordsSet) {
		Objects.requireNonNull(stopWordsSet, "Given stop word list reference is null.");
		this.stopWordsSet = stopWordsSet;
	}

	/**
	 * Getter for dictionary map
	 * 
	 * @return dictionary
	 */
	public Map<String, Integer> getDictionary() {
		return dictionary;
	}

	/**
	 * Getter for number of files
	 * 
	 * @return number of files in data source
	 */
	public int getNumberOfFiles() {
		return numberOfFiles;
	}

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
		List<String> lines = Files.readAllLines(file, StandardCharsets.UTF_8);
		Set<String> tempSet = new HashSet<>();
		numberOfFiles++;

		for (String line : lines) {
			UtilReader.readLineToSet(line, tempSet);
		}

		for (String word : tempSet) {
			if (!stopWordsSet.contains(word)) {
				word = word.toLowerCase();
				if (dictionary.get(word) == null) {
					dictionary.put(word, 1);
				} else {
					dictionary.put(word, 1 + dictionary.get(word));
				}
			}
		}

		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
		System.err.println("Error while accessing " + file.toAbsolutePath().toString());
		return FileVisitResult.CONTINUE;
	}
}

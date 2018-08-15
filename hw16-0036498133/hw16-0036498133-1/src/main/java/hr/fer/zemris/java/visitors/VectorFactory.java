package hr.fer.zemris.java.visitors;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import hr.fer.zemris.java.vector.TFIDFVector;

/**
 * Class representing a vector "factory" that will construct TFIDF vectors for
 * each file that will later be used for search engine implementation.
 * VectorFactory implements SimpleFileVisitor which makes it useful for
 * iterating through file trees.
 * 
 * @author Rafael Josip Penić
 *
 */
public class VectorFactory extends SimpleFileVisitor<Path> {

	/**
	 * Collection containing entries that pair up each file with a specific TFIDF
	 * vector
	 */
	private Map<Path, TFIDFVector> vectors = new HashMap<>();

	/**
	 * Dictionary containing all words from the data source. Those words are paired
	 * with the number of files they appear in.
	 */
	private Map<String, Integer> dictionary;

	/**
	 * Number of files in the data source/data base
	 */
	private int numberOfFiles;

	/**
	 * Set containing stop words of the specific language
	 */
	private Set<String> stopWordsSet;

	/**
	 * Constructor for VectorFactory objects
	 * 
	 * @param dictionary
	 *            dictionary containing words needed for calculation
	 * @param numberOfFiles
	 *            number of files in data source
	 * @param stopWordsSet
	 *            set containing stop words that will be ignored
	 */
	public VectorFactory(Map<String, Integer> dictionary, int numberOfFiles, Set<String> stopWordsSet) {
		Objects.requireNonNull(dictionary, "Given dictionary reference is null.");
		Objects.requireNonNull(stopWordsSet, "Given stop words set reference is null.");

		this.dictionary = dictionary;
		this.numberOfFiles = numberOfFiles;
		this.stopWordsSet = stopWordsSet;
	}

	/**
	 * Getter for vectors map
	 * 
	 * @return vectors map
	 */
	public Map<Path, TFIDFVector> getVectors() {
		return vectors;
	}

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
		List<String> lines = Files.readAllLines(file, StandardCharsets.UTF_8);
		Map<String, Integer> tempMap = new HashMap<>();

		for (String line : lines) {
			UtilReader.readLineToMap(line, tempMap);
		}

		Map<String, Double> vectorMap = new HashMap<>();
		tempMap.forEach((k, v) -> {
			if (!stopWordsSet.contains(k)) {
				vectorMap.put(k, calculate(k, tempMap.get(k)));
			}
		});

		vectors.put(file, new TFIDFVector(vectorMap));

		return FileVisitResult.CONTINUE;
	}

	/**
	 * Calculate TFIDF factor of the word and a certain file
	 * 
	 * @param word
	 *            word which the factor is calculated for
	 * @param wordCountInFile
	 *            number of the given word in the file
	 * @return calculate factor
	 */
	public double calculate(String word, int wordCountInFile) {
		return wordCountInFile * Math.log10((double) (numberOfFiles) / dictionary.get(word));
	}

	@Override
	public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
		System.err.println("Error while accessing " + file.toAbsolutePath().toString());
		return FileVisitResult.CONTINUE;
	}
}

package hr.fer.zemris.java.console;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.Objects;
import java.util.Set;
import java.util.Scanner;

import hr.fer.zemris.java.vector.TFIDFVector;
import hr.fer.zemris.java.visitors.DictionaryCreator;
import hr.fer.zemris.java.visitors.UtilReader;
import hr.fer.zemris.java.visitors.VectorFactory;

/**
 * Program that offers a simple implementation of search engine using TFIDF
 * Vector algorithm.
 * 
 * @author Rafael Josip Penić
 */
public class SearchEngine {

	/**
	 * String representation of a file containing stop words of the croatian
	 * language
	 */
	private static final String STOP_WORDS_FILE = "./src/main/resources/hrvatski_stoprijeci.txt";

	/**
	 * Main program
	 * 
	 * @param args
	 *            command line arguments
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Dan je neispravan argumenata! Program će biti terminiran.");
			return;
		}

		Path origin = null;
		try {
			origin = Paths.get(args[0]);
		} catch (InvalidPathException ex) {
			System.out.println("Dani argument se ne može protumačiti kao valjana staza! Program će biti terminiran.");
			return;
		}

		// ------------------------------------------------

		System.out.println("Učitavam stop riječi...");
		Set<String> stopWordsSet = null;
		try {
			stopWordsSet = createStopWordsSet(STOP_WORDS_FILE);
		} catch (IOException ex) {
			System.out.println(
					"Došlo je do pogreške prilikom čitanja datoteke koja definira stop riječi! Program će biti terminiran.");
			return;
		}
		System.out.println("Stop riječi uspješno učitane.");
		System.out.println();

		// -----------------------------------------------

		System.out.println("Učitavam vokabular...");
		DictionaryCreator dictionaryCreator = new DictionaryCreator(stopWordsSet);
		try {
			Files.walkFileTree(origin, dictionaryCreator);
		} catch (IOException e) {
			System.out.println("Greška prilikom učitavanja riječnika! Program će biti terminiran.");
			return;
		}
		Map<String, Integer> dictionary = dictionaryCreator.getDictionary();
		System.out.println("Vokabular uspješno učitan.");
		System.out.println();

		// ------------------------------------------------------------

		System.out.println("Veličina rječnika je " + dictionary.size() + " riječi.");
		System.out.println();

		// ------------------------------------------------------------

		System.out.println("Učitavam vektore...");
		VectorFactory vecFactory = new VectorFactory(dictionary, dictionaryCreator.getNumberOfFiles(), stopWordsSet);
		try {
			Files.walkFileTree(origin, vecFactory);
		} catch (IOException ex) {
			System.out.println("Greška prilikom učitavanja vektora! Program će biti terminiran.");
			return;
		}
		System.out.println("Vektori uspješno učitani.");
		System.out.println();

		// --------------------------------------------------------------

		Scanner sc = new Scanner(System.in);
		boolean endLoop = false;
		Set<Map.Entry<Path, Double>> resultSet = null;

		while (!endLoop) {
			System.out.print("Enter command > ");
			String entry = sc.nextLine().trim();

			if (entry.isEmpty())
				continue;

			String[] entryArr = entry.split("\\s+");
			switch (entryArr[0]) {
			case "query":
				resultSet = processQuery(entry.substring(entryArr[0].length()), dictionary, vecFactory);
				System.out.println("Najboljih 10 rezultata: ");
				printResults(resultSet);
				break;

			case "type":
				if (entryArr.length < 2) {
					System.out.println(
							"Naredba 'type' očekuje jedan argument koji određuje koji će se rezultat ispisati.");
					break;
				}

				int i = 0;
				try {
					i = Integer.parseInt(entryArr[1]);
					printFile(resultSet, i);
				} catch (NumberFormatException ex) {
					System.out.println("Dani argument nije cijeli broj");
				} catch (IndexOutOfBoundsException ex) {
					System.out.println("Dani broj nije valjan.");
				} catch (IOException ex) {
					System.out.println("Greška prilikom čitanja datoteke.");

				}

				break;

			case "results":
				if (resultSet == null) {
					System.out.println("Rezultati nisu incijalizirani");
					break;
				}

				printResults(resultSet);
				break;

			case "exit":
				endLoop = true;
				break;

			default:
				System.out.println("Nepoznata naredba.");
				break;
			}

			System.out.println();
		}
		sc.close();
		System.out.println("Program je završen");
	}

	/**
	 * Prints files content on standard output. Used when type command is called.
	 * 
	 * @param resultSet
	 *            set containing search results
	 * @param i
	 *            index of the file that will be printed
	 * @throws IOException
	 *             in case of an error while reading from file
	 */
	private static void printFile(Set<Entry<Path, Double>> resultSet, int i) throws IOException {
		@SuppressWarnings("unchecked")
		Entry<Path, Double> entry = (Entry<Path, Double>) resultSet.toArray()[i];

		Path p = entry.getKey();

		System.out.println("-------------------------------------------");
		System.out.println("Dokument: " + p.toAbsolutePath());
		System.out.println("-------------------------------------------");
		System.out.println();

		List<String> lines = Files.readAllLines(p);

		for (String line : lines) {
			System.out.println(line);
		}

		System.out.println("-------------------------------------------");
	}

	/**
	 * 
	 * @param argumentString
	 *            part of the query containing arguments
	 * @param dictionary
	 *            map containing all words from data source that is searched
	 * @param vecFactory
	 *            vector factory that constructs vectors used when determining
	 *            "similarity"
	 * @return set containing results along with their similarity factor
	 */
	private static Set<Map.Entry<Path, Double>> processQuery(String argumentString, Map<String, Integer> dictionary,
			VectorFactory vecFactory) {
		Map<String, Integer> tempMap = new HashMap<>();
		List<String> tempList = UtilReader.readLineToList(argumentString);

		printQuery(tempList);

		// removing words that are not in dictionary
		Iterator<String> it = tempList.iterator();
		while (it.hasNext()) {
			if (!dictionary.containsKey(it.next())) {
				it.remove();
			}
		}

		// creating query vector
		UtilReader.readListToMap(tempList, tempMap);
		Map<String, Double> queryVector = new HashMap<>();
		tempMap.forEach((k, v) -> {
			queryVector.put(k, vecFactory.calculate(k, v));
		});
		TFIDFVector vec = new TFIDFVector(queryVector);

		// constructing result set
		Map<Path, Double> results = new HashMap<>();
		vecFactory.getVectors().forEach((k, v) -> {
			results.put(k, vec.multiply(v) / (vec.module() * v.module()));
		});

		List<Map.Entry<Path, Double>> listResult = results.entrySet().stream()
				.filter(e -> Math.abs(e.getValue() - 0) > 1E-4)
				.sorted((e1, e2) -> Double.compare(e2.getValue(), e1.getValue())).limit(10)
				.collect(Collectors.toList());

		return new LinkedHashSet<>(listResult);
	}

	/**
	 * Method that prints "units" of the query. Shows how program divided the query
	 * when searching for results.
	 * 
	 * @param queryElements
	 *            list containing query elements
	 */
	private static void printQuery(List<String> queryElements) {
		System.out.print("Query is: [");

		for (int i = 0; i < queryElements.size(); i++) {
			System.out.print(queryElements.get(i));
			if (i != queryElements.size() - 1) {
				System.out.print(", ");
			}
		}

		System.out.println("]");
	}

	/**
	 * Method that prints results from the given result set
	 * 
	 * @param resultSet
	 *            set containing results of the search that will be printed
	 */
	private static void printResults(Set<Map.Entry<Path, Double>> resultSet) {
		int i = 0;
		for (Map.Entry<Path, Double> entry : resultSet) {
			if (Math.abs(entry.getValue() - 0) < 1E-4)
				break;
			System.out.printf("[%2d] (%.4f) %s\n", i, entry.getValue(), entry.getKey().toAbsolutePath());
			i++;
		}
	}

	/**
	 * Constructs a set containing stop words that are read from the given file
	 * 
	 * @param stopWordsFilePath
	 *            string representation of the path leading to a file that contains
	 *            info about stop words
	 * @return set containing stop words of the language
	 * @throws IOException
	 *             in case of an error while reading stop words
	 */
	private static Set<String> createStopWordsSet(String stopWordsFilePath) throws IOException {
		Objects.requireNonNull(stopWordsFilePath, "Given path string reference is null.");

		Path stopWordsPath = Paths.get(stopWordsFilePath);

		List<String> wordsList = Files.readAllLines(stopWordsPath);
		return new HashSet<>(wordsList);
	}

}

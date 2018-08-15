package hr.fer.zemris.java.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.servlets.GlasanjeServlet.BandInfo;

/**
 * Utility class offering various static methods like loading result file,
 * definition file or merging them into one data structure.
 * 
 * @author Rafael Josip Penić
 *
 */
public class UtilLoader {

	/**
	 * Merges data found in results file and definition file. Method reads entries
	 * from these files and then constructs a list containing all needed info for
	 * further operations.
	 * 
	 * @param req
	 *            Servlet request object which makes getting servlet context
	 *            possible
	 * @param resultFilePath
	 *            string of the path of the file containing result entries
	 * @param definitionFilePath
	 *            string of the path of the file containing definition entries
	 * @return list containing definition entries paired with result entries
	 * @throws IOException
	 *             in case of an error while reading
	 */
	public static List<BandResult> mergeDefinitionAndResult(HttpServletRequest req, String resultFilePath,
			String definitionFilePath) throws IOException {

		Objects.requireNonNull(req, "Given servlet request reference is null.");
		Objects.requireNonNull(resultFilePath, "Given result file path string refernce is null.");
		Objects.requireNonNull(definitionFilePath, "Given definition file path string reference is null.");

		String fileName = req.getServletContext().getRealPath(resultFilePath);

		Path p = Paths.get(fileName);

		List<String> lines = Files.readAllLines(p);

		Map<String, Integer> resultPairs = createResultPairMap(lines);

		List<BandInfo> bands = loadDefinition(req, definitionFilePath);

		List<BandResult> bandResultsList = getResults(resultPairs, bands);
		bandResultsList.sort((v1, v2) -> v2.getVoteCount().compareTo(v1.getVoteCount()));

		return bandResultsList;
	}

	/**
	 * Constructs a list of BandResult objects from given result map(map that
	 * contains pairs id-score) and list that consists of BandInfo objects.
	 * 
	 * @param pairs
	 *            map containing band IDs paired with bands score
	 * @param bands
	 *            list containing basic info about bands(name, id and etc.)
	 * @return constructed list containing BandResult objects
	 */
	private static List<BandResult> getResults(Map<String, Integer> pairs, List<BandInfo> bands) {
		Objects.requireNonNull(pairs, "Given result pairs map reference is null.");
		Objects.requireNonNull(bands, "Given bands list reference is null.");

		List<BandResult> bandResults = new ArrayList<>();

		for (BandInfo band : bands) {
			Integer score = pairs.get(band.getId());

			if (score != null) {
				bandResults.add(new BandResult(band, score));
			} else {
				bandResults.add(new BandResult(band, 0));
			}
		}

		return bandResults;
	}

	/**
	 * Loads definition of bands and constructs a list from loaded info
	 * 
	 * @param req
	 *            servlet request used for getting the servlet context
	 * @param definitionFilePath
	 *            string of a path with band definitions
	 * @return list containing info about each loaded band
	 * @throws IOException
	 *             in case of an error while reading
	 */
	public static List<BandInfo> loadDefinition(HttpServletRequest req, String definitionFilePath) throws IOException {
		Objects.requireNonNull(req, "Given servlet request reference is null.");
		Objects.requireNonNull(definitionFilePath, "Given file path string reference is null.");

		String fileName = req.getServletContext().getRealPath(definitionFilePath);

		Path p = Paths.get(fileName);

		List<String> fileLines = Files.readAllLines(p);
		List<BandInfo> bands = new ArrayList<>();

		for (String line : fileLines) {
			if (line.trim().isEmpty())
				continue;// skips empty lines
			String[] splittedArray = line.trim().split("\t");
			if (splittedArray.length < 3)
				continue;// skips lines that have less than 3 arguments

			bands.add(new BandInfo(splittedArray[1], splittedArray[2], splittedArray[0]));
		}

		return bands;
	}

	/**
	 * Constructs a result map from the lines of the score file
	 * 
	 * @param lines
	 *            lines of the file containing info about the vote score
	 * @return map containing pairs of IDs and the score of the band with that id
	 */
	private static Map<String, Integer> createResultPairMap(List<String> lines) {
		Objects.requireNonNull(lines, "Given list reference is null.");

		Map<String, Integer> resultPairs = new HashMap<>();

		for (String line : lines) {
			if (line.trim().isEmpty())
				continue;

			String[] arr = line.trim().split("\t");

			if (arr.length < 2)
				continue;

			try {
				resultPairs.put(arr[0], Integer.parseInt(arr[1]));
			} catch (NumberFormatException ex) {
				resultPairs.put(arr[0], 0);
			}
		}

		return resultPairs;
	}

	/**
	 * Class representing a pair that consists of a band(basic informations about
	 * band) and its vote score.
	 * 
	 * @author Rafael Josip Penić
	 *
	 */
	public static class BandResult {

		/**
		 * Contains basic informations about band(etc. name)
		 */
		private BandInfo band;

		/**
		 * Number representing how many votes band received
		 */
		private Integer voteCount;

		/**
		 * Constructor for BandResult objects
		 * 
		 * @param band
		 *            basic information about the band
		 * @param voteCount
		 *            bands score
		 */
		public BandResult(BandInfo band, Integer voteCount) {
			Objects.requireNonNull(band, "Given band reference is null.");
			Objects.requireNonNull(voteCount, "Given vote count reference is null");

			this.band = band;
			this.voteCount = voteCount;
		}

		/**
		 * Getter for band info
		 * 
		 * @return band info
		 */
		public BandInfo getBand() {
			return band;
		}

		/**
		 * Getter for number of votes
		 * 
		 * @return number of votes
		 */
		public Integer getVoteCount() {
			return voteCount;
		}
	}
}

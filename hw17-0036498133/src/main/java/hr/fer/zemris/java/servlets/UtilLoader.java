package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Utility class used when loading football player entries from the files. It
 * offers methods that can extract various data from the player database like
 * tags, picture names and etc.
 * 
 * @author Rafael Josip Penić
 *
 */
public class UtilLoader {

	/**
	 * Extract football player entries from the descriptor
	 * 
	 * @return list containing football player entries
	 * @throws IOException
	 *             in case of an error while reading the descriptor
	 */
	public static List<FootballPlayerEntry> getPlayerEntries(String pS) throws IOException {
		Path p = Paths.get(pS);
		List<String> lines = Files.readAllLines(p);

		List<FootballPlayerEntry> entries = new ArrayList<>();

		for (int i = 0; i < lines.size(); i++) {
			String pictureName;
			String title;
			List<String> tags = new ArrayList<>();

			try {
				pictureName = lines.get(i++).trim();
				title = lines.get(i++).trim();

				List<String> tempList = Arrays.asList(lines.get(i).trim().split(","));

				tempList.forEach(v -> tags.add(v.trim()));
			} catch (IndexOutOfBoundsException ex) {
				throw new RuntimeException("Error while reading descriptor file.");
			}

			if (pictureName.isEmpty() || title.isEmpty()) {
				throw new RuntimeException("Descriptor file is not formatted correctly.");
			}

			entries.add(new FootballPlayerEntry(pictureName, title, tags));
		}

		return entries;
	}

	/**
	 * Extracts tags from the player "database" and collects them into a set
	 * 
	 * @return set containing all tags from the data source
	 * @throws IOException
	 *             in case of an error while reading tags
	 */
	public static Set<String> getTags(String pS) throws IOException {
		List<FootballPlayerEntry> players = getPlayerEntries(pS);
		Set<String> resultSet = new HashSet<>();

		for (FootballPlayerEntry player : players) {
			resultSet.addAll(player.getTags());
		}

		return resultSet;
	}

	/**
	 * Extracts picture names of football player entries that contain the given tag
	 * 
	 * @param tag
	 *            tag that will be used as a filter
	 * @return list of image names that contain the given tag
	 * @throws IOException
	 *             in case of an error while reading through the descriptor
	 */
	public static List<String> getPictureNames(String tag, String pS) throws IOException {
		List<FootballPlayerEntry> players = getPlayerEntries(pS);

		List<String> names = new ArrayList<>();

		for (FootballPlayerEntry player : players) {
			if (player.getTags().contains(tag)) {
				names.add(player.getPictureName());
			}
		}

		return names;
	}

	/**
	 * Gets football player entry with the given image name
	 * 
	 * @param picName
	 *            name of the corresponding image
	 * @return football player associated with the image with the given name
	 * @throws IOException
	 *             in case of an error while reading descriptor
	 */
	public static FootballPlayerEntry getPlayerEntryWithPicName(String picName, String pS) throws IOException {
		List<FootballPlayerEntry> players = getPlayerEntries(pS);

		List<FootballPlayerEntry> tempList = players.stream().filter(entry -> entry.getPictureName().equals(picName))
				.collect(Collectors.toList());

		if (tempList.isEmpty())
			return null;

		return tempList.get(0);
	}

	/**
	 * Class representing a single football player entry with some basic info about
	 * it
	 * 
	 * @author Rafael Josip Penić
	 *
	 */
	public static class FootballPlayerEntry {
		/**
		 * Name of the image associated with the player
		 */
		private String pictureName;

		/**
		 * Title of the image
		 */
		private String title;

		/**
		 * Tags connected to this player
		 */
		private List<String> tags;

		/**
		 * Constructor
		 * 
		 * @param pictureName
		 *            name of the image
		 * @param title
		 *            title of the image
		 * @param tags
		 *            list containing image tags
		 */
		public FootballPlayerEntry(String pictureName, String title, List<String> tags) {
			Objects.requireNonNull(pictureName, "Given picture name string reference is null.");
			Objects.requireNonNull(title, "Given title string reference is null.");
			Objects.requireNonNull(tags, "Given tags list reference is null.");

			this.pictureName = pictureName;
			this.title = title;
			this.tags = tags;
		}

		/**
		 * Getter for image name
		 * 
		 * @return image name
		 */
		public String getPictureName() {
			return pictureName;
		}

		/**
		 * Getter for image title
		 * 
		 * @return image title
		 */
		public String getTitle() {
			return title;
		}

		/**
		 * Getter for player tags
		 * 
		 * @return player tags
		 */
		public List<String> getTags() {
			return tags;
		}
	}
}

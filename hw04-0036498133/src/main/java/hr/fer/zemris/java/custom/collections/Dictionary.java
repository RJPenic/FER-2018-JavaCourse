package hr.fer.zemris.java.custom.collections;

/**
 * Class that under the certain key remembers certain value.
 * 
 * @author Rafael Josip Penić
 * @version 1.0
 *
 */
public class Dictionary {

	/**
	 * Collection where dictionary entries are stored.
	 */
	private ArrayIndexedCollection entries;

	/**
	 * Class that represents a single directory entry.
	 * 
	 * @author Rafael Josip Penić
	 *
	 */
	private class Entry {
		/**
		 * Entry key.
		 */
		Object key;

		/**
		 * Value that is stored under the certain key.
		 */
		Object value;

		/**
		 * Constructor for dictionary entry.
		 * 
		 * @param key
		 *            key of the entry that is constructed.
		 * @param value
		 *            value stored in the entry.
		 * @throws IllegalArgumentException
		 *             when the key is null.
		 */
		public Entry(Object key, Object value) {
			if (key == null)
				throw new IllegalArgumentException("Key cannot be null.");

			this.key = key;
			this.value = value;
		}
	}

	/**
	 * Constructor which allocates entry collection.
	 */
	public Dictionary() {
		entries = new ArrayIndexedCollection();
	}

	/**
	 * Method that reveals if the dictionary is empty or not
	 * 
	 * @return true if the dictionary is empty, false otherwise.
	 */
	public boolean isEmpty() {
		return entries.isEmpty();
	}

	/**
	 * Method that returns size of the dictionary(number of entries).
	 * 
	 * @return number of entries in dictionary.
	 */
	public int size() {
		return entries.size();
	}

	/**
	 * Removes all entries from the dictionary.
	 */
	public void clear() {
		entries.clear();
	}

	/**
	 * Puts new entry with the given key and value into the dictionary. If the given
	 * key is already present, value is updated with the given one.
	 * 
	 * @param key
	 *            key of the entry that will be added into the dictionary(if not
	 *            already present)
	 * @param value
	 *            value of the entry that will be added
	 */
	public void put(Object key, Object value) {
		if (key == null)
			throw new IllegalArgumentException("Cannot put a key that is null in the dictionary.");

		Object[] tempArray = entries.toArray();

		int i;
		for (i = 0; i < tempArray.length; i++) {
			if (((Entry) tempArray[i]).key == key)
				break;
		}

		if (i != tempArray.length) {
			entries.remove(i);
		}

		entries.add(new Entry(key, value));
	}

	/**
	 * Returns value stored under the given key.
	 * 
	 * @param key
	 *            key of the entry from which the value will be "taken".
	 * @return value of the entry with the given key.
	 */
	public Object get(Object key) {
		Object[] tempArray = entries.toArray();

		for (int i = 0; i < tempArray.length; i++) {
			if (((Entry) tempArray[i]).key.equals(key))
				return ((Entry) tempArray[i]).value;
		}

		return null;
	}
}

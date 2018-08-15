package hr.fer.zemris.java.hw05.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Class that implements simple hashtable which provides methods for quick and
 * efficient getting of entries
 * 
 * @author Rafael Josip Penić
 * @version 1.0
 *
 * @param <K>
 *            parameter for entry key
 * @param <V>
 *            parameter for entry value
 */
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K, V>> {

	private static final int DEFAULT_TABLE_SIZE = 16;
	private static final double SIZE_TOLERANCE = 0.75;

	/**
	 * Array in which the heads of the lists, which store entries, are stored
	 */
	private TableEntry<K, V>[] table;

	/**
	 * Capacity of the hashtable
	 */
	private int capacity;

	/**
	 * Modification counter. Whenever there is a change made on the hashtable, this
	 * variable is incremented by one
	 */
	private int modificationCount = 0;

	/**
	 * Class that represents single table entry which contains key, value and
	 * reference to other entry
	 * 
	 * @author Rafael Josip Penić
	 *
	 * @param <K>
	 *            parameter for entry key
	 * @param <V>
	 *            parameter for entry value
	 */
	public static class TableEntry<K, V> {

		/**
		 * Entry key
		 */
		private K key;

		/**
		 * Entry value
		 */
		private V value;

		/**
		 * Reference to next entry
		 */
		private TableEntry<K, V> next;

		/**
		 * Constructor which allocates new entry
		 * 
		 * @param key
		 *            key of the entry that is constructed
		 * @param value
		 *            value of the entry that is constructed
		 * @param next
		 *            reference to next entry
		 */
		public TableEntry(K key, V value, TableEntry<K, V> next) {
			Objects.requireNonNull(key, "Table entry key is not allowed to be null.");
			this.key = key;
			this.value = value;
			this.next = next;
		}

		/**
		 * Getter for entry key
		 * 
		 * @return entry key
		 */
		public K getKey() {
			return key;
		}

		/**
		 * Getter for entry value
		 * 
		 * @return entry value
		 */
		public V getValue() {
			return value;
		}

		/**
		 * Setter for entry value
		 * 
		 * @param value
		 *            new value of the entry
		 */
		public void setValue(V value) {
			this.value = value;
		}
	}

	/**
	 * Class that implements Iterator interface and helps with useful methods needed
	 * when changing hash table while iterating
	 * 
	 * @author Rafael Josip Penić
	 *
	 */
	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K, V>> {

		/**
		 * Current entry "observed" by the iterator
		 */
		private TableEntry<K, V> currentEntry;

		/**
		 * Number of modifications made when the iterator was created
		 */
		private int modificationsWhenCreated;

		/**
		 * Constructor for the hashtable iterator
		 * 
		 * @param modificationsWhenCreated
		 *            number of modifications made in time of creating the iterator
		 */
		public IteratorImpl(int modificationsWhenCreated) {
			this.modificationsWhenCreated = modificationsWhenCreated;
		}

		@Override
		public boolean hasNext() {
			if (modificationsWhenCreated != modificationCount)
				throw new ConcurrentModificationException("Hashtable has been changed \"outside\" of iterator.");

			if (currentEntry == null) {
				for (int i = 0; i < capacity; i++) {
					if (table[i] != null)
						return true;
				}
			} else {
				if (currentEntry.next != null)
					return true;

				for (int i = calculateSlot(currentEntry.key) + 1; i < capacity; i++) {
					if (table[i] != null)
						return true;
				}
			}

			return false;
		}

		@Override
		public SimpleHashtable.TableEntry<K, V> next() {
			if (modificationsWhenCreated != modificationCount)
				throw new ConcurrentModificationException("Hashtable has been changed \"outside\" of iterator.");

			if(!hasNext())
				throw new NoSuchElementException("There are no more elements to read.");
			
			if (currentEntry == null) {
				for (int i = 0; i < capacity; i++) {
					if (table[i] != null) {
						currentEntry = table[i];
						break;
					}
				}
			} else {
				
				if (currentEntry.next != null) {
					currentEntry = currentEntry.next;
				} else {
					for (int i = calculateSlot(currentEntry.key) + 1; i < capacity; i++) {
						if (table[i] != null) {
							currentEntry = table[i];
							break;
						}
					}
				}
			}

			return currentEntry;
		}

		@Override
		public void remove() {
			if (modificationsWhenCreated != modificationCount)
				throw new ConcurrentModificationException("Hashtable has been changed \"outside\" of iterator.");
			if (currentEntry == null)
				throw new IllegalStateException("Before removing you must read next element.");
			if (!(SimpleHashtable.this.containsKey(currentEntry.key)))
				throw new IllegalStateException("Remove method has already been called for this element.");

			modificationsWhenCreated++;
			SimpleHashtable.this.remove(currentEntry.key);
		}
	}

	/**
	 * Default hash table constructor
	 */
	public SimpleHashtable() {
		this(DEFAULT_TABLE_SIZE);
	}

	/**
	 * Constructor for hash table which allocates the hash table with the given
	 * capacity
	 * 
	 * @param capacity
	 *            capacity of the table array
	 * @throws IllegalArgumentException
	 *             when the given capacity is lesser than 1
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int capacity) {
		if (capacity < 1)
			throw new IllegalArgumentException("Capacity given in constructor must be greater or equal to one.");

		int i;
		for (i = 1; i < capacity; i *= 2);

		this.capacity = i;
		table = (TableEntry<K, V>[]) new TableEntry[i];
	}

	/**
	 * Method that puts entry with the given key and value into the hash table
	 * 
	 * @param key
	 *            key of the entry that will be added into hash table
	 * @param value
	 *            value of the entry that will be added into hash table
	 * 
	 * @throws NullPointerException
	 *             when the given key is null
	 */
	public void put(K key, V value) {
		Objects.requireNonNull(key, "Entry key cannot be null.");

		if (!this.containsKey(key)) {
			checkSizeAndResizeTable();
		}

		int slot = calculateSlot(key);

		if (table[slot] == null) {
			modificationCount++;
			table[slot] = new TableEntry<K, V>(key, value, null);
		} else {
			TableEntry<K, V> entry = table[slot];

			for (entry = table[slot]; entry.next != null; entry = entry.next) {
				if (entry.key.equals(key)) {
					entry.value = value;
					break;
				}
			}

			if (entry.next == null) {
				if (entry.key.equals(key)) {
					entry.value = value;
				} else {
					modificationCount++;
					entry.next = new TableEntry<K, V>(key, value, null);
				}
			}

		}
	}

	/**
	 * Gets the value stored under the given key
	 * 
	 * @param key
	 *            key which value will be returned
	 * @return value stored under the given key or null if the given key is not
	 *         present in the table
	 */
	public V get(Object key) {
		if (key == null)
			return null;

		for (TableEntry<K, V> entry = table[calculateSlot(key)]; entry != null; entry = entry.next) {
			if (entry.key.equals(key))
				return entry.value;
		}

		return null;
	}

	/**
	 * Method that calculates and returns number of entries in hash table
	 * 
	 * @return size of the hash table
	 */
	public int size() {
		int counter = 0;

		for (int i = 0; i < capacity; i++) {
			for (TableEntry<K, V> entry = table[i]; entry != null; entry = entry.next) {
				counter++;
			}
		}

		return counter;
	}

	/**
	 * Method that says if the certain key is present in table or not
	 * 
	 * @param key
	 *            key which presence is "questioned"
	 * @return true if the key is present in the hash table, false otherwise
	 */
	public boolean containsKey(Object key) {
		if (key == null)
			return false;

		for (TableEntry<K, V> entry = table[calculateSlot(key)]; entry != null; entry = entry.next) {
			if (entry.key.equals(key))
				return true;
		}

		return false;
	}

	/**
	 * Method that says if the certain value is present in the hash table or not
	 * 
	 * @param value
	 *            value which presence is "questioned"
	 * @return true if the value is present in the table and false otherwise
	 */
	public boolean containsValue(Object value) {

		for (int i = 0; i < capacity; i++) {
			for (TableEntry<K, V> entry = table[i]; entry != null; entry = entry.next) {
				if (entry.value.equals(value))
					return true;
			}
		}

		return false;
	}

	/**
	 * Method that says if the table is empty or not
	 * 
	 * @return true if the hash table is empty, false otherwise
	 */
	public boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * Removes entry with the given key from the hash table
	 * 
	 * @param key
	 *            key of the entry that will be removed
	 */
	public void remove(Object key) {
		int slot = calculateSlot(key);

		if (table[slot] != null) {
			if (table[slot].key.equals(key)) {
				modificationCount++;
				table[slot] = table[slot].next;
			} else {
				for (TableEntry<K, V> entry = table[slot]; entry.next != null; entry = entry.next) {
					if (entry.next.key.equals(key)) {
						modificationCount++;
						entry.next = entry.next.next;
						break;
					}
				}
			}
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("[");

		for (int i = 0; i < capacity; i++) {
			for (TableEntry<K, V> entry = table[i]; entry != null; entry = entry.next) {
				sb.append(entry.key + "=" + entry.value + ((i == capacity - 1 && entry.next == null) ? "" : ", "));
			}
		}

		sb.append("]");
		return sb.toString();
	}

	/**
	 * Method that removes all entries from the table
	 */
	public void clear() {
		for (int i = 0; i < capacity; i++) {
			table[i] = null;
		}
	}

	/**
	 * Method that calculates hash "slot" for the entry with the given key
	 * 
	 * @param key
	 *            key of the entry which "hash slot" is calculated
	 * @return integer value that represents the calculated slot
	 */
	private int calculateSlot(Object key) {
		return Math.abs(key.hashCode()) % capacity;
	}

	/**
	 * Method that loads the hash table with the values from the given entry array
	 * 
	 * @param table
	 *            array from which the data will be added into the hash table
	 */
	private void importData(TableEntry<K, V>[] table) {
		for (int i = 0; i < table.length; i++) {
			for (TableEntry<K, V> entry = table[i]; entry != null; entry = entry.next) {
				put(entry.key, entry.value);
			}
		}
	}

	/**
	 * Method that checks if the hash table exceeded certain size tolerance and if
	 * that proves to be true, it resizes the hash table to the capacity two times
	 * bigger than the previous one.
	 */
	@SuppressWarnings("unchecked")
	private void checkSizeAndResizeTable() {
		if (size() + 1 >= SIZE_TOLERANCE * capacity) {
			modificationCount++;
			capacity *= 2;
			TableEntry<K, V>[] tempTable = table;
			table = (TableEntry<K, V>[]) new TableEntry[capacity];
			importData(tempTable);
		}
	}

	@Override
	public Iterator<TableEntry<K, V>> iterator() {
		return new IteratorImpl(modificationCount);
	}
	
}

package hr.fer.zemris.java.custom.collections;

/**
 * Inherits class Collection and it is realized with the array.
 * 
 * @author Rafael Josip Penić
 * @version 1.0
 *
 */
public class ArrayIndexedCollection extends Collection {

	/**
	 * Default collection capacity that will be allocated in case of default
	 * constructor usage.
	 */
	public static final int DEFAULT_COLLECTION_CAPACITY = 16;

	/**
	 * Factor with which will the current capacity be multiplied in case of need for
	 * a bigger capacity
	 */
	public static final int RESIZING_FACTOR = 2;

	/**
	 * Value that will be returned in case of an error in the method
	 */
	public static final int ERROR_RETURN_VALUE = -1;

	/**
	 * Size of the collection.
	 */
	private int size;

	/**
	 * Capacity of the collection.
	 */
	private int capacity;

	/**
	 * Array in which collections elements are stored.
	 */
	private Object[] elements;

	/**
	 * Default constructor which allocates the array with the size of 16.
	 */
	public ArrayIndexedCollection() {
		this(DEFAULT_COLLECTION_CAPACITY);
	}

	/**
	 * Constructor which allocates the array with the size of the given capacity.
	 * 
	 * @param initialCapacity
	 *            size of the array in which the collection elements will be stored.
	 */
	public ArrayIndexedCollection(int initialCapacity) {
		if (initialCapacity < FIRST_POSITION + 1) {
			throw new IllegalArgumentException("Capacity of collection cannot be lower than one.");
		}

		capacity = initialCapacity;
		elements = new Object[initialCapacity];
	}

	/**
	 * Constructor which allocates the array and adds all elements of the given
	 * collection into the collection. Size of the array is equal to the size of the
	 * give collection.
	 * 
	 * @param otherColl
	 *            collection which elements will be added in the collection
	 */
	public ArrayIndexedCollection(Collection otherColl) {
		this(otherColl, otherColl.size());
	}

	/**
	 * Constructor which allocates the array and adds all elements of the given
	 * collection into the collection. Size of the array is equal to the given
	 * capacity(if the capacity is smaller or equal to the given collection size)
	 * 
	 * @param otherColl
	 *            collection which elements will be added in the collection
	 * @param initialCapacity
	 *            size of the array that will be allocated(if size of the otherColl
	 *            isn't bigger than initialCapacity)
	 */
	public ArrayIndexedCollection(Collection otherColl, int initialCapacity) {
		if (otherColl == null) {
			throw new NullPointerException("Give collection is null.");
		}

		if (initialCapacity < otherColl.size()) {
			elements = new Object[otherColl.size()];
			capacity = otherColl.size();
		} else {
			elements = new Object[initialCapacity];
			capacity = initialCapacity;
		}

		this.addAll(otherColl);
	}

	@Override
	public void add(Object value) {
		if (value == null) {
			throw new NullPointerException("It is not allowed to add null into the collection.");
		}

		if (size == capacity) {
			ArrayIndexedCollection tempColl = new ArrayIndexedCollection(this, capacity);
			capacity *= RESIZING_FACTOR;
			this.clear();
			elements = new Object[capacity];
			this.addAll(tempColl);
		}

		elements[size] = value;
		size++;
	}

	/**
	 * Gets the object from the collection on the given index.
	 * 
	 * @param index
	 *            index in the collection of the object that will be returned
	 * @return object on the given index in the collection
	 * @throws IndexOutOfBoundsException
	 *             if the index is out of allowed interval
	 */
	public Object get(int index) {
		if (index < FIRST_POSITION || index > (size - 1)) {
			throw new IndexOutOfBoundsException(
					"Index must not be negative or bigger than collections size decremented by one.");
		}

		return elements[index];
	}

	@Override
	public void clear() {
		for (int i = FIRST_POSITION; i < size; i++) {
			elements[i] = null;
		}

		size = FIRST_POSITION;
	}

	/**
	 * Inserts given object on the given position.
	 * 
	 * @param value
	 *            object that will be inserted
	 * @param position
	 *            index on which the given object will be inserted
	 * @throws IndexOutOfBoundsException
	 *             if the given position is out of allowed interval.
	 */
	public void insert(Object value, int position) {
		if (position < FIRST_POSITION || position > size) {
			throw new IndexOutOfBoundsException(
					"Position in which object will be inserted cannot be negative nor bigger than collection's size.");
		}

		ArrayIndexedCollection tempColl = new ArrayIndexedCollection(capacity);

		for (int i = FIRST_POSITION; i < position; i++) {
			tempColl.add(this.get(i));
		}

		tempColl.add(value);

		for (int i = position; i < size; i++) {
			tempColl.add(this.get(i));
		}

		this.clear();
		this.addAll(tempColl);
	}

	/**
	 * Method which finds index of the given object.
	 * 
	 * @param value
	 *            Object which collection index will be returned(if present in the
	 *            collection)
	 * @return Index of the give object in the collection or -1 if the collection
	 *         doesn't contain given object.
	 */
	public int indexOf(Object value) {
		for (int i = FIRST_POSITION; i < size; i++) {
			if (value.equals(elements[i])) {
				return i;
			}
		}

		return ERROR_RETURN_VALUE;
	}

	/**
	 * Removes object on the given index from the collection.
	 * 
	 * @param index
	 *            index from which the object will be removed from the collection.
	 * 
	 * @throws IndexOutOfBoundsException
	 *             if the given index is negative or bigger than collections size
	 *             decremented by one.
	 */
	public void remove(int index) {
		if (index < FIRST_POSITION || index > size - 1) {
			throw new IndexOutOfBoundsException(
					"Index for removal cannot be negative or bigger than collections size decremented by one.");
		}

		for (int i = index + 1; i < size; i++) {
			elements[i - 1] = elements[i];
		}

		elements[size - 1] = null;

		size--;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean contains(Object value) {
		return this.indexOf(value) != ERROR_RETURN_VALUE;
	}

	@Override
	public Object[] toArray() {
		Object[] tempArray = new Object[size];
		System.arraycopy(elements, 0, tempArray, 0, tempArray.length);
		return tempArray;
	}

	@Override
	public void forEach(Processor processor) {
		for (int i = FIRST_POSITION; i < size; i++) {
			processor.process(elements[i]);
		}
	}
}

package hr.fer.zemris.java.custom.collections;

/**
 * This class extends class Collection and it is realized with the linked list
 * 
 * @author Rafael Josip Penić
 * @version 1.0
 *
 */
public class LinkedListIndexedCollection extends Collection {

	/**
	 * Factor that will be used when finding out if the searched index is closer to
	 * the first or the last list node.
	 */
	public static final int PARTITION_FACTOR = 2;

	/**
	 * Value that will be returned in case of an error in the method.
	 */
	public static final int ERROR_RETURN_VALUE = -1;

	/**
	 * Size of the collection.
	 */
	private int size;

	/**
	 * Reference on the first(firstly added) node of the list.
	 */
	private ListNode first;

	/**
	 * Reference on the last(lastly added) node of the list.
	 */
	private ListNode last;

	/**
	 * Class that defines list node which consists of nodes value and references on
	 * previous and next node.
	 *
	 */
	private static class ListNode {
		/**
		 * Object which the node contains.
		 */
		Object value;

		/**
		 * Reference on the previous node in the list.
		 */
		ListNode previous;

		/**
		 * Reference on the next node in the list.
		 */
		ListNode next;
	}

	/**
	 * Default constructor which initializes list in which collection elements will
	 * be stored.
	 */
	public LinkedListIndexedCollection() {
		first = null;
		last = null;
	}

	/**
	 * Constructor which will initialize the collection and add all elements of the
	 * given collection in it.
	 * 
	 * @param other
	 *            Collection which elements will be added in the collection.
	 */
	public LinkedListIndexedCollection(Collection other) {
		this();
		this.addAll(other);
	}

	@Override
	public void add(Object value) {
		if (value == null) {
			throw new NullPointerException("It is not allowed to add null into indexed linkedlist collection.");
		}

		ListNode addedNode = new ListNode();
		addedNode.value = value;

		if (first != null) {
			last.next = addedNode;
			addedNode.previous = last;
			last = addedNode;
		} else {
			first = addedNode;
			last = addedNode;
		}

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
		if (index < FIRST_POSITION || index > size - 1) {
			throw new IndexOutOfBoundsException(
					"Given index must not be negative nor bigger than the collection's size decremented by one.");
		}

		return getNode(index).value;
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
					"Given index must not be negative nor bigger than the collection's size.");
		}

		if (position == size) {
			this.add(value);
		} else {
			ListNode tempNode = getNode(position);
			ListNode insertedNode = new ListNode();

			insertedNode.value = value;
			insertedNode.next = tempNode;
			insertedNode.previous = tempNode.previous;

			if (position != FIRST_POSITION) {
				tempNode.previous.next = insertedNode;
			} else {
				first = insertedNode;
			}

			tempNode.previous = insertedNode;
		}

		size++;
	}

	/**
	 * Returns list node that is on give index in the list.
	 * 
	 * @param index
	 *            index of the node that will be returned
	 * @return reference on the list node on the given index
	 * @throws IndexOutOfBoundsException
	 *             if the given index is out of allowed interval
	 */
	private ListNode getNode(int index) {
		if (index < FIRST_POSITION || index > size - 1) {
			throw new IndexOutOfBoundsException(
					"Given index must not be negative nor bigger than the collection's size decremented by one.");
		}

		ListNode tempNode = new ListNode();

		if (index < size / PARTITION_FACTOR) {
			int i = FIRST_POSITION;

			for (tempNode = first; tempNode != null; tempNode = tempNode.next) {
				if (i == index) {
					break;
				}

				i++;
			}
		} else {
			int i = size - 1;

			for (tempNode = last; tempNode != null; tempNode = tempNode.previous) {
				if (i == index) {
					break;
				}

				i--;
			}
		}

		return tempNode;
	}

	@Override
	public void clear() {
		first = null;
		last = null;
		size = FIRST_POSITION;
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
		int i = FIRST_POSITION;

		for (ListNode tempNode = first; tempNode != null; tempNode = tempNode.next) {
			if (tempNode.value.equals(value)) {
				return i;
			}

			i++;
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
					"Given index must not be negative nor bigger than the collection's size decremented by one.");
		}

		ListNode tempNode = getNode(index);

		if (index != FIRST_POSITION) {
			tempNode.previous.next = tempNode.next;
		} else {
			first = tempNode.next;
		}

		if (index != size - 1) {
			tempNode.next.previous = tempNode.previous;
		} else {
			last = tempNode.previous;
		}

		size--;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean contains(Object value) {
		return indexOf(value) != ERROR_RETURN_VALUE;
	}

	@Override
	public boolean remove(Object value) {
		if (!contains(value))
			return false;

		remove(indexOf(value));

		return true;
	}

	@Override
	public Object[] toArray() {
		Object[] tempArray = new Object[size];
		int i = FIRST_POSITION;

		for (ListNode tempNode = first; tempNode != null; tempNode = tempNode.next) {
			tempArray[i] = tempNode.value;
			i++;
		}

		return tempArray;
	}

	@Override
	public void forEach(Processor processor) {
		for (ListNode tempNode = first; tempNode != null; tempNode = tempNode.next) {
			processor.process(tempNode.value);
		}
	}
}

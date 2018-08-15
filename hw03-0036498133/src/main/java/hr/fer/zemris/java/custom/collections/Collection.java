package hr.fer.zemris.java.custom.collections;

/**
 * Class which defines collection in which objects can be stored and contains
 * some basic methods for operations with collections
 * 
 * @author Rafael Josip Penić
 * @version 1.0
 *
 */
public class Collection {

	/**
	 * Index of the first object in the collection.
	 */
	public static final int FIRST_POSITION = 0;

	/**
	 * Default constructor without any arguments.
	 */
	protected Collection() {
	}

	/**
	 * Method which tells user if the collection contains any elements or not.
	 * 
	 * @return true if the collection is empty and false otherwise.
	 */
	public boolean isEmpty() {
		if (this.size() == FIRST_POSITION) {
			return true;
		}
		return false;
	}

	/**
	 * Method which returns number of elements in the collection.
	 * 
	 * @return size of collection
	 */
	public int size() {
		return FIRST_POSITION;
	}

	/**
	 * Adds given object into the collection.
	 * 
	 * @param value
	 *            object that will be inserted into the collection
	 */
	public void add(Object value) {
	}

	/**
	 * Finds out if the given object is present in the collection.
	 * 
	 * @param value
	 *            object which presence will be "questioned".
	 * @return true if the given object is present in collection and false
	 *         otherwise.
	 */
	public boolean contains(Object value) {
		return false;
	}

	/**
	 * Removes given object from the collection.
	 * 
	 * @param value
	 *            object which will be removed from the collection(if present)
	 * @return true if the given object is successfully remove from the collection
	 *         and false otherwise
	 */
	public boolean remove(Object value) {
		return false;
	}

	/**
	 * Allocates new array with size equals to the size of this collections, fills
	 * it with collection content and returns the array.
	 * 
	 * @return array that contains all objects present in collection
	 */
	public Object[] toArray() {
		throw new UnsupportedOperationException("Undefined what should program do.");
	}

	/**
	 * Calls process method on each element of the collection.
	 * 
	 * @param processor
	 *            Processor which method process is called on each element of
	 *            collection
	 */
	public void forEach(Processor processor) {
	}

	/**
	 * Adds all elements from the given other collection to the collection.
	 * 
	 * @param other
	 *            collection which elements will be added to the collection
	 */
	public void addAll(Collection other) {
		class ProcessorExtended extends Processor {

			public void process(Collection coll) {
				Object[] tempArray = coll.toArray();

				for (int i = FIRST_POSITION; i < coll.size(); i++) {
					Collection.this.add(tempArray[i]);
				}

				other.forEach(this);
			}
		}

		ProcessorExtended proc = new ProcessorExtended();
		proc.process(other);
	}

	/**
	 * Removes all elements from the collection.
	 */
	public void clear() {
	}
}

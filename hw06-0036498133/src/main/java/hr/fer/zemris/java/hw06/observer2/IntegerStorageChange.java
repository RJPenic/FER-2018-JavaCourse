package hr.fer.zemris.java.hw06.observer2;

/**
 * Class that represents a change in IntegerStorage object
 * 
 * @author Rafael Josip Penić
 *
 */
public class IntegerStorageChange {

	/**
	 * Reference to a IntegerStorage in which the change occurred
	 */
	private IntegerStorage intStorage;

	/**
	 * Value of the stored value before the change
	 */
	private Integer oldValue;

	/**
	 * Value of the stored value after the change
	 */
	private Integer newValue;

	/**
	 * Constructor for IntegerStorageChange objects
	 * 
	 * @param intStorage
	 *            reference to a IntegerStorage object
	 * @param oldValue
	 *            value before the change
	 * @param newValue
	 *            value after the change
	 */
	public IntegerStorageChange(IntegerStorage intStorage, Integer oldValue, Integer newValue) {
		super();
		this.intStorage = intStorage;
		this.oldValue = oldValue;
		this.newValue = newValue;
	}

	/**
	 * Getter for IntegerStorage reference
	 * 
	 * @return reference to IntegerStorage where change of value happened
	 */
	public IntegerStorage getIntStorage() {
		return intStorage;
	}

	/**
	 * Getter for the old value
	 * 
	 * @return value before the change
	 */
	public Integer getOldValue() {
		return oldValue;
	}

	/**
	 * Getter for the new value
	 * 
	 * @return value after the change
	 */
	public Integer getNewValue() {
		return newValue;
	}

}

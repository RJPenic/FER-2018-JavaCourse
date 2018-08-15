package hr.fer.zemris.java.hw06.observer2;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents a storage unit which stores int value and also has an
 * observers list in which it can add or remove observers.
 * 
 * @author Rafael Josip Penić
 *
 */
public class IntegerStorage {
	
	/**
	 * Integer value that is stored in the storage
	 */
	private int value;
	
	/**
	 * List where observers are stored
	 */
	private List<IntegerStorageObserver> observers; // use ArrayList here!!!
	
	/**
	 * Constructor for IntegerStorage objects
	 * 
	 * @param initialValue
	 *            initial value of the stored value
	 */
	public IntegerStorage(int initialValue) {
		this.value = initialValue;
	}
	
	/**
	 * Adds the observer in the observers list if it is not already there
	 * 
	 * @param observer
	 *            observer that will be added into the list
	 */
	public void addObserver(IntegerStorageObserver observer) {
		// add the observer in observers if not already there ...
		if(observers == null) {
			observers = new ArrayList<>();
		}
		
		if(!observers.contains(observer)) {
			observers.add(observer);
		}
	}
	
	/**
	 * Removes the observer from the observers list if it is present there
	 * 
	 * @param observer
	 *            observer that will be removed from the list
	 */
	public void removeObserver(IntegerStorageObserver observer) {
		// remove the observer from observers if present ...
		observers.remove(observer);
	}
	
	/**
	 * Removes all observers from observers list
	 */
	public void clearObservers() {
		// remove all observers from observers list ...
		observers.clear();
	}
	
	/**
	 * Getter for value
	 * 
	 * @return current stored value
	 */
	public int getValue() {
		return value;
	}
	
	/**
	 * Setter for value
	 * 
	 * @param value
	 *            new value
	 */
	public void setValue(int value) {
		// Only if new value is different than the current value:
		if(this.value!=value) {
			IntegerStorageChange change = new IntegerStorageChange(this, this.value, value);
			// Update current value
			this.value = value;
			// Notify all registered observers
			if(observers!=null) {
				List<IntegerStorageObserver> tempObserversList = new ArrayList<>(observers);
				for(IntegerStorageObserver observer : tempObserversList) {
					observer.valueChanged(change);
				}
			}
		}
	}

}

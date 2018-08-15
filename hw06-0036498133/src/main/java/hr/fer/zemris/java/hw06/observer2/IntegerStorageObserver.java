package hr.fer.zemris.java.hw06.observer2;

/**
 * Interface implemented by observers which do certain action when the changes
 * are made in the given IntegerStorage
 * 
 * @author Rafael Josip Penić
 *
 */
public interface IntegerStorageObserver {

	/**
	 * Method that that represents an action which should be made when the value of
	 * the given storage is changed
	 * 
	 * @param istorage
	 *            IntegerStorageChange object which notifies observer
	 */
	public void valueChanged(IntegerStorageChange istorage);
	
}

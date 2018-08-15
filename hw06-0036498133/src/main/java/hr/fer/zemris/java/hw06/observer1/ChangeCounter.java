package hr.fer.zemris.java.hw06.observer1;

/**
 * Counts how many times stored value changed since the registration and prints
 * that number on the standard output
 * 
 * @author Rafael Josip Penić
 *
 */
public class ChangeCounter implements IntegerStorageObserver {
	/**
	 * Variable used for counting how many times the value changed
	 */
	private int counter = 0;

	@Override
	public void valueChanged(IntegerStorage istorage) {
		counter++;
		System.out.println("Number of value changes since tracking: " + counter);
	}

}

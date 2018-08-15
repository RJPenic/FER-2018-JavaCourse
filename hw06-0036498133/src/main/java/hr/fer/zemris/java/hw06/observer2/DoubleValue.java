package hr.fer.zemris.java.hw06.observer2;

/**
 * Writes the double value of the current value stored in the storage on the
 * standard output, but it does it only certain number of times. After that it
 * de-registers itself from the subject
 * 
 * @author Rafael Josip Penić
 *
 */
public class DoubleValue implements IntegerStorageObserver {
	
	/**
	 * Determines how many times the double value will be printed. After that,
	 * observer will be de-registered from the subject
	 */
	private int n;
	
	
	/**
	 * Constructor for DoubleValue objects
	 * 
	 * @param n
	 *            number that determines how many times the object will print double
	 *            value until it de-registers itself from the subject
	 */
	public DoubleValue(int n) {
		if(n < 1) throw new IllegalArgumentException("Constructor argument cannot be lesser than 1.");
		this.n = n;
	}

	@Override
	public void valueChanged(IntegerStorageChange istorage) {
		System.out.println("Double value: " + 2 * istorage.getNewValue());
		n--;
		if(n == 0) {
			istorage.getIntStorage().removeObserver(this);
		}
	}
	
}

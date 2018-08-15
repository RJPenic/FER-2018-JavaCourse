package hr.fer.zemris.java.hw06.observer2;

/**
 * Demo class that demonstrates how observers,IntegerStorage and
 * IntegerStorageChange classes work
 * 
 * @author Rafael Josip Penić
 *
 */
public class ObserverExample {

	/**
	 * Main method
	 * 
	 * @param args
	 *            command line arguments
	 */
	public static void main(String[] args) {

		IntegerStorage istorage = new IntegerStorage(20);

		IntegerStorageObserver observer = new SquareValue();

		istorage.addObserver(observer);
		istorage.addObserver(new ChangeCounter());
		istorage.addObserver(new DoubleValue(1));
		istorage.addObserver(new DoubleValue(2));
		istorage.addObserver(new DoubleValue(2));

		istorage.setValue(5);
		istorage.setValue(2);
		istorage.setValue(25);

		istorage.setValue(13);
		istorage.setValue(22);
		istorage.setValue(15);
	}
}

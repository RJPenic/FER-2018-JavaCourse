package hr.fer.zemris.java.hw06.observer2;

/**
 * Observer that each time the observed value is changed, prints square of that
 * value on the standard output
 * 
 * @author Rafael Josip Penić
 *
 */
public class SquareValue implements IntegerStorageObserver {

	@Override
	public void valueChanged(IntegerStorageChange istorage) {
		System.out.println("Provided new value: " + istorage.getNewValue() + 
							", square is " + istorage.getNewValue() * istorage.getNewValue());
	}

}

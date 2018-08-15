package hr.fer.zemris.java.hw06.observer1;

/**
 * Observer that each time the observed value is changed, prints square of that
 * value on the standard output
 * 
 * @author Rafael Josip Penić
 *
 */
public class SquareValue implements IntegerStorageObserver {

	@Override
	public void valueChanged(IntegerStorage istorage) {
		System.out.println("Provided new value: " + istorage.getValue() + ", square is "
				+ istorage.getValue() * istorage.getValue());
	}

}

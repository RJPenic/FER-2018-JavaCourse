package hr.fer.zemris.java.gui.prim;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * List model which stores prime numbers and generates next prime number when
 * needed
 * 
 * @author Rafael Josip Penić
 *
 */
public class PrimListModel implements ListModel<Integer> {

	/**
	 * List storing prime numbers generated so far
	 */
	private List<Integer> primes = new ArrayList<>();

	/**
	 * List that stores listeners of the model
	 */
	private List<ListDataListener> listeners = new ArrayList<>();

	/**
	 * Constructor which constructs PrimListModel object and adds 1 into prime
	 * numbers list
	 */
	public PrimListModel() {
		primes.add(1);
	}

	@Override
	public void addListDataListener(ListDataListener arg0) {
		Objects.requireNonNull(arg0, "Given listener reference is null.");
		listeners.add(arg0);
	}

	@Override
	public Integer getElementAt(int arg0) {
		return primes.get(arg0);
	}

	@Override
	public int getSize() {
		return primes.size();
	}

	@Override
	public void removeListDataListener(ListDataListener arg0) {
		Objects.requireNonNull(arg0, "Given listener reference is null.");
		listeners.remove(arg0);
	}

	/**
	 * Stores next prime number into prime numbers list and notifies listeners about
	 * the change
	 */
	public void next() {
		int pos = getSize();
		int lastPrime = primes.get(pos - 1);

		primes.add(nextPrime(lastPrime));

		ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, pos, pos);
		for (ListDataListener l : listeners) {
			l.intervalAdded(event);
		}
	}

	/**
	 * Finds first prime number that is greater than the given number
	 * 
	 * @param lastPrime
	 *            number from which the prime is searched for
	 * @return next prime
	 */
	private int nextPrime(int lastPrime) {
		int i = lastPrime + 1;
		while (true) {
			boolean isPrime = true;

			if (i != 2 && i % 2 == 0) {
				i++;
				continue;
			}

			for (int j = 3; j <= Math.sqrt(i); j += 2) {
				if (i % j == 0) {
					isPrime = false;
					break;
				}
			}

			if (isPrime)
				return i;
			i++;
		}
	}
}

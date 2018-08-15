package hr.fer.zemris.java.hw06.demo2;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Class that represent the collection of prime integer without actually storing
 * them
 * 
 * @author Rafael Josip Penić
 *
 */
public class PrimesCollection implements Iterable<Integer> {

	/**
	 * Size of the collection
	 */
	private int size;

	/**
	 * Constructor for primes collections
	 * 
	 * @param size
	 *            size of the collection that will be constructed
	 * @throws IllegalArgumentException
	 *             in case the given value is negative
	 */
	public PrimesCollection(int size) {
		if (size < 0)
			throw new IllegalArgumentException("Collection size cannot be negative.");

		this.size = size;
	}

	/**
	 * Class representing iterator for PrimesCollection which is used for getting
	 * more primes
	 * 
	 * @author Rafael Josip Penić
	 *
	 */
	private class PrimeIterator implements Iterator<Integer> {
		/**
		 * First number that hasn't been tested if it's prime
		 */
		private int currentNumber = 2;

		/**
		 * Counter used for determining if the collection exceeds its size or not
		 */
		private int counter = 0;

		@Override
		public boolean hasNext() {
			return counter < size;
		}

		@Override
		public Integer next() {
			if(counter >= size) throw new NoSuchElementException("There are no more elements in primes collection");
			
			while (!isPrime(currentNumber)) {
				currentNumber++;
			}

			counter++;

			return currentNumber++;
		}

		/**
		 * Method that tells if the certain number is prime or not
		 * 
		 * @param number
		 *            number that will be tested
		 * @return true if the given number is prime, false otherwise
		 */
		private boolean isPrime(int number) {
			if (number != 2 && number % 2 == 0)
				return false;

			int borderValue = (int) Math.sqrt(number);

			for (int i = 3; i <= borderValue; i += 2) {
				if (number % i == 0)
					return false;
			}

			return true;
		}
	}

	@Override
	public PrimeIterator iterator() {
		return new PrimeIterator();
	}

}

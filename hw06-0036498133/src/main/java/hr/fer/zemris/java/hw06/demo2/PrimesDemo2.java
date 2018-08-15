package hr.fer.zemris.java.hw06.demo2;

/**
 * Second demo class that demonstrates how primes collections work
 * 
 * @author Rafael Josip Penić
 *
 */
public class PrimesDemo2 {

	/**
	 * Main method
	 * 
	 * @param args
	 *            command line arguments
	 */
	public static void main(String[] args) {
		PrimesCollection primesCollection = new PrimesCollection(2);
		for (Integer prime : primesCollection) {
			for (Integer prime2 : primesCollection) {
				System.out.println("Got prime pair: " + prime + ", " + prime2);
			}
		}

	}
}

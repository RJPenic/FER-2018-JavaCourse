package hr.fer.zemris.java.hw06.demo2;

/**
 * First demo class that demonstrates PrimesCollection methods
 * 
 * @author Rafael Josip Penić
 *
 */
public class PrimesDemo1 {

	/**
	 * Main method
	 * 
	 * @param args
	 *            command line arguments
	 */
	public static void main(String[] args) {
		PrimesCollection primesCollection = new PrimesCollection(5); // 5: how many of them

		for (Integer prime : primesCollection) {
			System.out.println("Got prime: " + prime);
		}

	}
}

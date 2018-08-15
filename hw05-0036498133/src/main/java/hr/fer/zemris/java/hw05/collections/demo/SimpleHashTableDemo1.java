package hr.fer.zemris.java.hw05.collections.demo;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

import hr.fer.zemris.java.hw05.collections.SimpleHashtable;

/**
 * Class that demonstrates usage of SimpleHashtable class
 * 
 * @author Rafael Josip Penić
 *
 */
public class SimpleHashTableDemo1 {

	/**
	 * Main method where the usage is demonstrated
	 * 
	 * @param args
	 *            command line arguments
	 */
	public static void main(String[] args) {
		// create collection:
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);

		// fill data:
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5); // overwrites old grade for Ivana

		for (SimpleHashtable.TableEntry<String, Integer> pair : examMarks) {
			System.out.printf("%s => %d%n", pair.getKey(), pair.getValue());
		}

		// ----------------------------------------

		System.out.println("------------------");

		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();
		while (iter.hasNext()) {
			SimpleHashtable.TableEntry<String, Integer> pair = iter.next();

			if (pair.getKey().equals("Ivana")) {
				iter.remove(); // sam iterator kontrolirano uklanja trenutni element
			}
		}

		for (SimpleHashtable.TableEntry<String, Integer> pair : examMarks) {
			System.out.printf("%s => %d%n", pair.getKey(), pair.getValue());
		}

		examMarks.put("Ivana", 5);

		// ---------------------------------------------------

		System.out.println("------------------");

		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter1 = examMarks.iterator();
		try {
			while (iter1.hasNext()) {
				SimpleHashtable.TableEntry<String, Integer> pair = iter1.next();

				if (pair.getKey().equals("Ivana")) {
					iter1.remove();
					iter1.remove();
				}
			}

			System.out.println("Test failed! Double remove should have thrown an exception.");
		} catch (IllegalStateException ex) {
			System.out.println("IllegalStateException has been thrown. Test passed!");
		}

		examMarks.put("Ivana", 5);

		// -------------------------------------------------------

		System.out.println("------------------");

		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter3 = examMarks.iterator();
		try {
			while (iter3.hasNext()) {
				SimpleHashtable.TableEntry<String, Integer> pair = iter3.next();
				if (pair.getKey().equals("Ivana")) {
					examMarks.remove("Ivana");
				}
			}
			System.out.println("Test failed! An exception should have been thrown.");
		} catch (ConcurrentModificationException ex) {
			System.out.println("ConcurrentModificationException has been thrown. Test passed!");
		}

		examMarks.put("Ivana", 5);

		// --------------------------------------------------------

		System.out.println("------------------");

		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter4 = examMarks.iterator();

		while (iter4.hasNext()) {
			SimpleHashtable.TableEntry<String, Integer> pair = iter4.next();
			System.out.printf("%s => %d%n", pair.getKey(), pair.getValue());
			iter4.remove();
		}

		System.out.printf("Veličina: %d%n", examMarks.size());// should print out 0

	}
}

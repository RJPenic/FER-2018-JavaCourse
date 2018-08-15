package hr.fer.zemris.java.custom.collections;

import org.junit.Test;
import org.junit.Assert;

public class ArrayIndexedCollectionTest {

	@Test
	public void testClear() {
		ArrayIndexedCollection coll = new ArrayIndexedCollection();

		for (int i = 0; i < 5; i++) {
			coll.add(i);
		}

		coll.clear();

		for (int i = 0; i < 5; i++) {
			Assert.assertEquals(-1, coll.indexOf(i));
		}
	}

	@Test
	public void testAdd() {
		ArrayIndexedCollection coll = new ArrayIndexedCollection();

		fillCollection(coll);
		coll.add(-35);

		Assert.assertEquals(-35, coll.get(5));
	}

	@Test
	public void testAddWhenCollectionFull() {
		ArrayIndexedCollection coll = new ArrayIndexedCollection();

		for (int i = ArrayIndexedCollection.FIRST_POSITION; i < ArrayIndexedCollection.DEFAULT_COLLECTION_CAPACITY; i++) {
			coll.add(i);
		}

		coll.add("Sš 12,");
		coll.add(23);

		Assert.assertEquals("Sš 12,", coll.get(ArrayIndexedCollection.DEFAULT_COLLECTION_CAPACITY));
		Assert.assertEquals(23, coll.get(ArrayIndexedCollection.DEFAULT_COLLECTION_CAPACITY + 1));
	}

	@Test
	public void testAddWhenAddingNull() {
		ArrayIndexedCollection coll = new ArrayIndexedCollection();

		try {
			coll.add(null);
			Assert.assertTrue(false);
		} catch (NullPointerException ex) {
			Assert.assertTrue(true);
		}
	}

	@Test
	public void testGet() {
		ArrayIndexedCollection coll = new ArrayIndexedCollection();

		fillCollection(coll);

		Assert.assertEquals(coll.toArray()[3], coll.get(3));
	}

	@Test
	public void testGetWhenOutOfBounds() {
		ArrayIndexedCollection coll = new ArrayIndexedCollection();

		fillCollection(coll);

		try {
			coll.get(5);
			coll.get(-1);
			Assert.assertTrue(false);
		} catch (IndexOutOfBoundsException ex) {
			Assert.assertTrue(true);
		}
	}

	@Test
	public void testInsert() {
		ArrayIndexedCollection coll = new ArrayIndexedCollection();

		fillCollection(coll);

		coll.insert('a', 2);

		Assert.assertEquals('a', coll.get(2));
		Assert.assertEquals(-35, coll.get(3));
	}

	@Test
	public void testInsertAtFirstPosition() {
		ArrayIndexedCollection coll = new ArrayIndexedCollection();

		fillCollection(coll);

		coll.insert('a', 0);

		Assert.assertEquals('a', coll.get(0));
	}

	@Test
	public void testInsertAtLastPosition() {
		ArrayIndexedCollection coll = new ArrayIndexedCollection();

		for (int i = ArrayIndexedCollection.FIRST_POSITION; i < ArrayIndexedCollection.DEFAULT_COLLECTION_CAPACITY; i++) {
			coll.add(i);
		}

		coll.insert("ag", ArrayIndexedCollection.DEFAULT_COLLECTION_CAPACITY);

		Assert.assertEquals("ag", coll.get(ArrayIndexedCollection.DEFAULT_COLLECTION_CAPACITY));
	}

	@Test
	public void testIndexOfWhenPresent() {
		ArrayIndexedCollection coll = new ArrayIndexedCollection();

		fillCollection(coll);

		Assert.assertEquals(2, coll.indexOf(-35));
	}

	@Test
	public void testIndexOfWhenNotPresent() {
		ArrayIndexedCollection coll = new ArrayIndexedCollection();

		fillCollection(coll);

		Assert.assertEquals(-1, coll.indexOf('š'));
	}

	@Test
	public void testRemoveFromFirstPosition() {
		ArrayIndexedCollection coll = new ArrayIndexedCollection();

		fillCollection(coll);

		coll.remove(0);

		Assert.assertNotEquals('S', coll.get(0));
		Assert.assertNotNull(coll.get(0));
	}

	@Test
	public void testRemoveFromLastPosition() {
		ArrayIndexedCollection coll = new ArrayIndexedCollection();

		fillCollection(coll);

		coll.remove(4);

		Assert.assertEquals(-1, coll.indexOf("bas"));
		Assert.assertEquals(4, coll.size());
	}

	private void fillCollection(ArrayIndexedCollection coll) {
		coll.add('S');
		coll.add(2.14523);
		coll.add(-35);
		coll.add("štef");
		coll.add("bas");
	}
}

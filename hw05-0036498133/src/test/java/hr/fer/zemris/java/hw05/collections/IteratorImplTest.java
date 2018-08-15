package hr.fer.zemris.java.hw05.collections;

import org.junit.Test;

import hr.fer.zemris.java.hw05.collections.SimpleHashtable.TableEntry;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Assert;

public class IteratorImplTest {

	@Test
	public void testHasNextWhenNoNext() {
		SimpleHashtable<String, Integer> hashTable = new SimpleHashtable<>(4);
		
		hashTable.put("Ivan", 3);
		hashTable.put("Stane", 1);
		
		Iterator<TableEntry<String, Integer>> iter = hashTable.iterator();
		
		iter.next();
		iter.next();
		
		Assert.assertFalse(iter.hasNext());
	}
	
	@Test
	public void testHasNextWhenThereIsNext() {
		SimpleHashtable<String, Integer> hashTable = new SimpleHashtable<>(3);
		
		hashTable.put("Ivan", 3);
		hashTable.put("Stane", 1);
		
		Iterator<TableEntry<String, Integer>> iter = hashTable.iterator();
		
		Assert.assertTrue(iter.hasNext());
	}
	
	@Test(expected = NoSuchElementException.class)
	public void testNext() {
		SimpleHashtable<String, Integer> hashTable = new SimpleHashtable<>(4);
		
		hashTable.put("Ivan", 3);
		hashTable.put("Stane", 1);
		
		Iterator<TableEntry<String, Integer>> iter = hashTable.iterator();
		
		Assert.assertEquals("Ivan", iter.next().getKey());
		Assert.assertEquals("Stane", iter.next().getKey());
		iter.next(); // throws
	}
	
	@Test(expected = ConcurrentModificationException.class)
	public void testWhenConcurrentChangesOnHashtable() {
		SimpleHashtable<String, Integer> hashTable = new SimpleHashtable<>(4);
		
		hashTable.put("Ivan", 3);
		hashTable.put("Stane", 1);
		
		Iterator<TableEntry<String, Integer>> iter = hashTable.iterator();
		
		iter.next();
		iter.hasNext();
		hashTable.put("Marko", 4);
		iter.next(); //throws
	}
	
	@Test
	public void testRemove() {
		SimpleHashtable<String, Integer> hashTable = new SimpleHashtable<>(4);
		
		hashTable.put("Ivan", 3);
		hashTable.put("Stane", 1);
		hashTable.put("Tihomir", 4);
		hashTable.put("Ana", 4);
		hashTable.put("Marija", 2);
		
		Iterator<TableEntry<String, Integer>> iter = hashTable.iterator();
		
		Assert.assertEquals(5, hashTable.size());
		
		iter.next();
		iter.next();
		iter.remove();
		
		Assert.assertTrue(iter.hasNext());
		Assert.assertEquals(4, hashTable.size());
	}
}

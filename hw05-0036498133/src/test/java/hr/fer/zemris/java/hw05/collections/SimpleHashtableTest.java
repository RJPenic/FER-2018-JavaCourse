package hr.fer.zemris.java.hw05.collections;

import org.junit.Test;
import org.junit.Assert;

public class SimpleHashtableTest {

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorWhenGivenArgumentLesserThan1() {
		SimpleHashtable<String, Integer> hashTable = new SimpleHashtable<>(0);
		
		hashTable.put("k", 1);
	}
	
	@Test
	public void testIsEmptyWhenEmpty() {
		SimpleHashtable<String, Integer> hashTable = new SimpleHashtable<>();
		
		Assert.assertTrue(hashTable.isEmpty());
	}
	
	@Test
	public void testIsEmptyWhenNotEmpty() {
		SimpleHashtable<String, Integer> hashTable = new SimpleHashtable<>(3);
		
		fillWithData(hashTable);
		
		Assert.assertFalse(hashTable.isEmpty());
	}
	
	@Test
	public void testSizeWhenEmpty() {
		SimpleHashtable<String, Integer> hashTable = new SimpleHashtable<>(16);
		
		Assert.assertEquals(0, hashTable.size());
	}
	
	@Test
	public void testSizeWhenNotEmpty() {
		SimpleHashtable<String, Integer> hashTable = new SimpleHashtable<>(3);
		
		fillWithData(hashTable);
		
		Assert.assertEquals(6, hashTable.size());
	}
	
	@Test
	public void testGetWhenKeyIsPresent() {
		SimpleHashtable<String, Integer> hashTable = new SimpleHashtable<>(3);
		
		fillWithData(hashTable);
		
		Assert.assertEquals(Integer.valueOf(2), hashTable.get("Ante"));
	}
	
	@Test
	public void testGetWhenKeyIsNotPresent() {
		SimpleHashtable<String, Integer> hashTable = new SimpleHashtable<>(3);
		
		fillWithData(hashTable);
		
		Assert.assertNull(hashTable.get("Nikola"));
	}
	
	@Test
	public void testGetWhenKeyIsNotValidClass() {
		SimpleHashtable<String, Integer> hashTable = new SimpleHashtable<>(3);
		
		fillWithData(hashTable);
		
		Assert.assertNull(hashTable.get(5.12));
	}
	
	@Test
	public void testPutWhenRewritingValue() {
		SimpleHashtable<String, Integer> hashTable = new SimpleHashtable<>(3);
		
		fillWithData(hashTable);
		
		hashTable.put("Marko", 1);
		
		Assert.assertEquals(6, hashTable.size());
		Assert.assertEquals(Integer.valueOf(1), hashTable.get("Marko"));
	}
	
	@Test
	public void testContainsKeyWhenPresent() {
		SimpleHashtable<String, Integer> hashTable = new SimpleHashtable<>(3);
		
		fillWithData(hashTable);
		
		Assert.assertTrue(hashTable.containsKey("Ana"));
	}
	
	@Test
	public void testContainsKeyWhenNotPresent() {
		SimpleHashtable<String, Integer> hashTable = new SimpleHashtable<>(3);
		
		fillWithData(hashTable);
		
		Assert.assertFalse(hashTable.containsKey("Veronika"));
	}
	
	@Test
	public void testContainsValueWhenPresent() {
		SimpleHashtable<String, Integer> hashTable = new SimpleHashtable<>(7);
		
		fillWithData(hashTable);
		
		Assert.assertTrue(hashTable.containsValue(5));
	}
	
	@Test
	public void testContainsValueWhenNotPresent() {
		SimpleHashtable<String, Integer> hashTable = new SimpleHashtable<>(7);
			
		fillWithData(hashTable);
			
		Assert.assertFalse(hashTable.containsValue(1));
	}
	
	@Test
	public void testRemoveWhenPresent() {
		SimpleHashtable<String, Integer> hashTable = new SimpleHashtable<>(7);
		
		fillWithData(hashTable);
			
		hashTable.remove("Marko");
		
		Assert.assertEquals(5, hashTable.size());
		Assert.assertFalse(hashTable.containsKey("Marko"));
	}
	
	@Test
	public void testRemoveWhenNotPresent() {
		SimpleHashtable<String, Integer> hashTable = new SimpleHashtable<>(7);
		
		fillWithData(hashTable);
			
		hashTable.remove("Mihael");
		
		Assert.assertEquals(6, hashTable.size());
	}
	
	@Test
	public void testRemoveWhenEmpty() {
		SimpleHashtable<String, Integer> hashTable = new SimpleHashtable<>(7);
			
		hashTable.remove("Mihael");
		
		Assert.assertTrue(hashTable.isEmpty());
	}
	
	@Test
	public void testClear() {
		SimpleHashtable<String, Integer> hashTable = new SimpleHashtable<>(7);
		
		fillWithData(hashTable);
		
		hashTable.clear();
		
		Assert.assertEquals(0, hashTable.size());
	}
	
	private void fillWithData(SimpleHashtable<String, Integer> hashTable) {
		hashTable.put("Ivan", 5);
		hashTable.put("Marko", 5);
		hashTable.put("Ana", 4);
		hashTable.put("Marija", 5);
		hashTable.put("Ante", 2);
		hashTable.put("Jelena", 3);
	}
}

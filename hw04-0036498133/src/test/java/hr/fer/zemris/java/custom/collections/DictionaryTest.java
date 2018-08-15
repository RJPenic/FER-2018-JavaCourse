package hr.fer.zemris.java.custom.collections;

import org.junit.Assert;
import org.junit.Test;

public class DictionaryTest {
	
	@Test
	public void testIsEmptyWhenEmpty() {
		Dictionary d = new Dictionary();
		
		Assert.assertTrue(d.isEmpty());
	}
	
	@Test
	public void testSizeWhenEmpty() {
		Dictionary d = new Dictionary();
		
		Assert.assertEquals(0, d.size());
	}
	
	@Test
	public void testSizeWhenNotEmpty() {
		Dictionary d = new Dictionary();
		
		d.put("dre", 1);
		d.put("skrra", 'c');
		d.put(3, 222);
		
		Assert.assertEquals(3, d.size());
	}
	
	@Test
	public void testGetWhenNotPresent() {
		Dictionary d = new Dictionary();
		
		d.put("dre", 1);
		d.put("skrra", 'c');
		d.put(3, 222);
		
		Assert.assertNull(d.get('t'));
	}
	
	@Test
	public void testGetWhenPresent() {
		Dictionary d = new Dictionary();
		
		d.put("dre", 1);
		d.put("skrra", 'c');
		d.put(3, 222);
		
		Assert.assertEquals('c', d.get("skrra"));
	}
	
	@Test
	public void testClear() {
		Dictionary d = new Dictionary();
		
		d.put("dre", 1);
		d.put("skrra", 'c');
		d.put(3, 222);
		
		d.clear();
		
		Assert.assertEquals(0, d.size());
	}
	
	@Test
	public void testPutWhenPresent() {
		Dictionary d = new Dictionary();
		
		d.put("dre", 1);
		d.put("skrra", 'c');
		d.put(3, 222);
		d.put("skrra", 3);
		
		Assert.assertEquals(3, d.get("skrra"));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testWhenPuttingNullKey() {
		Dictionary d = new Dictionary();
		
		d.put("dre", 1);
		d.put(null, 3); //throws
	}
	
	@Test
	public void testPutWhenPuttingValueNull() {
		Dictionary d = new Dictionary();
		
		d.put("dre", 1);
		d.put(3, null);
		d.put("sd", "prprp");
		
		Assert.assertNull(d.get(3));
	}
}

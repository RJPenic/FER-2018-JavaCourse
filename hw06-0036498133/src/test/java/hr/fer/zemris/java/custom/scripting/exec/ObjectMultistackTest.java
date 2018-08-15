package hr.fer.zemris.java.custom.scripting.exec;

import java.util.EmptyStackException;

import org.junit.Assert;
import org.junit.Test;

public class ObjectMultistackTest {
	
	@Test
	public void testPush() {
		ObjectMultistack multiStack = new ObjectMultistack();
		
		multiStack.push("e1", new ValueWrapper(null));
		multiStack.push("e2", new ValueWrapper(55));
		multiStack.push("e1", new ValueWrapper("asvss"));
		multiStack.push("e1", new ValueWrapper('t'));
		
		Assert.assertEquals('t', multiStack.pop("e1").getValue());
		Assert.assertEquals("asvss", multiStack.pop("e1").getValue());
		Assert.assertNull(multiStack.pop("e1").getValue());
		Assert.assertEquals(55, multiStack.pop("e2").getValue());
	}
	
	@Test
	public void testIsEmptyWhenEmpty() {
		ObjectMultistack multiStack = new ObjectMultistack();
		
		multiStack.push("222", new ValueWrapper(null));
		multiStack.push("331", new ValueWrapper(-222.1451));
		multiStack.push("222", new ValueWrapper("aR2ss"));
		multiStack.push("331", new ValueWrapper('k'));
		
		multiStack.pop("331");
		multiStack.pop("331");
		
		Assert.assertTrue(multiStack.isEmpty("331"));
	}
	
	@Test
	public void testIsEmptyWhenNotEmpty() {
		ObjectMultistack multiStack = new ObjectMultistack();
		
		multiStack.push("222", new ValueWrapper(null));
		multiStack.push("331", new ValueWrapper(null));
		multiStack.push("311", new ValueWrapper("aR2ss"));
		multiStack.push("331", new ValueWrapper('k'));
		
		Assert.assertFalse(multiStack.isEmpty("222"));
	}
	
	@Test(expected = EmptyStackException.class)
	public void testPopWhenEmpty() {
		ObjectMultistack multiStack = new ObjectMultistack();
		
		multiStack.push("sss", new ValueWrapper(null));
		multiStack.push("331", new ValueWrapper(211.11));
		multiStack.push("sss", new ValueWrapper("aR2sssfav2"));
		multiStack.push("21", new ValueWrapper('T'));
		
		multiStack.pop("sss");
		multiStack.pop("sss");
		multiStack.pop("sss");//throws
	}
	
	@Test
	public void testPopWhenNotEmpty() {
		ObjectMultistack multiStack = new ObjectMultistack();
		
		multiStack.push("sss", new ValueWrapper(null));
		multiStack.push("331", new ValueWrapper(211.11));
		multiStack.push("sss", new ValueWrapper("aR2sssfav2"));
		multiStack.push("21", new ValueWrapper('T'));
		
		Assert.assertEquals('T', multiStack.pop("21").getValue());
	}
	
	@Test(expected = EmptyStackException.class)
	public void testPeekWhenEmpty() {
		ObjectMultistack multiStack = new ObjectMultistack();
		
		multiStack.push("sss", new ValueWrapper(null));
		multiStack.push("331", new ValueWrapper(211.11));
		multiStack.push("sss", new ValueWrapper("aR2sssfav2"));
		multiStack.push("21", new ValueWrapper('T'));
		
		multiStack.peek("r");//throws
	}
	
	@Test
	public void testPeekWhenNotEmpty() {
		ObjectMultistack multiStack = new ObjectMultistack();
		
		multiStack.push("sss", new ValueWrapper(null));
		multiStack.push("sss", new ValueWrapper(211.11));
		multiStack.push("sss", new ValueWrapper("aR2sssfav2"));
		multiStack.push("21", new ValueWrapper('T'));
		multiStack.push("41414", new ValueWrapper(0.));
		
		Assert.assertEquals("aR2sssfav2", multiStack.peek("sss").getValue());
		Assert.assertEquals("aR2sssfav2", multiStack.pop("sss").getValue());
	}
}

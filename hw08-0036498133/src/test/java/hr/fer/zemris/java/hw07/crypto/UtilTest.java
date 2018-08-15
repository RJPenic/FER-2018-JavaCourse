package hr.fer.zemris.java.hw07.crypto;

import org.junit.Test;

import hr.fer.zemris.java.hw07.crypto.Util;

import org.junit.Assert;

public class UtilTest {
	
	@Test
	public void testByteToHexWhenArrayEmpty() {
		byte[] array = new byte[0];
		String s = Util.byteToHex(array);
		Assert.assertEquals("", s);
	}
	
	@Test
	public void testByteToHexWhenLegal() {
		byte[] array = {1, -82, 34};
		String s = Util.byteToHex(array);
		Assert.assertEquals("01ae22", s);
	}
	
	@Test
	public void testHexToByteWhenStringEmpty() {
		Assert.assertEquals(0, Util.hexToByte("").length);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testHexToByteWhenOddNumberOfCharacters() {
		Util.hexToByte("ad13f");//throws
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testHexToByteWhenIllegalCharacters1() {
		Util.hexToByte("d251et");//throws
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testHexToByteWhenIllegalCharacters2() {
		Util.hexToByte("d2:51e");//throws
	}
	
	@Test
	public void testHexToByteWhenLegal() {
		String s = "01aE22";
		byte[] array = Util.hexToByte(s);
		
		Assert.assertEquals(3, array.length);
		Assert.assertEquals(1, array[0]);
		Assert.assertEquals(-82, array[1]);
		Assert.assertEquals(34, array[2]);
	}
}

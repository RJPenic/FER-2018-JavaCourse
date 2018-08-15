package hr.fer.zemris.java.custom.scripting.exec;

import org.junit.Assert;
import org.junit.Test;

public class ValueWrapperTest {
	
	private final static double THRESHOLD = 1E-7;
	
	@Test
	public void testAddWhenWrapperIntegerAndArgumentInteger() {
		ValueWrapper vw1 = new ValueWrapper(Integer.valueOf(2612));
		Object ob = Integer.valueOf(-1251);
		
		vw1.add(ob);
		
		Assert.assertEquals(Integer.valueOf(1361), vw1.getValue());
		Assert.assertEquals(Integer.valueOf(-1251), ob);
	}
	
	@Test
	public void testAddWhenWrapperDoubleAndArgumentInteger() {
		ValueWrapper vw1 = new ValueWrapper(Double.valueOf(1225.1));
		Object ob = Integer.valueOf(-1251);
		
		vw1.add(ob);
		
		Assert.assertTrue(isDoubleEquals(-25.9, ((Double)vw1.getValue()).doubleValue()));
		Assert.assertEquals(Integer.valueOf(-1251), ob);
	}
	
	@Test
	public void testAddWhenWrapperIntegerAndArgumentDouble() {
		ValueWrapper vw1 = new ValueWrapper(Integer.valueOf(55));
		Object ob = Double.valueOf(2.3);
		
		vw1.add(ob);
		
		Assert.assertTrue(isDoubleEquals(57.3, ((Double)vw1.getValue()).doubleValue()));
		Assert.assertEquals(Double.valueOf(2.3), ob);
	}
	
	@Test
	public void testAddWhenWrapperDoubleAndArgumentDouble() {
		ValueWrapper vw1 = new ValueWrapper(Double.valueOf(55.12));
		Object ob = Double.valueOf(2.3);
		
		vw1.add(ob);
		
		Assert.assertTrue(isDoubleEquals(57.42, ((Double)vw1.getValue()).doubleValue()));
		Assert.assertEquals(Double.valueOf(2.3), ob);
	}
	
	@Test
	public void testSubtractWhenWrapperIntegerAndArgumentInteger() {
		ValueWrapper vw1 = new ValueWrapper(Integer.valueOf(2612));
		Object ob = Integer.valueOf(-1251);
		
		vw1.subtract(ob);
		
		Assert.assertEquals(Integer.valueOf(3863), vw1.getValue());
		Assert.assertEquals(Integer.valueOf(-1251), ob);
	}
	
	@Test
	public void testSubtractWhenWrapperDoubleAndArgumentInteger() {
		ValueWrapper vw1 = new ValueWrapper(Double.valueOf(1225.1));
		Object ob = Integer.valueOf(-1251);
		
		vw1.subtract(ob);
		
		Assert.assertTrue(isDoubleEquals(2476.1, ((Double)vw1.getValue()).doubleValue()));
		Assert.assertEquals(Integer.valueOf(-1251), ob);
	}
	
	@Test
	public void testSubtractWhenWrapperIntegerAndArgumentDouble() {
		ValueWrapper vw1 = new ValueWrapper(Integer.valueOf(55));
		Object ob = Double.valueOf(2.3);
		
		vw1.subtract(ob);
		
		Assert.assertTrue(isDoubleEquals(52.7, ((Double)vw1.getValue()).doubleValue()));
		Assert.assertEquals(Double.valueOf(2.3), ob);
	}
	
	@Test
	public void testSubtractWhenWrapperDoubleAndArgumentDouble() {
		ValueWrapper vw1 = new ValueWrapper(Double.valueOf(55.12));
		Object ob = Double.valueOf(2.3);
		
		vw1.subtract(ob);
		
		Assert.assertTrue(isDoubleEquals(52.82, ((Double)vw1.getValue()).doubleValue()));
		Assert.assertEquals(Double.valueOf(2.3), ob);
	}
	
	@Test
	public void testMultiplyWhenWrapperIntegerAndArgumentInteger() {
		ValueWrapper vw1 = new ValueWrapper(Integer.valueOf(2612));
		Object ob = Integer.valueOf(-1251);
		
		vw1.multiply(ob);
		
		Assert.assertEquals(Integer.valueOf(-3267612), vw1.getValue());
		Assert.assertEquals(Integer.valueOf(-1251), ob);
	}
	
	@Test
	public void testMultiplyWhenWrapperDoubleAndArgumentInteger() {
		ValueWrapper vw1 = new ValueWrapper(Double.valueOf(1225.1));
		Object ob = Integer.valueOf(-1251);
		
		vw1.multiply(ob);
		
		Assert.assertTrue(isDoubleEquals(-1532600.1, ((Double)vw1.getValue()).doubleValue()));
		Assert.assertEquals(Integer.valueOf(-1251), ob);
	}
	
	@Test
	public void testMultiplyWhenWrapperIntegerAndArgumentDouble() {
		ValueWrapper vw1 = new ValueWrapper(Integer.valueOf(55));
		Object ob = Double.valueOf(2.3);
		
		vw1.multiply(ob);
		
		Assert.assertTrue(isDoubleEquals(126.5, ((Double)vw1.getValue()).doubleValue()));
		Assert.assertEquals(Double.valueOf(2.3), ob);
	}
	
	@Test
	public void testMultiplyWhenWrapperDoubleAndArgumentDouble() {
		ValueWrapper vw1 = new ValueWrapper(Double.valueOf(55.12));
		Object ob = Double.valueOf(2.3);
		
		vw1.multiply(ob);
		
		Assert.assertTrue(isDoubleEquals(126.776, ((Double)vw1.getValue()).doubleValue()));
		Assert.assertEquals(Double.valueOf(2.3), ob);
	}
	
	@Test
	public void testDivideWhenWrapperIntegerAndArgumentInteger() {
		ValueWrapper vw1 = new ValueWrapper(Integer.valueOf(2612));
		Object ob = Integer.valueOf(-1251);
		
		vw1.divide(ob);
		
		Assert.assertEquals(Integer.valueOf(2612/-1251), vw1.getValue());
		Assert.assertEquals(Integer.valueOf(-1251), ob);
	}
	
	@Test
	public void testDivideWhenWrapperDoubleAndArgumentInteger() {
		ValueWrapper vw1 = new ValueWrapper(Double.valueOf(4533.15));
		Object ob = Integer.valueOf(-1251);
		
		vw1.divide(ob);
		
		Assert.assertTrue(isDoubleEquals(4533.15/-1251, ((Double)vw1.getValue()).doubleValue()));
		Assert.assertEquals(Integer.valueOf(-1251), ob);
	}
	
	@Test
	public void testDivideWhenWrapperIntegerAndArgumentDouble() {
		ValueWrapper vw1 = new ValueWrapper(Integer.valueOf(55));
		Object ob = Double.valueOf(2.3);
		
		vw1.divide(ob);
		
		Assert.assertTrue(isDoubleEquals(55/2.3, ((Double)vw1.getValue()).doubleValue()));
		Assert.assertEquals(Double.valueOf(2.3), ob);
	}
	
	@Test
	public void testDivideWhenWrapperDoubleAndArgumentDouble() {
		ValueWrapper vw1 = new ValueWrapper(Double.valueOf(5.512e1));
		Object ob = Double.valueOf(2.3);
		
		vw1.divide(ob);
		
		Assert.assertTrue(isDoubleEquals(55.12/2.3, ((Double)vw1.getValue()).doubleValue()));
		Assert.assertEquals(Double.valueOf(2.3), ob);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testDivideWhenWrapperIntegerAndArgumentIntegerWhenArgumentZero() {
		ValueWrapper vw1 = new ValueWrapper(Integer.valueOf(2612));
		Object ob = Integer.valueOf(0);
		
		vw1.divide(ob);
	}
	
	@Test
	public void testDivideWhenWrapperDoubleAndArgumentIntegerWhenArgumentZero() {
		ValueWrapper vw1 = new ValueWrapper(Double.valueOf(4533.15));
		Object ob = Integer.valueOf(0);
		
		vw1.divide(ob);
		
		Assert.assertTrue(Double.isInfinite(((Double)vw1.getValue()).doubleValue()));
	}
	
	@Test
	public void testDivideWhenWrapperIntegerAndArgumentDoubleWhenArgumentZero() {
		ValueWrapper vw1 = new ValueWrapper(Integer.valueOf(55));
		Object ob = Double.valueOf(0);
		
		vw1.divide(ob);
		
		Assert.assertTrue(Double.isInfinite(((Double)vw1.getValue()).doubleValue()));
	}
	
	@Test
	public void testDivideWhenWrapperDoubleAndArgumentDoubleWhenArgumentZero() {
		ValueWrapper vw1 = new ValueWrapper(Double.valueOf("5.512e1"));
		Object ob = null;
		
		vw1.divide(ob);
		
		Assert.assertTrue(Double.isInfinite(((Double)vw1.getValue()).doubleValue()));
	}
	
	@Test
	public void testNumCompareWhenWrapperIntegerAndArgumentIntegerWhenEqual() {
		ValueWrapper vw1 = new ValueWrapper(Integer.valueOf(223));
		Object ob = Integer.valueOf(223);
		
		Assert.assertEquals(0, vw1.numCompare(ob));
	}
	
	@Test
	public void testNumCompareWhenWrapperIntegerAndArgumentIntegerWhenNotEqual() {
		ValueWrapper vw1 = new ValueWrapper(Integer.valueOf(271));
		Object ob = Integer.valueOf(223);
		
		Assert.assertEquals(1, vw1.numCompare(ob));
	}
	
	@Test
	public void testNumCompareWhenWrapperDoubleAndArgumentIntegerWhenEqual() {
		ValueWrapper vw1 = new ValueWrapper(Double.valueOf(223.0));
		Object ob = Integer.valueOf(223);
		
		Assert.assertEquals(0, vw1.numCompare(ob));
	}
	
	@Test
	public void testNumCompareWhenWrapperDoubleAndArgumentIntegerWhenNotEqual() {
		ValueWrapper vw1 = new ValueWrapper(Double.valueOf(121.134));
		Object ob = Integer.valueOf(223);
		
		Assert.assertEquals(-1, vw1.numCompare(ob));
	}
	
	@Test
	public void testNumCompareWhenWrapperIntegerAndArgumentDoubleWhenEqual() {
		ValueWrapper vw1 = new ValueWrapper(Integer.valueOf(551));
		Object ob = Double.valueOf(551.0);
		
		Assert.assertEquals(0, vw1.numCompare(ob));
	}
	
	@Test
	public void testNumCompareWhenWrapperIntegerAndArgumentDoubleWhenNotEqual() {
		ValueWrapper vw1 = new ValueWrapper(Integer.valueOf(551));
		Object ob = Double.valueOf(551.01);
		
		Assert.assertEquals(-1, vw1.numCompare(ob));
	}
	
	@Test
	public void testNumCompareWhenWrapperDoubleAndArgumentDoubleWhenNotEqual() {
		ValueWrapper vw1 = new ValueWrapper(Double.valueOf(551.0));
		Object ob = Double.valueOf(551.01);
		
		Assert.assertEquals(-1, vw1.numCompare(ob));
	}
	
	@Test
	public void testNumCompareWhenWrapperDoubleAndArgumentDoubleWhenEqual() {
		ValueWrapper vw1 = new ValueWrapper(Double.valueOf(1653.0/3));
		Object ob = Double.valueOf(551.000000);
		
		Assert.assertEquals(0, vw1.numCompare(ob));
	}
	
	@Test(expected = RuntimeException.class)
	public void testOperationsWhenArgumentStringOrNotDoubleOrInteger() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);
		v1.add(v2.getValue()); // v1 now stores Integer(0); v2 still stores null.
		Assert.assertEquals(Integer.valueOf(0), v1.getValue());
		Assert.assertNull(v2.getValue());
		
		ValueWrapper v3 = new ValueWrapper("1.2E1");
		ValueWrapper v4 = new ValueWrapper(Integer.valueOf(1));
		v3.add(v4.getValue()); // v3 now stores Double(13); v4 still stores Integer(1).
		Assert.assertTrue(isDoubleEquals((Double)v3.getValue(), 13.));
		Assert.assertEquals(Integer.valueOf(1), v4.getValue());
		
		ValueWrapper v5 = new ValueWrapper("12");
		ValueWrapper v6 = new ValueWrapper(Integer.valueOf(1));
		v5.add(v6.getValue()); // v5 now stores Integer(13); v6 still stores Integer(1).
		Assert.assertEquals(Integer.valueOf(13), v5.getValue());
		Assert.assertEquals(Integer.valueOf(1), v6.getValue());
		
		ValueWrapper v7 = new ValueWrapper("Ankica");
		ValueWrapper v8 = new ValueWrapper(Integer.valueOf(1));
		v7.add(v8.getValue()); // throws RuntimeException
	}
	
	private boolean isDoubleEquals(double value1, double value2) {
		return Math.abs(value1 - value2) < THRESHOLD;
	}
}

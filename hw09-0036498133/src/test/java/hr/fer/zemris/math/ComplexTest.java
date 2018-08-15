package hr.fer.zemris.math;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class ComplexTest {
	
	private final static double THRESHOLD = 1E-6;
	
	@Test
	public void testModule() {
		Complex c = new Complex(2, 2);
		
		Assert.assertTrue(Math.abs(c.module() - Math.sqrt(8)) < THRESHOLD);
	}
	
	@Test
	public void testModuleWhenModuleZero() {
		Complex c = new Complex();
		
		Assert.assertTrue(Math.abs(c.module()) < THRESHOLD);
	}
	
	@Test
	public void testMultiply() {
		Complex c1 = new Complex(1, -1);
		Complex c2 = new Complex(-4, 3);
		
		Complex c = c1.multiply(c2);
		Complex cAlt = c2.multiply(c1);
		
		Assert.assertTrue(Math.abs(c.getRe() - cAlt.getRe()) < THRESHOLD);
		Assert.assertTrue(Math.abs(c.getIm() - cAlt.getIm()) < THRESHOLD);
		
		Assert.assertTrue(Math.abs(c.getRe() + 1) < THRESHOLD);
		Assert.assertTrue(Math.abs(c.getIm() - 7) < THRESHOLD);
	}
	
	@Test
	public void testMultiplyWhenOneMultiplicandZero() {
		Complex c1 = Complex.ZERO;
		Complex c2 = new Complex(-4, 3);
		
		Complex c = c1.multiply(c2);
		
		Assert.assertTrue(Math.abs(c.getRe() - 0) < THRESHOLD);
		Assert.assertTrue(Math.abs(c.getIm() - 0) < THRESHOLD);
	}
	
	@Test
	public void testDivide() {
		Complex c1 = new Complex(-3, -1);
		Complex c2 = new Complex(-4, 3);
		
		Complex c = c1.divide(c2);
		
		Assert.assertTrue(Math.abs(c.getRe() - 9./25) < THRESHOLD);
		Assert.assertTrue(Math.abs(c.getIm() - 13./25) < THRESHOLD);
	}
	
	@Test
	public void testDivideWhenDividngZero() {
		Complex c1 = Complex.ZERO;
		Complex c2 = new Complex(-4, 3);
		
		Complex c = c1.divide(c2);
		
		Assert.assertTrue(Math.abs(c.getRe() - 0) < THRESHOLD);
		Assert.assertTrue(Math.abs(c.getIm() - 0) < THRESHOLD);
	}
	
	@Test
	public void testAdd() {
		Complex c1 = new Complex(1, -1);
		Complex c2 = new Complex(-4, 3);
		
		Complex c = c1.add(c2);
		Complex cAlt = c2.add(c1);
		
		Assert.assertTrue(Math.abs(c.getRe() - cAlt.getRe()) < THRESHOLD);
		Assert.assertTrue(Math.abs(c.getIm() - cAlt.getIm()) < THRESHOLD);
		
		Assert.assertTrue(Math.abs(c.getRe() + 3) < THRESHOLD);
		Assert.assertTrue(Math.abs(c.getIm() - 2) < THRESHOLD);
	}
	
	@Test
	public void testAddWhenOneZero() {
		Complex c1 = new Complex(1, -1);
		Complex c2 = Complex.ZERO;
		
		Complex c = c1.add(c2);
		
		
		Assert.assertTrue(Math.abs(c.getRe() - 1) < THRESHOLD);
		Assert.assertTrue(Math.abs(c.getIm() + 1) < THRESHOLD);
	}
	
	@Test
	public void testSub() {
		Complex c1 = new Complex(25, -11);
		Complex c2 = new Complex(-4, 3);
		
		Complex c = c1.sub(c2);
		
		Assert.assertTrue(Math.abs(c.getRe() - 29) < THRESHOLD);
		Assert.assertTrue(Math.abs(c.getIm() + 14) < THRESHOLD);
	}
	
	@Test
	public void testSubWhenOneArgZero() {
		Complex c1 = new Complex(25, -11);
		Complex c2 = Complex.ZERO;
		
		Complex c = c1.sub(c2);
		
		Assert.assertTrue(Math.abs(c.getRe() - 25) < THRESHOLD);
		Assert.assertTrue(Math.abs(c.getIm() + 11) < THRESHOLD);
	}
	
	@Test
	public void testNegate() {
		Complex c1 = new Complex(-12, 55);
		
		Complex c = c1.negate();
		
		Assert.assertTrue(Math.abs(c.getRe() - 12) < THRESHOLD);
		Assert.assertTrue(Math.abs(c.getIm() + 55) < THRESHOLD);
		
		c = c.negate();
		
		Assert.assertTrue(Math.abs(c.getRe() + 12) < THRESHOLD);
		Assert.assertTrue(Math.abs(c.getIm() - 55) < THRESHOLD);
	}
	
	@Test
	public void testNegateWhenZero() {
		Complex c1 = Complex.ZERO;
		
		Complex c = c1.negate();
		
		Assert.assertTrue(Math.abs(c.getRe() + 0) < THRESHOLD);
		Assert.assertTrue(Math.abs(c.getIm() - 0) < THRESHOLD);
	}
	
	@Test
	public void testPower() {
		Complex c1 = new Complex(4, -3);
		
		Complex c = c1.power(5);
		
		Assert.assertTrue(Math.abs(c.getRe() + 3116) < THRESHOLD);
		Assert.assertTrue(Math.abs(c.getIm() - 237) < THRESHOLD);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testPowerWhenArgNegative() {
		Complex c1 = new Complex(0, -3);
		
		c1.power(-2);//throws
	}
	
	@Test
	public void testRoots() {
		Complex c = new Complex(5, -2);
		
		List<Complex> list = c.root(3);
		
		Assert.assertEquals(3, list.size());
		
		Assert.assertTrue(Math.abs(list.get(0).getRe() - 1.7387226) < THRESHOLD);
		Assert.assertTrue(Math.abs(list.get(0).getIm() + 0.2217219) < THRESHOLD);
		
		Assert.assertTrue(Math.abs(list.get(1).getRe() + 0.6773445) < THRESHOLD);
		Assert.assertTrue(Math.abs(list.get(1).getIm() - 1.6166389) < THRESHOLD);
		
		Assert.assertTrue(Math.abs(list.get(2).getRe() + 1.0613781) < THRESHOLD);
		Assert.assertTrue(Math.abs(list.get(2).getIm() + 1.394917) < THRESHOLD);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testRootsWhenArgumentZero() {
		Complex c = new Complex(3, -7);
		
		c.root(0);//throws
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testRootWhenArgumentNegative() {
		Complex c = new Complex(322, 0);
		
		c.root(-1);//throws
	}
	
}

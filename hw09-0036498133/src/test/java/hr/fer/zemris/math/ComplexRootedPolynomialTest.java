package hr.fer.zemris.math;

import org.junit.Test;
import org.junit.Assert;

public class ComplexRootedPolynomialTest {

	private final static double THRESHOLD = 1E-6;
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorWithoutArguments() {
		new ComplexRootedPolynomial();//throws
	}
	
	@Test
	public void testApply() {
		ComplexRootedPolynomial poly = new ComplexRootedPolynomial(new Complex(2, 2),
															new Complex(-1, -1),
															new Complex(1, 0));
		
		Complex c = poly.apply(new Complex(2, 1));
		
		Assert.assertTrue(Math.abs(c.getRe() - 5) < THRESHOLD);
		Assert.assertTrue(Math.abs(c.getIm() + 1) < THRESHOLD);						
	}
	
	@Test
	public void testApplyWhenApplyZero() {
		ComplexRootedPolynomial poly = new ComplexRootedPolynomial(new Complex(2, 2),
				new Complex(-1, -1),
				new Complex(1, 0));

		Complex c = poly.apply(Complex.ZERO);
		
		Assert.assertTrue(Math.abs(c.getRe() - 0) < THRESHOLD);
		Assert.assertTrue(Math.abs(c.getIm() - 4) < THRESHOLD);
	}
	
	@Test
	public void testToComplexPolynomial() {
		ComplexRootedPolynomial poly = new ComplexRootedPolynomial(new Complex(1, 0),
												new Complex(1, 0));
		
		ComplexPolynomial polyConverted = poly.toComplexPolynom();
		
		Complex[] factors = polyConverted.getFactors();
		
		Assert.assertEquals(2, polyConverted.order());
		
		Assert.assertTrue(Math.abs(factors[0].getRe() - 1) < THRESHOLD);
		Assert.assertTrue(Math.abs(factors[0].getIm() - 0) < THRESHOLD);
		
		Assert.assertTrue(Math.abs(factors[1].getRe() + 2) < THRESHOLD);
		Assert.assertTrue(Math.abs(factors[1].getIm() - 0) < THRESHOLD);
		
		Assert.assertTrue(Math.abs(factors[2].getRe() - 1) < THRESHOLD);
		Assert.assertTrue(Math.abs(factors[2].getIm() - 0) < THRESHOLD);
	}
	
	@Test
	public void testToComplexPolynomialWhenOnlyOneRoot() {
		ComplexRootedPolynomial poly = new ComplexRootedPolynomial(new Complex(4, 7));
		
		ComplexPolynomial polyConverted = poly.toComplexPolynom();
		
		Complex[] factors = polyConverted.getFactors();
		
		Assert.assertEquals(1, polyConverted.order());
		
		Assert.assertTrue(Math.abs(factors[0].getRe() + 4) < THRESHOLD);
		Assert.assertTrue(Math.abs(factors[0].getIm() + 7) < THRESHOLD);
		
		Assert.assertTrue(Math.abs(factors[1].getRe() - 1) < THRESHOLD);
		Assert.assertTrue(Math.abs(factors[1].getIm() - 0) < THRESHOLD);
	}
	
	@Test
	public void testIndexOfClosestRootFor() {
		ComplexRootedPolynomial poly = new ComplexRootedPolynomial(new Complex(2, 2),
				new Complex(-1, -1),
				new Complex(1, 0));
		
		Assert.assertEquals(1, poly.indexOfClosestRootFor(new Complex(-2,-2), 5));
	}
	
	@Test
	public void testIndexOfClosestRootForWhenNoRootInThreshold() {
		ComplexRootedPolynomial poly = new ComplexRootedPolynomial(new Complex(2, 2),
				new Complex(-1, -1),
				new Complex(1, 0));
		
		Assert.assertEquals(-1, poly.indexOfClosestRootFor(new Complex(-2,-2), 0.01));
	}
}

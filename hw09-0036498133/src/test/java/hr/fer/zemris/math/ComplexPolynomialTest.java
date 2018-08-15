package hr.fer.zemris.math;

import org.junit.Test;
import org.junit.Assert;

public class ComplexPolynomialTest {

	private final static double THRESHOLD = 1E-6;
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorWhenNoFactors() {
		@SuppressWarnings("unused")
		ComplexPolynomial poly = new ComplexPolynomial();//throws
	}
	
	@Test
	public void testOrder() {
		ComplexPolynomial poly = new ComplexPolynomial(new Complex(3, 2),
														new Complex(4, 1),
														new Complex(3, 0),
														new Complex(2, 2));
		
		Assert.assertEquals(3, poly.order());
	}
	
	@Test
	public void testDerive() {
		ComplexPolynomial poly = new ComplexPolynomial(new Complex(3, 2),
											new Complex(4, 1),
											new Complex(3, 0),
											new Complex(2, 2));
		
		ComplexPolynomial polyDerived = poly.derive();
		
		Assert.assertEquals(2, polyDerived.order());
		
		Complex[] factors = polyDerived.getFactors();
		
		Assert.assertTrue(Math.abs(factors[0].getRe() - 4) < THRESHOLD);
		Assert.assertTrue(Math.abs(factors[0].getIm() - 1) < THRESHOLD);
		
		Assert.assertTrue(Math.abs(factors[1].getRe() - 3 * 2) < THRESHOLD);
		Assert.assertTrue(Math.abs(factors[1].getIm() - 0) < THRESHOLD);
		
		Assert.assertTrue(Math.abs(factors[2].getRe() - 2 * 3) < THRESHOLD);
		Assert.assertTrue(Math.abs(factors[2].getIm() - 2 * 3) < THRESHOLD);
	}
	
	@Test
	public void testDeriveWhenDerivingPolynomialWithOrderZero() {
		ComplexPolynomial poly = new ComplexPolynomial(new Complex(-7, 3));
		
		ComplexPolynomial polyDerived = poly.derive();
		
		Assert.assertEquals(0, polyDerived.order());
		
		Complex[] factors = polyDerived.getFactors();
		
		Assert.assertTrue(Math.abs(factors[0].getRe() - 0) < THRESHOLD);
		Assert.assertTrue(Math.abs(factors[0].getIm() - 0) < THRESHOLD);
	}
	
	@Test
	public void testMultiply() {
		ComplexPolynomial poly1 = new ComplexPolynomial(new Complex(5, 0),
												new Complex(3, 0),
												new Complex(0, 0),
												new Complex(1, 0));
		
		ComplexPolynomial poly2 = new ComplexPolynomial(new Complex(-3, 0),
												new Complex(1, 0));
		
		ComplexPolynomial poly = poly1.multiply(poly2);
		
		Assert.assertEquals(4, poly.order());
		
		Complex[] factors = poly.getFactors();
		
		Assert.assertTrue(Math.abs(factors[0].getRe() + 15) < THRESHOLD);
		Assert.assertTrue(Math.abs(factors[0].getIm() - 0) < THRESHOLD);
		
		Assert.assertTrue(Math.abs(factors[1].getRe() + 4) < THRESHOLD);
		Assert.assertTrue(Math.abs(factors[1].getIm() - 0) < THRESHOLD);
		
		Assert.assertTrue(Math.abs(factors[2].getRe() - 3) < THRESHOLD);
		Assert.assertTrue(Math.abs(factors[2].getIm() - 0) < THRESHOLD);
		
		Assert.assertTrue(Math.abs(factors[3].getRe() + 3) < THRESHOLD);
		Assert.assertTrue(Math.abs(factors[3].getIm() - 0) < THRESHOLD);
		
		Assert.assertTrue(Math.abs(factors[4].getRe() - 1) < THRESHOLD);
		Assert.assertTrue(Math.abs(factors[4].getIm() - 0) < THRESHOLD);	
	}
	
	@Test
	public void testApply() {
		ComplexPolynomial poly = new ComplexPolynomial(new Complex(3, 2),
										new Complex(4, 1),
										new Complex(3, 0),
										new Complex(2, 2));
		
		Complex c = poly.apply(new Complex(1, 0));
		
		Assert.assertTrue(Math.abs(c.getRe() - 12) < THRESHOLD);
		Assert.assertTrue(Math.abs(c.getIm() - 5) < THRESHOLD);
	}
	
	@Test
	public void testApplyOnZeroOrderyPolynomial() {
		ComplexPolynomial poly = new ComplexPolynomial(new Complex(-23, -2));

		Complex c = poly.apply(new Complex(11, -3));

		Assert.assertTrue(Math.abs(c.getRe() + 23) < THRESHOLD);
		Assert.assertTrue(Math.abs(c.getIm() + 2) < THRESHOLD);
	}
	
}

package hr.fer.zemris.java.hw02;

import org.junit.Assert;
import org.junit.Test;

public class ComplexNumberTest {

	private static double THRESHOLD = 1E-6;

	@Test
	public void testFromReal() {
		ComplexNumber c = ComplexNumber.fromReal(2.14);

		Assert.assertTrue(Math.abs(c.getReal() - 2.14) < THRESHOLD);
		Assert.assertTrue(Math.abs(c.getImaginary() - 0) < THRESHOLD);
	}

	@Test
	public void testFromImaginary() {
		ComplexNumber c = ComplexNumber.fromImaginary(5.12341);

		Assert.assertTrue(Math.abs(c.getReal() - 0) < THRESHOLD);
		Assert.assertTrue(Math.abs(c.getImaginary() - 5.12341) < THRESHOLD);
	}

	@Test
	public void testFromMagnitudeAndAngle() {
		ComplexNumber c = ComplexNumber.fromMagnitudeAndAngle(1, Math.PI / 2);

		Assert.assertTrue(Math.abs(c.getReal() - 0) < THRESHOLD);
		Assert.assertTrue(Math.abs(c.getImaginary() - 1) < THRESHOLD);
	}

	@Test
	public void testParse() {
		ComplexNumber c = ComplexNumber.parse("2.145+3.12i");

		Assert.assertTrue(Math.abs(c.getReal() - 2.145) < THRESHOLD);
		Assert.assertTrue(Math.abs(c.getImaginary() - 3.12) < THRESHOLD);
	}

	@Test
	public void testParseWhenImaginaryZero() {
		ComplexNumber c = ComplexNumber.parse("2.1633");

		Assert.assertTrue(Math.abs(c.getReal() - 2.1633) < THRESHOLD);
		Assert.assertTrue(Math.abs(c.getImaginary() - 0) < THRESHOLD);
	}

	@Test
	public void testParseWhenRealZero() {
		ComplexNumber c = ComplexNumber.parse("-21.143i");

		Assert.assertTrue(Math.abs(c.getReal() - 0) < THRESHOLD);
		Assert.assertTrue(Math.abs(c.getImaginary() - (-21.143)) < THRESHOLD);
	}

	@Test
	public void testParseWhenRealZeroAndImaginaryOne() {
		ComplexNumber c = ComplexNumber.parse("i");

		Assert.assertTrue(Math.abs(c.getReal() - 0) < THRESHOLD);
		Assert.assertTrue(Math.abs(c.getImaginary() - 1) < THRESHOLD);
	}

	@Test
	public void testParseWhenRealZeroAndImaginaryMinusOne() {
		ComplexNumber c = ComplexNumber.parse("-i");

		Assert.assertTrue(Math.abs(c.getReal() - 0) < THRESHOLD);
		Assert.assertTrue(Math.abs(c.getImaginary() - (-1)) < THRESHOLD);
	}

	@Test
	public void testGetReal() {
		ComplexNumber c = new ComplexNumber(4, 5);

		Assert.assertTrue(Math.abs(c.getReal() - 4) < THRESHOLD);
	}

	@Test
	public void testGetImaginary() {
		ComplexNumber c = new ComplexNumber(12, -22);

		Assert.assertTrue(Math.abs(c.getImaginary() - (-22)) < THRESHOLD);
	}

	@Test
	public void testGetMagnitude() {
		ComplexNumber c = new ComplexNumber(3, -4);

		Assert.assertTrue(
				Math.abs(5 - Math.sqrt(c.getReal() * c.getReal() + c.getImaginary() * c.getImaginary())) < THRESHOLD);
	}

	@Test
	public void testGetAngle() {
		ComplexNumber c = new ComplexNumber(2, 2);

		Assert.assertTrue(Math.abs(c.getAngle() - Math.PI / 4) < THRESHOLD);
	}

	@Test
	public void testAdd() {
		ComplexNumber c1 = new ComplexNumber(4, 20);
		ComplexNumber c2 = new ComplexNumber(7, 13);

		ComplexNumber c3 = c1.add(c2);

		Assert.assertTrue(Math.abs(c3.getReal() - 11) < THRESHOLD);
		Assert.assertTrue(Math.abs(c3.getImaginary() - 33) < THRESHOLD);
	}

	@Test
	public void testSub() {
		ComplexNumber c1 = new ComplexNumber(4, 20);
		ComplexNumber c2 = new ComplexNumber(7, 13);

		ComplexNumber c3 = c1.sub(c2);

		Assert.assertTrue(Math.abs(c3.getReal() - (-3)) < THRESHOLD);
		Assert.assertTrue(Math.abs(c3.getImaginary() - 7) < THRESHOLD);
	}

	@Test
	public void testMul() {
		ComplexNumber c1 = new ComplexNumber(2, 1);
		ComplexNumber c2 = new ComplexNumber(3, 2);

		ComplexNumber c3 = c1.mul(c2);

		Assert.assertTrue(Math.abs(c3.getReal() - 4) < THRESHOLD);
		Assert.assertTrue(Math.abs(c3.getImaginary() - 7) < THRESHOLD);
	}

	@Test
	public void testDiv() {
		ComplexNumber c1 = new ComplexNumber(2, 1);
		ComplexNumber c2 = new ComplexNumber(3, 2);

		ComplexNumber c3 = c1.div(c2);

		Assert.assertTrue(Math.abs(c3.getReal() - 8. / 13) < THRESHOLD);
		Assert.assertTrue(Math.abs(c3.getImaginary() - (-1. / 13)) < THRESHOLD);
	}

	@Test
	public void testPower() {
		ComplexNumber c = new ComplexNumber(4, 7);

		ComplexNumber c1 = c.power(5);

		Assert.assertTrue(Math.abs(c1.getReal() - 17684) < THRESHOLD);
		Assert.assertTrue(Math.abs(c1.getImaginary() - (-29113)) < THRESHOLD);
	}

	@Test
	public void testRoot() {
		ComplexNumber c = new ComplexNumber(8, 8);

		ComplexNumber[] tempArray = c.root(3);

		Assert.assertTrue(Math.abs(tempArray[1].getReal() - (-Math.pow(2, 2. / 3))) < THRESHOLD);
		Assert.assertTrue(Math.abs(tempArray[1].getImaginary() - Math.pow(2, 2. / 3)) < THRESHOLD);
	}

	@Test
	public void testToStringWhenImaginaryNegative() {
		ComplexNumber c = new ComplexNumber(3.125, -8);

		Assert.assertEquals("3.125-8.0i", c.toString());
	}

	@Test
	public void testToStringWhenImaginaryPositive() {
		ComplexNumber c = new ComplexNumber(3, 21.333);

		Assert.assertEquals("3.0+21.333i", c.toString());
	}
}

package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class representing complex numbers and provides basic operations like adding
 * and finding module of a complex number.
 * 
 * @author Rafael Josip Penić
 *
 */
public class Complex {

	/**
	 * Constants used when comparing two doubles.
	 */
	public static final double THRESHOLD = 1E-7;

	public static final Complex ZERO = new Complex(0, 0);
	public static final Complex ONE = new Complex(1, 0);
	public static final Complex ONE_NEG = new Complex(-1, 0);
	public static final Complex IM = new Complex(0, 1);
	public static final Complex IM_NEG = new Complex(0, -1);

	/**
	 * Real part of the complex number.
	 */
	private final double re;

	/**
	 * Imaginary part of the complex number.
	 */
	private final double im;

	/**
	 * Angle of the complex number.
	 */
	private final double angle;

	/**
	 * Module of the complex number.
	 */
	private final double mod;

	/**
	 * Default constructor for complex numbers which constructs complex number which
	 * real and imaginary part are equal zero
	 */
	public Complex() {
		this(0, 0);
	}

	/**
	 * Constructor for complex numbers which constructs complex number with given
	 * real and imaginary part.
	 * 
	 * @param re
	 *            real part
	 * @param im
	 *            imaginary part
	 */
	public Complex(double re, double im) {
		this.re = re;
		this.im = im;

		angle = Math.atan2(im, re);// calculating complex numbers angle

		mod = Math.sqrt(re * re + im * im);
	}

	/**
	 * Method that returns module of the complex number(|c| where c is complex
	 * number)
	 * 
	 * @return module of the complex number
	 */
	public double module() {
		return mod;
	}

	/**
	 * Method that multiplies two complex numbers and returns result without
	 * changing the arguments of the operation
	 * 
	 * @param c
	 *            complex number with which this complex number will be multiplied
	 * @return result of the multiplication
	 */
	public Complex multiply(Complex c) {
		Objects.requireNonNull(c, "Given object is null.");
		return new Complex(re * c.re - im * c.im, re * c.im + im * c.re);
	}

	/**
	 * Divides this complex number with the given complex number and returns result
	 * without changing the arguments of the operation
	 * 
	 * @param c
	 *            complex number with which this complex number will be divided
	 * @return result of the division
	 */
	public Complex divide(Complex c) {
		Objects.requireNonNull(c, "Given complex number is null.");

		return new Complex((re * c.re + im * c.im) / (c.re * c.re + c.im * c.im),
				(im * c.re - re * c.im) / (c.re * c.re + c.im * c.im));
	}

	/**
	 * Adds one complex number to another and returns result without changing the
	 * arguments of the operation
	 * 
	 * @param c
	 *            complex number to be added to this complex number
	 * @return result of the addition
	 */
	public Complex add(Complex c) {
		Objects.requireNonNull(c, "Given object is null.");
		return new Complex(re + c.re, im + c.im);
	}

	/**
	 * Subtracts given complex number from this complex number and returns result
	 * without changing the arguments of the operation
	 * 
	 * @param c
	 *            complex number to be subtracted from this complex number
	 * @return result of the subtraction
	 */
	public Complex sub(Complex c) {
		Objects.requireNonNull(c, "Given object is null.");
		return new Complex(re - c.re, im - c.im);
	}

	/**
	 * Method that constructs new complex number with opposite real and imaginary
	 * part from this complex number
	 * 
	 * @return result of negation
	 */
	public Complex negate() {
		return new Complex(-re, -im);
	}

	/**
	 * Method that powers the complex number by the given integer value.
	 * 
	 * @param n
	 *            number on which the complex number will be powered
	 * @return result of powering
	 * 
	 * @throws IllegalArgumentException
	 *             in case the given integer is negative
	 */
	public Complex power(int n) {
		if (n < 0)
			throw new IllegalArgumentException("Cannot do power operation when the given argument is negative.");

		double newMod = Math.pow(mod, n);

		return new Complex(newMod * Math.cos(n * angle), newMod * Math.sin(n * angle));
	}

	/**
	 * Method that finds n-th roots of the complex number and returns list
	 * containing the result
	 * 
	 * @param n
	 *            determines results of which root will be calculated
	 * @return list containing results of the rooting
	 * 
	 * @throws IllegalArgumentException
	 *             in case the given integer is not positive
	 */
	public List<Complex> root(int n) {
		if (n <= 0)
			throw new IllegalArgumentException("Cannot root complex number if the given argument is not positive.");

		List<Complex> resultList = new ArrayList<>();
		double newMod = Math.pow(mod, 1. / n);

		for (int i = 0; i < n; i++) {
			double tempAngle = (angle + 2 * i * Math.PI) / n;
			resultList.add(new Complex(newMod * Math.cos(tempAngle), newMod * Math.sin(tempAngle)));
		}

		return resultList;
	}

	/**
	 * Getter for real part of the complex number
	 * 
	 * @return real part
	 */
	public double getRe() {
		return re;
	}

	/**
	 * Getter for the imaginary part of the complex number
	 * 
	 * @return imaginary part
	 */
	public double getIm() {
		return im;
	}

	@Override
	public String toString() {
		return re + ((im < 0) ? " - " : " + ") + "i" + Math.abs(im);
	}
}

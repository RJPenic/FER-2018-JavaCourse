package hr.fer.zemris.java.hw02;

/**
 * This class defines complex numbers and gives static methods for constructing
 * them and methods for doing basic operations with them.
 * 
 * @author Rafael Josip Penić
 * @version 1.0
 *
 */
public class ComplexNumber {

	/*
	 * Constants
	 */
	public static final int ZERO = 0;
	public static final int ONE = 1;
	public static final int MINUS_ONE = -1;

	/**
	 * Real part of the complex number.
	 */
	private double real;

	/**
	 * Imaginary part of the complex number.
	 */
	private double imaginary;

	/**
	 * Constructor which allocates new complex number from given real numbers.
	 * 
	 * @param real
	 *            Real part of the constructed complex number
	 * @param imaginary
	 *            Imaginary part of the constructed complex number
	 */
	public ComplexNumber(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}

	/**
	 * Static method that constructs complex number which real part is equal to the
	 * given number and set imaginary part to 0
	 * 
	 * @param real
	 *            real part of the constructed complex number
	 * @return complex number which real part is equal the given number and
	 *         imaginary part is 0
	 */
	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real, ZERO);
	}

	/**
	 * Static method that constructs complex number which imaginary part is equal to
	 * the given number and the real part is 0
	 * 
	 * @param imaginary
	 *            imaginary part of the constructed complex number
	 * @return complex number which imaginary part is equal the given number and
	 *         real part is 0
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(ZERO, imaginary);
	}

	/**
	 * Static method that constructs complex number from the give magnitude and
	 * angle
	 * 
	 * @param magnitude
	 *            magnitude of the complex number
	 * @param angle
	 *            angle of the complex number
	 * @return complex number with the given magnitude and angle
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
		return new ComplexNumber(magnitude * Math.cos(angle), magnitude * Math.sin(angle));
	}

	/**
	 * Parses the given string as a complex number.
	 * 
	 * @param s
	 *            string containing ComplexNumber representation to be parsed
	 * @return complex number parsed from the given string
	 * @throws NumberFormatException
	 *             if the given string is invalid
	 */
	public static ComplexNumber parse(String s) {
		if (s.charAt(s.length() - ONE) != 'i') {
			try {
				return new ComplexNumber(Double.parseDouble(s), ZERO);
			} catch (NumberFormatException ex) {
				throw new IllegalArgumentException("Real part of complex number must be a real number.");
			}
		}

		int index = (s.lastIndexOf('-') > s.lastIndexOf('+')) ? s.lastIndexOf('-') : s.lastIndexOf('+');

		if (index < ONE) {
			if (s.substring(0, s.length() - ONE).equals("+") || s.equals("i")) {
				return new ComplexNumber(0, ONE);
			}

			if (s.substring(0, s.length() - ONE).equals("-")) {
				return new ComplexNumber(0, MINUS_ONE);
			}

			try {
				return new ComplexNumber(ZERO, Double.parseDouble(s.substring(ZERO, s.length() - 1)));
			} catch (NumberFormatException ex) {
				throw new IllegalArgumentException("Imaginary part of complex number must be a real number.");
			}
		}

		try {
			return new ComplexNumber(Double.parseDouble(s.substring(ZERO, index)),
					Double.parseDouble(s.substring(index, s.length() - ONE)));
		} catch (NumberFormatException ex) {
			throw new IllegalArgumentException("Real and imaginary part of complex number must be real numbers");
		}
	}

	/**
	 * Method that returns real part of the complex number
	 * 
	 * @return real part of the complex number
	 */
	public double getReal() {
		return real;
	}

	/**
	 * Method that returns imaginary part of the complex number
	 * 
	 * @return imaginary part of the complex number
	 */
	public double getImaginary() {
		return imaginary;
	}

	/**
	 * Calculates and returns magnitude of the complex number
	 * 
	 * @return magnitude of the complex number
	 */
	public double getMagnitude() {
		return Math.sqrt(real * real + imaginary * imaginary);
	}

	/**
	 * Calculates and returns angle of the complex number
	 * 
	 * @return angle of the complex number
	 */
	public double getAngle() {
		double angle = Math.atan(imaginary / real);

		if ((real < ZERO && imaginary > ZERO) || real < ZERO && imaginary < ZERO) {
			angle += Math.PI;
		}

		return angle;
	}

	/**
	 * Adds given complex number and this complex number
	 * 
	 * @param c
	 *            complex number that will be added to this complex number
	 * @return result of the addition
	 */
	public ComplexNumber add(ComplexNumber c) {
		return new ComplexNumber(real + c.real, imaginary + c.imaginary);
	}

	/**
	 * Substracts this complex number and the given complex number
	 * 
	 * @param c
	 *            complex number that will be substracted from this complex number
	 * @return result of the substraction
	 */
	public ComplexNumber sub(ComplexNumber c) {
		return new ComplexNumber(real - c.real, imaginary - c.imaginary);
	}

	/**
	 * Multiplies this complex number with the given complex number
	 * 
	 * @param c
	 *            complex number that will be multiplied with this complex number
	 * @return result of multiplication
	 */
	public ComplexNumber mul(ComplexNumber c) {
		return new ComplexNumber(real * c.real - imaginary * c.imaginary, real * c.imaginary + imaginary * c.real);
	}

	/**
	 * Divides this complex number with the complex number in the argument.
	 * 
	 * @param c
	 *            Complex number with which this complex number will be divided with
	 * @return result of partition
	 */
	public ComplexNumber div(ComplexNumber c) {
		return fromMagnitudeAndAngle(this.getMagnitude() / c.getMagnitude(), this.getAngle() - c.getAngle());
	}

	/**
	 * Calculates powers of complex numbers.
	 * 
	 * @param n
	 *            power that will be calculated
	 * @return n-th power of this complex number
	 * @throws IllegalArgumentException
	 *             if the argument is negative
	 */
	public ComplexNumber power(int n) {
		if (n < ZERO) {
			throw new IllegalArgumentException("Powering complex number with negative number is not allowed.");
		}

		return fromMagnitudeAndAngle(Math.pow(getMagnitude(), n), n * getAngle());
	}

	/**
	 * Calculates roots of this complex number
	 * 
	 * @param n
	 *            root which will be calculated
	 * @return array of complex numbers gotten as result of rooting this complex
	 *         number
	 * @throws IllegalArgumentException
	 *             if n is zero or negative
	 */
	public ComplexNumber[] root(int n) {
		if (n <= ZERO) {
			throw new IllegalArgumentException("Calculating n-th root where n is zero or negative is not allowed.");
		}

		ComplexNumber[] tempArray = new ComplexNumber[n];

		for (int i = ZERO; i < n; i++) {
			tempArray[i] = fromMagnitudeAndAngle(Math.pow(getMagnitude(), 1. / n), (getAngle() + 2 * Math.PI * i) / n);
		}

		return tempArray;
	}

	/**
	 * Method that returns string representation of this complex number.
	 * 
	 * @return string representing this complex number
	 */
	public String toString() {
		return real + ((imaginary < ZERO) ? "" : "+") + imaginary + "i";
	}
}

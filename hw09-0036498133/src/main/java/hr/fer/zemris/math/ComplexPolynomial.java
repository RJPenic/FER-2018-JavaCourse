package hr.fer.zemris.math;

import java.util.Objects;

/**
 * Models polynomials with complex numbers. Complex polynomial looks like this :
 * zn*z^n + zn-1*z^n-1 + ... + z1*z + z0 where z1,z2,...,zn are factors of the
 * polynomial.
 * 
 * @author Rafael Josip Penić
 *
 */
public class ComplexPolynomial {

	/**
	 * Array containing factors of the polynomial.
	 */
	private final Complex[] factors;

	/**
	 * Constructor for Complex polynomials which constructs polynomial with the
	 * given factors
	 * 
	 * @param factors
	 *            factors of the polynomial that will be constructed
	 * 
	 * @throws IllegalArgumentException
	 *             in case there are no given factors
	 */
	public ComplexPolynomial(Complex... factors) {
		Objects.requireNonNull(factors, "Given argument is null");

		if (factors.length == 0)
			throw new IllegalArgumentException("No arguments given.");

		this.factors = factors;
	}

	/**
	 * Method that returns order of this polynomial; eg. For (7+2i)z^3+2z^2+5z+1
	 * returns 3
	 * 
	 * @return order of the polynomial
	 */
	public short order() {
		return (short) (factors.length - 1);
	}

	/**
	 * Computes a new polynomial this*p
	 * 
	 * @param p
	 *            polynomial with which this polynomial will be multiplied
	 * @return result of multiplication
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		Complex[] resultFactors = new Complex[this.order() + p.order() + 1];

		for (int i = 0; i < factors.length; i++) {
			for (int j = 0; j < p.factors.length; j++) {
				if (resultFactors[i + j] == null) {
					resultFactors[i + j] = new Complex();
				}
				resultFactors[i + j] = resultFactors[i + j].add(this.factors[i].multiply(p.factors[j]));
			}
		}

		return new ComplexPolynomial(resultFactors);
	}

	/**
	 * Computes first derivative of this polynomial; for example, for
	 * (7+2i)z^3+2z^2+5z+1 returns (21+6i)z^2+4z+5
	 * 
	 * @return polynomial that is gotten as result of derivation of this polynomial
	 */
	public ComplexPolynomial derive() {
		if(factors.length == 1) {
			return new ComplexPolynomial(new Complex(0,0));
		}
		
		Complex[] resultFactors = new Complex[factors.length - 1];

		for (int i = 0; i < resultFactors.length; i++) {
			resultFactors[i] = factors[i + 1].multiply(new Complex(i + 1, 0));
		}

		return new ComplexPolynomial(resultFactors);
	}

	/**
	 * Computes polynomial value at given point
	 * 
	 * @param z
	 *            point in which the value of the polynomial will be calculated
	 * @return calculated value of the polynomial in the point z
	 */
	public Complex apply(Complex z) {
		Complex result = new Complex();

		for (int i = 0; i < factors.length; i++) {
			result = result.add(factors[i].multiply(z.power(i)));
		}

		return result;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		int numberOfFactors = factors.length;

		for (int i = numberOfFactors - 1; i >= 0; i--) {
			sb.append("(" + factors[i] + ") z^" + i);

			if (i != 0) {
				sb.append(" + ");
			}
		}

		return sb.toString();
	}

	/**
	 * Getter for factors of the polynomial
	 * 
	 * @return array containing factors of polynomial
	 */
	public Complex[] getFactors() {
		return factors.clone();
	}
}

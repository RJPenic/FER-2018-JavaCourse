package hr.fer.zemris.math;

import java.util.Objects;

/**
 * Models polynomials with complex numbers. Complex rooted polynomial looks like this :
 * (z-z1)*(z-z2)*...*(z-zn) where z1,z2,...,zn are nullpoints of the polynomial
 * 
 * @author Rafael Josip Penić
 *
 */
public class ComplexRootedPolynomial {

	/**
	 * Array containing roots(nullpoints) of the polynomial
	 */
	private final Complex[] roots;

	/**
	 * Constructor for Rooted complex polynomial which constructs polynomial with
	 * the given roots
	 * 
	 * @param roots
	 *            roots of the polynomial that is constructed
	 * 
	 * @throws IllegalArgumentException
	 *             in case the length of the given array is zero
	 */
	public ComplexRootedPolynomial(Complex... roots) {
		Objects.requireNonNull(roots, "Given roots are null");

		if (roots.length == 0)
			throw new IllegalArgumentException("No arguments given.");

		this.roots = roots;
	}

	/**
	 * Computes polynomial value at given point
	 * 
	 * @param z
	 *            point in which the value of the polynomial will be computed
	 * @return result gotten when "inserting" given complex number into polynomial
	 */
	public Complex apply(Complex z) {
		Objects.requireNonNull(z, "Given complex number is null.");
		Complex result = Complex.ONE;

		for (Complex root : roots) {
			result = result.multiply(z.sub(root));
		}

		return result;
	}

	/**
	 * Method that converts Rooted complex polynomial into classic complex
	 * polynomial form
	 * 
	 * @return corresponding ComplexPolynomial to the ComplexRootedPolynomial
	 */
	public ComplexPolynomial toComplexPolynom() {
		ComplexPolynomial result = new ComplexPolynomial(new Complex(1, 0));

		for (int i = 0; i < roots.length; i++) {
			result = result.multiply(new ComplexPolynomial(roots[i].negate(), new Complex(1, 0)));
		}

		return result;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		int sizeOfList = roots.length;
		for (int i = 0; i < sizeOfList; i++) {
			sb.append("(z - (" + roots[i] + "))");
			if (i != sizeOfList - 1) {
				sb.append(" * ");
			}
		}

		return sb.toString();
	}

	/**
	 * finds index of closest root for given complex number that is within given
	 * threshold; if there is no such root, returns -1
	 * 
	 * @param z
	 *            complex number for which the closest root will be found(if there
	 *            is any root in the given threshold)
	 * @param threshold
	 *            number that determines in which radius around z, roots will be
	 *            searched for
	 * @return index of the closest root that is within given threshold or -1 if
	 *         there is no such root
	 */
	public int indexOfClosestRootFor(Complex z, double threshold) {
		Objects.requireNonNull(z, "Given complex number is null.");
		int sizeOfList = roots.length;
		int minIndex = -1;
		double minDistance = 0;
		boolean firstFound = false;

		for (int i = 0; i < sizeOfList; i++) {
			double dist = calculateDistance(roots[i], z);

			if (dist <= threshold) {
				if (!firstFound) {
					minIndex = i;
					minDistance = dist;
					firstFound = true;
				} else {
					if (dist < minDistance) {
						minIndex = i;
						minDistance = dist;
					}
				}
			}
		}

		return minIndex;
	}

	/**
	 * Method that calculates distance between points representing two complex
	 * number
	 * 
	 * @param z1
	 *            first complex number
	 * @param z2
	 *            second complex number
	 * @return distance between z2 and z1
	 */
	private double calculateDistance(Complex z1, Complex z2) {
		Objects.requireNonNull(z1, "First argument is null.");
		Objects.requireNonNull(z2, "Second argument is null.");
		
		return z1.sub(z2).module();
	}
}

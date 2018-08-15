package hr.fer.zemris.java.fractals;

import java.util.concurrent.Callable;

import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * Class implementing interface Callable and represents a calculating and
 * drawing process.
 * 
 * @author Rafael Josip Penić
 *
 */
public class CalculatingProcess implements Callable<Void> {

	private static final double MODULE_LIMIT = 1E-3;
	private static final double ROOT_CLOSENESS_LIMIT = 1E-3;

	/**
	 * Minimal real part of the complex number
	 */
	private double reMin;

	/**
	 * Maximal real part of the complex number
	 */
	private double reMax;

	/**
	 * Minimal imaginary part of the complex number
	 */
	private double imMin;

	/**
	 * Maximal imaginary part of the complex number
	 */
	private double imMax;

	/**
	 * Width of the screen
	 */
	private int width;

	/**
	 * Height of the screen
	 */
	private int height;

	/**
	 * Minimal y
	 */
	private int yMin;

	/**
	 * Maximum y
	 */
	private int yMax;

	/**
	 * Number of iterations
	 */
	private int m;

	/**
	 * Data storage array where informations about color will be stored
	 */
	private short[] data;

	/**
	 * Fractals polynomial
	 */
	private ComplexRootedPolynomial poly;

	/**
	 * Constructor for CalculatingProcess objects
	 * 
	 * @param reMin
	 *            minimal real part of the complex number
	 * @param reMax
	 *            maximal real part of the complex number
	 * @param imMin
	 *            minimal imaginary part of the complex number
	 * @param imMax
	 *            maximal imaginary part of the complex number
	 * @param width
	 *            width of screen
	 * @param height
	 *            height of screen
	 * @param yMin
	 *            minimal y
	 * @param yMax
	 *            maximal y
	 * @param m
	 *            number of iterations
	 * @param data
	 *            data array
	 * @param poly
	 *            fractals polynomial
	 */
	public CalculatingProcess(double reMin, double reMax, double imMin, double imMax, int width, int height, int yMin,
			int yMax, int m, short[] data, ComplexRootedPolynomial poly) {
		super();
		this.reMin = reMin;
		this.reMax = reMax;
		this.imMin = imMin;
		this.imMax = imMax;
		this.width = width;
		this.height = height;
		this.yMin = yMin;
		this.yMax = yMax;
		this.m = m;
		this.data = data;
		this.poly = poly;
	}

	@Override
	public Void call() {

		for (int y = yMin; y <= yMax; y++) {
			for (int x = 0; x <= width - 1; x++) {
				Complex c = map_to_complex_plain(x, y, width, height, reMin, reMax, imMin, imMax);
				Complex zn = c;
				int i = 0;
				double module;
				ComplexPolynomial derived = poly.toComplexPolynom().derive();
				Complex zn1;

				do {
					Complex numerator = poly.apply(zn);
					Complex denominator = derived.apply(zn);
					Complex fraction = numerator.divide(denominator);
					zn1 = zn.sub(fraction);
					module = zn1.sub(zn).module();
					zn = zn1;
					i++;
				} while (module > MODULE_LIMIT && i < m);

				int index = poly.indexOfClosestRootFor(zn1, ROOT_CLOSENESS_LIMIT);

				if (index == -1) {
					data[x + y * width] = 0;
				} else {
					data[x + y * width] = (short) (index + 1);
				}
			}
		}

		return null;
	}

	/**
	 * Converts given coordinates into complex plain
	 * 
	 * @param x
	 *            x-component of the coordinate
	 * @param y
	 *            y-component of the coordinate
	 * @param width
	 *            width of the screen
	 * @param height
	 *            height of the screen
	 * @param reMin
	 *            minimal real part of the complex number
	 * @param reMax
	 *            maximal real part of the complex number
	 * @param imMin
	 *            minimal imaginary part of the complex number
	 * @param imMax
	 *            maximal imaginary part of the complex number
	 * @return complex number gotten as result of mapping the given coordinates
	 */
	private static Complex map_to_complex_plain(int x, int y, int width, int height, double reMin, double reMax,
			double imMin, double imMax) {
		double cre = x / (width - 1.0) * (reMax - reMin) + reMin;
		double cim = (height - 1.0 - y) / (height - 1) * (imMax - imMin) + imMin;

		return new Complex(cre, cim);
	}

}

package hr.fer.zemris.math;

import java.util.Objects;

/**
 * Class representing 3D vector which is determined with certain x, y and z
 * coordinate.
 * 
 * @author Rafael Josip Penić
 *
 */
public class Vector3 {

	/**
	 * Constant used when comparing doubles
	 */
	private static final double THRESHOLD = 1E-7;

	/**
	 * x-component of the vector
	 */
	private final double x;

	/**
	 * y-component of the vector
	 */
	private final double y;

	/**
	 * z-component of the vector
	 */
	private final double z;

	/**
	 * Constructor which constructs vector with given x,y and z components
	 * 
	 * @param x
	 *            x-component of the vector
	 * @param y
	 *            y-component of the vector
	 * @param z
	 *            z-component of the vector
	 */
	public Vector3(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Calculates norm(length) of the vector
	 * 
	 * @return norm
	 */
	public double norm() {
		return Math.sqrt(x * x + y * y + z * z);
	}

	/**
	 * Constructs and returns normalized vector of this vector.
	 * 
	 * @return normalized vector.
	 */
	public Vector3 normalized() {
		double length = this.norm();

		return new Vector3(x / length, y / length, z / length);
	}

	/**
	 * Adds one vector to another(without changing arguments of the operation)
	 * 
	 * @param other
	 *            vector to be added to this vector
	 * @return result of addition
	 */
	public Vector3 add(Vector3 other) {
		Objects.requireNonNull(other, "Given argument is null.");
		return new Vector3(x + other.x, y + other.y, z + other.z);
	}

	/**
	 * Subtracts one vector to another(without changing arguments of the operation)
	 * 
	 * @param other
	 *            vector to be subtracted from this vector
	 * @return result of subtraction
	 */
	public Vector3 sub(Vector3 other) {
		Objects.requireNonNull(other, "Given argument is null.");
		return new Vector3(x - other.x, y - other.y, z - other.z);
	}

	/**
	 * Calculates and returns scalar product of two vectors
	 * 
	 * @param other
	 *            vector that will, along this vector, be used to calculate scalar
	 *            product
	 * @return result of the scalar product
	 */
	public double dot(Vector3 other) {
		Objects.requireNonNull(other, "Given argument is null.");
		return x * other.x + y * other.y + z * other.z;
	}

	/**
	 * Calculates and returns vector product of two vectors(without changing
	 * arguments of the operation)
	 * 
	 * @param other
	 *            vector that will, along this vector, be used to calculate vector
	 *            product
	 * @return result of vector product
	 */
	public Vector3 cross(Vector3 other) {
		Objects.requireNonNull(other, "Given argument is null.");
		return new Vector3(y * other.z - z * other.y, z * other.x - x * other.z, x * other.y - y * other.x);
	}

	/**
	 * Returns new vector that is this vector scaled by given factor
	 * 
	 * @param s
	 *            scaling factor
	 * @return vector gotten as result of scaling
	 */
	public Vector3 scale(double s) {
		return new Vector3(x * s, y * s, z * s);
	}

	/**
	 * Calculate cosine of the angle between this and other vector
	 * 
	 * @param other
	 *            vector which closes certain angle with this vector
	 * @return cosine of the angle between this and other vector
	 */
	public double cosAngle(Vector3 other) {
		Objects.requireNonNull(other, "Given argument is null.");

		double dotProductOfComponents = this.dot(other);
		double productOfLengths = this.norm() * other.norm();

		if (Math.abs(productOfLengths - 0) < THRESHOLD) // comparing to zero
			throw new IllegalArgumentException(
					"It is impossible to determine angle between vectors if one(or two) of them is null vector.");

		return dotProductOfComponents / productOfLengths;
	}

	/**
	 * Getter for x-component of the vector
	 * 
	 * @return x-component
	 */
	public double getX() {
		return x;
	}

	/**
	 * Getter for y-component of the vector
	 * 
	 * @return y-component
	 */
	public double getY() {
		return y;
	}

	/**
	 * Getter for z-component of the vector
	 * 
	 * @return z-component
	 */
	public double getZ() {
		return z;
	}

	/**
	 * Method that constructs and returns array of doubles containing x, y and z
	 * component of the vector
	 * 
	 * @return array containing components of the vector
	 */
	public double[] toArray() {
		return new double[] { x, y, z };
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ", " + z + ")";
	}

}
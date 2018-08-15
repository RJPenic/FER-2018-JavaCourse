package hr.fer.zemris.math;

public class Vector2D {

	/**
	 * Threshold that is used when determining if the two vectors are equal.
	 */
	public static final double THRESHOLD = 1E-6;

	/**
	 * X-component of the vector.
	 */
	private double x;

	/**
	 * Y-component of the vector.
	 */
	private double y;

	/**
	 * Constructor for 2-dimensional vector.
	 * 
	 * @param x
	 *            x-component of the vector that will be created.
	 * @param y
	 *            y-component of the vector that will be created.
	 */
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Getter for x-component of the vector.
	 * 
	 * @return x-component of the vector.
	 */
	public double getX() {
		return x;
	}

	/**
	 * Getter for y-component of the vector.
	 * 
	 * @return y-component of the vector.
	 */
	public double getY() {
		return y;
	}

	/**
	 * Method that translates vector for the given vector.
	 * 
	 * @param offset
	 *            vector that will determine where and how far the vector will be
	 *            translated.
	 */
	public void translate(Vector2D offset) {
		if(offset == null) throw new NullPointerException("It is not allowed for offset vector to be null");
			
		x = x + offset.x;
		y = y + offset.y;
	}

	/**
	 * Method that returns new translated vector.
	 * 
	 * @param offset
	 *            vector that will determine where and how far the new vector will
	 *            be translated.
	 * @return new 2-dimensional vector gotten as result of translation.
	 */
	public Vector2D translated(Vector2D offset) {
		if(offset == null) throw new NullPointerException("It is not allowed for offset vector to be null");
		
		return new Vector2D(x + offset.x, y + offset.y);
	}

	/**
	 * Method that rotates the vector for the given angle.
	 * 
	 * @param angle
	 *            angle of rotation(in degrees!)
	 */
	public void rotate(double angle) {
		double radianAngle = angle * Math.PI / 180;

		double tempX = x * Math.cos(radianAngle) - y * Math.sin(radianAngle);
		y = x * Math.sin(radianAngle) + y * Math.cos(radianAngle);
		x = tempX;
	}

	/**
	 * Method that produces a new vector gotten as result of rotation.
	 * 
	 * @param angle
	 *            angle of rotation(in degrees!)
	 * @return result of the vector-rotation
	 */
	public Vector2D rotated(double angle) {
		double radianAngle = angle * Math.PI / 180;

		return new Vector2D(x * Math.cos(radianAngle) - y * Math.sin(radianAngle),
				x * Math.sin(radianAngle) + y * Math.cos(radianAngle));
	}

	/**
	 * Method that scales vector for a certain factor.
	 * 
	 * @param scaler
	 *            factor of scaling.
	 */
	public void scale(double scaler) {
		x *= scaler;
		y *= scaler;
	}

	/**
	 * Method that produces new vector gotten as result of scaling.
	 * 
	 * @param scaler
	 *            factor of scaling.
	 * @return vector gotten as a result of scaling.
	 */
	public Vector2D scaled(double scaler) {
		return new Vector2D(x * scaler, y * scaler);
	}

	/**
	 * Method that produces a copy of the vector.
	 * 
	 * @return copy of the vector.
	 */
	public Vector2D copy() {
		return new Vector2D(x, y);
	}

	/**
	 * Method that calculates the length of the vector.
	 * 
	 * @return length of the vector.
	 */
	public double length() {
		return Math.sqrt(x * x + y * y);
	}

	@Override
	public boolean equals(Object obj) {	//made mainly because of testing purposes
		if (!(obj instanceof Vector2D) || obj == null)
			return false;

		Vector2D other = (Vector2D) obj;

		return Math.abs(x - other.x) < THRESHOLD && Math.abs(y - other.y) < THRESHOLD;
	}
}

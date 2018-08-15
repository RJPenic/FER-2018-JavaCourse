package hr.fer.zemris.java.gui.charts;

/**
 * Class representing a single pair of (x,y) values
 * 
 * @author Rafael Josip Penić
 *
 */
public class XYValue {

	/**
	 * x-axis value
	 */
	private final int x;

	/**
	 * y-axis value
	 */
	private final int y;

	/**
	 * Constructor for XYValue objects
	 * 
	 * @param x
	 *            x-axis coordinate
	 * @param y
	 *            y-axis value
	 */
	public XYValue(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	/**
	 * Getter for x
	 * 
	 * @return x
	 */
	public int getX() {
		return x;
	}

	/**
	 * Getter for y
	 * 
	 * @return y
	 */
	public int getY() {
		return y;
	}

}

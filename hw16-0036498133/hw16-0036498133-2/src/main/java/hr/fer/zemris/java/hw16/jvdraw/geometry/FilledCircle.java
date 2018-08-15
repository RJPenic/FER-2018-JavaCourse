package hr.fer.zemris.java.hw16.jvdraw.geometry;

import java.awt.Color;
import java.awt.Point;

import hr.fer.zemris.java.hw16.jvdraw.editors.FilledCircleEditor;
import hr.fer.zemris.java.hw16.jvdraw.editors.GeometricalObjectEditor;

/**
 * Class representing a filled circle. It extends Circle class
 * 
 * @author Rafael Josip Penić
 *
 */
public class FilledCircle extends Circle {

	/**
	 * background color
	 */
	private Color backgroundColor;

	/**
	 * Constructor
	 * 
	 * @param center
	 *            circle center
	 * @param radius
	 *            circle radius
	 * @param lineColor
	 *            circle outline color
	 * @param backgroundColor
	 *            circle fill color
	 */
	public FilledCircle(Point center, int radius, Color lineColor, Color backgroundColor) {
		super(center, radius, lineColor);

		this.backgroundColor = backgroundColor;
	}

	/**
	 * Getter for background color
	 * 
	 * @return background color
	 */
	public Color getBackgroundColor() {
		return backgroundColor;
	}

	/**
	 * Setter for background color
	 * 
	 * @param backgroundColor
	 *            new background color
	 */
	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}

	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new FilledCircleEditor(this);
	}

	@Override
	public String toString() {
		return String.format("Filled circle (%d, %d), %d, %s", getCenter().x, getCenter().y, getRadius(),
				getHexaColor());
	}

	/**
	 * Method that constructs a hexadecimal representation of current background
	 * color
	 * 
	 * @return hexadecimal representation of the current background color
	 */
	private String getHexaColor() {
		return String.format("#%02x%02x%02x", backgroundColor.getRed(), backgroundColor.getGreen(),
				backgroundColor.getBlue()).toUpperCase();
	}

	@Override
	public String constructStringForFile() {
		return String.format("FCIRCLE %d %d %d %d %d %d %d %d %d", getCenter().x, getCenter().y, getRadius(),
				getLineColor().getRed(), getLineColor().getGreen(), getLineColor().getBlue(),
				getBackgroundColor().getRed(), getBackgroundColor().getGreen(), getBackgroundColor().getBlue());

	}
}

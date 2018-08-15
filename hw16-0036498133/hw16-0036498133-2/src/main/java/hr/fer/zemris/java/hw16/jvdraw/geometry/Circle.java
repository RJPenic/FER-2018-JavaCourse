package hr.fer.zemris.java.hw16.jvdraw.geometry;

import java.awt.Color;
import java.awt.Point;
import java.util.Objects;

import hr.fer.zemris.java.hw16.jvdraw.editors.CircleEditor;
import hr.fer.zemris.java.hw16.jvdraw.editors.GeometricalObjectEditor;

/**
 * Class representing a circle
 * 
 * @author Rafael Josip Penić
 *
 */
public class Circle extends GeometricalObject {

	/**
	 * Circle center
	 */
	private Point center;

	/**
	 * Circle radius
	 */
	private int radius;

	/**
	 * Circle outline color
	 */
	private Color lineColor;

	/**
	 * Constructor
	 * 
	 * @param center
	 *            circle center
	 * @param radius
	 *            circle radius
	 * @param lineColor
	 *            outline color
	 */
	public Circle(Point center, int radius, Color lineColor) {
		Objects.requireNonNull(center, "Given center point reference is null");
		Objects.requireNonNull(lineColor, "Given line color reference is null");

		this.center = center;
		this.radius = radius;
		this.lineColor = lineColor;
	}

	/**
	 * Getter for circle center
	 * 
	 * @return center
	 */
	public Point getCenter() {
		return center;
	}

	/**
	 * Setter for circle center
	 * 
	 * @param center
	 *            new center
	 */
	public void setCenter(Point center) {
		this.center = center;
	}

	/**
	 * Getter for circle radius
	 * 
	 * @return radius
	 */
	public int getRadius() {
		return radius;
	}

	/**
	 * Setter for circle radius
	 * 
	 * @param radius
	 *            new radius
	 */
	public void setRadius(int radius) {
		this.radius = radius;
	}

	/**
	 * Getter for outline color
	 * 
	 * @return outline color
	 */
	public Color getLineColor() {
		return lineColor;
	}

	/**
	 * Setter for outline color
	 * 
	 * @param lineColor
	 *            new outline color
	 */
	public void setLineColor(Color lineColor) {
		this.lineColor = lineColor;
	}

	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}

	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new CircleEditor(this);
	}

	@Override
	public String toString() {
		return String.format("Circle (%d, %d), %d", center.x, center.y, radius);
	}

	@Override
	public String constructStringForFile() {
		return String.format("CIRCLE %d %d %d %d %d %d", center.x, center.y, radius, lineColor.getRed(),
				lineColor.getGreen(), lineColor.getBlue());
	}
}

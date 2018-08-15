package hr.fer.zemris.java.hw16.jvdraw.geometry;

import java.awt.Color;
import java.awt.Point;
import java.util.Objects;

import hr.fer.zemris.java.hw16.jvdraw.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.editors.LineEditor;

/**
 * Class representing a simple line
 * 
 * @author Rafael Josip Penić
 *
 */
public class Line extends GeometricalObject {

	/**
	 * Start point of the line
	 */
	private Point lineStart;

	/**
	 * End point of the line
	 */
	private Point lineEnd;

	/**
	 * Color of the line
	 */
	private Color lineColor;

	/**
	 * Constructor
	 * 
	 * @param lineStart
	 *            line start point
	 * @param lineEnd
	 *            line end point
	 * @param lineColor
	 *            line color
	 */
	public Line(Point lineStart, Point lineEnd, Color lineColor) {
		Objects.requireNonNull(lineStart, "Given line start point reference is null");
		Objects.requireNonNull(lineEnd, "Given end line point reference is null");
		Objects.requireNonNull(lineColor, "Given line color reference is null");

		this.lineColor = lineColor;
		this.lineEnd = lineEnd;
		this.lineStart = lineStart;
	}

	/**
	 * Getter for line start point
	 * 
	 * @return line start point
	 */
	public Point getLineStart() {
		return lineStart;
	}

	/**
	 * Setter for line start point
	 * 
	 * @param lineStart
	 *            line start point
	 */
	public void setLineStart(Point lineStart) {
		Objects.requireNonNull(lineStart);
		this.lineStart = lineStart;
	}

	/**
	 * Getter for line end point
	 * 
	 * @return line end point
	 */
	public Point getLineEnd() {
		return lineEnd;
	}

	/**
	 * Setter for line end point
	 * 
	 * @param lineStart
	 *            line end point
	 */
	public void setLineEnd(Point lineEnd) {
		Objects.requireNonNull(lineEnd);
		this.lineEnd = lineEnd;
	}

	/**
	 * Getter for line color
	 * 
	 * @return line color
	 */
	public Color getLineColor() {
		return lineColor;
	}

	/**
	 * Setter for line color
	 * 
	 * @param lineColor
	 *            line color
	 */
	public void setLineColor(Color lineColor) {
		Objects.requireNonNull(lineColor);
		this.lineColor = lineColor;
	}

	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}

	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new LineEditor(this);
	}

	@Override
	public String toString() {
		return String.format("Line (%d, %d)-(%d,%d)", lineStart.x, lineStart.y, lineEnd.x, lineEnd.y);
	}

	@Override
	public String constructStringForFile() {
		return String.format("LINE %d %d %d %d %d %d %d", lineStart.x, lineStart.y, lineEnd.x, lineEnd.y,
				lineColor.getRed(), lineColor.getGreen(), lineColor.getBlue());
	}
}

package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

import hr.fer.zemris.math.Vector2D;

/**
 * Class that describes a turtle state and provides useful informations about
 * the turtles current state.
 * 
 * @author Rafael Josip Penić
 * @version 1.0
 *
 */
public class TurtleState {

	/**
	 * Current position of the turtle.
	 */
	private Vector2D currentPosition;

	/**
	 * Orientation of the turtle.
	 */
	private Vector2D turtleOrientation;

	/**
	 * Color of lines that the turtle draws.
	 */
	private Color color;

	/**
	 * Length of one step.
	 */
	private double shift;

	/**
	 * Constructor for turtle state.
	 * 
	 * @param currentPosition
	 *            position of the turtle
	 * @param turtleOrientation
	 *            turtle orientation
	 * @param color
	 *            color of the turtles lines
	 * @param shift
	 *            turtle shift
	 */
	public TurtleState(Vector2D currentPosition, Vector2D turtleOrientation, Color color, double shift) {
		super();
		this.currentPosition = currentPosition;
		this.turtleOrientation = turtleOrientation.scaled(1. / turtleOrientation.length());
		this.color = color;
		this.shift = shift;
	}

	/**
	 * Method that produces a copy of the turtle state
	 * 
	 * @return copy of the turtle state
	 */
	public TurtleState copy() {
		return new TurtleState(currentPosition.copy(), turtleOrientation.copy(), color, shift);
	}

	/**
	 * Getter for current position of the turtle
	 * 
	 * @return turtles current position
	 */
	public Vector2D getCurrentPosition() {
		return currentPosition;
	}

	/**
	 * Getter for turtle orientation.
	 * 
	 * @return turtle orientation
	 */
	public Vector2D getTurtleOrientation() {
		return turtleOrientation;
	}

	/**
	 * Getter for turtles current color.
	 * 
	 * @return line color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Getter for turtles shift value
	 * 
	 * @return double value of the shift
	 */
	public double getShift() {
		return shift;
	}

	/**
	 * Setter for the color variable
	 * 
	 * @param color
	 *            new color value
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * Setter for turtles shift variable
	 * 
	 * @param shift
	 *            new shift value
	 */
	public void setShift(double shift) {
		this.shift = shift;
	}

}

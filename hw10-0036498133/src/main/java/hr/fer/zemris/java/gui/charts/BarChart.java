package hr.fer.zemris.java.gui.charts;

import java.util.List;
import java.util.Objects;

/**
 * Class representing a simple BarChart graph with named x and y axis
 * 
 * @author Rafael Josip Penić
 *
 */
public class BarChart {

	/**
	 * List that stores XYValues. Each XYValue represents a single pillar on a
	 * BarChart.
	 */
	private List<XYValue> valuesList;

	/**
	 * name of the x-axis
	 */
	private String xDescription;

	/**
	 * name of the y-axis
	 */
	private String yDescription;

	/**
	 * Minimal value on y-axis
	 */
	private int yMin;

	/**
	 * Maximal value on y-axis
	 */
	private int yMax;

	/**
	 * Step between ticks on the y-axis
	 */
	private int yStep;

	/**
	 * Constructor for BarChart objects
	 * 
	 * @param valuesList
	 *            list of values
	 * @param xDescription
	 *            name of x-axis
	 * @param yDescription
	 *            name of y-axis
	 * @param yMin
	 *            minimal y value
	 * @param yMax
	 *            maximal y value
	 * @param yStep
	 *            step on the y-axis
	 */
	public BarChart(List<XYValue> valuesList, String xDescription, String yDescription, int yMin, int yMax, int yStep) {
		Objects.requireNonNull(valuesList, "Given list reference is null.");
		Objects.requireNonNull(xDescription, "Given x description string is null.");
		Objects.requireNonNull(yDescription, "Given y description string is null.");

		if (yMax < yMin)
			throw new IllegalArgumentException("Maximal y must be greater than minimal y.");

		if (yStep <= 0)
			throw new IllegalArgumentException("Step must be positive integer.");

		this.valuesList = valuesList;
		this.xDescription = xDescription;
		this.yDescription = yDescription;
		this.yMin = yMin;
		this.yMax = yMax;
		this.yStep = yStep;
	}

	/**
	 * Getter for values list
	 * 
	 * @return values list
	 */
	public List<XYValue> getValuesList() {
		return valuesList;
	}

	/**
	 * Getter for x-axis name
	 * 
	 * @return x-axis name
	 */
	public String getxDescription() {
		return xDescription;
	}

	/**
	 * Getter for y-axis name
	 * 
	 * @return y-axis name
	 */
	public String getyDescription() {
		return yDescription;
	}

	/**
	 * Getter for minimal y value
	 * 
	 * @return minimal y value
	 */
	public int getyMin() {
		return yMin;
	}

	/**
	 * Getter for maximal value on the y-axis
	 * 
	 * @return maximal y value
	 */
	public int getyMax() {
		return yMax;
	}

	/**
	 * Getter for the step on y-axis
	 * 
	 * @return step on the y-axis
	 */
	public int getyStep() {
		return yStep;
	}

}

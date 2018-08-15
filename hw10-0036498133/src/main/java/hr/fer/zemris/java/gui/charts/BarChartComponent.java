package hr.fer.zemris.java.gui.charts;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.util.List;
import java.util.Objects;

import javax.swing.JComponent;

/**
 * Class that represents a GUI representation of the BarChart
 * 
 * @author Rafael Josip Penić
 *
 */
public class BarChartComponent extends JComponent {

	/**
	 * Gap between axis name string and number strings of the axis
	 */
	private static int GAP1 = 30;

	/**
	 * Gap between number string of the axis and the axis itself
	 */
	private static int GAP2 = 20;

	/**
	 * Length of the pointer(arrow)
	 */
	private static int POINTER_LENGTH = 20;

	/**
	 * Gap between the end of the pointer the edge of the frame
	 */
	private static int GAP_AFTER_POINTER = 10;

	private static final long serialVersionUID = 1L;

	/**
	 * Corresponding bar chart which stores needed information
	 */
	private BarChart chart;

	/**
	 * Constructor for BarChart components
	 * 
	 * @param chart
	 *            chart according to which the GUI component will be constructed
	 */
	public BarChartComponent(BarChart chart) {
		Objects.requireNonNull(chart, "Given chart reference is null.");
		this.chart = chart;
	}

	@Override
	protected void paintComponent(Graphics g) {
		Insets ins = getInsets();
		Dimension dim = getSize();
		Rectangle r = new Rectangle(ins.left, ins.top, dim.width - ins.left - ins.right,
				dim.height - ins.top - ins.bottom);

		int maxLenOfY = Math.max(g.getFontMetrics().stringWidth(Integer.toString(chart.getyMax())),
				g.getFontMetrics().stringWidth(Integer.toString(chart.getyMin())));

		int xOrigin = r.x + GAP1 + GAP2 + maxLenOfY;
		int yOrigin = r.y + r.height - (GAP1 + GAP2);

		int yAxisPixStep = (yOrigin - r.y - POINTER_LENGTH - GAP_AFTER_POINTER)
				/ ((chart.getyMax() + chart.getyStep() - 1 - chart.getyMin()) / chart.getyStep());

		// ------------------------------------------------

		drawYPartOfChart(g, r, xOrigin, yOrigin, yAxisPixStep);

		// ------------------------------------------

		drawXPartOfChartAndColumns(g, r, xOrigin, yOrigin, yAxisPixStep);

		// ----------------------------------------------

		drawArrowsAndAxes(g, r, xOrigin, yOrigin);
	}

	/**
	 * Method that using graphics object draws y part of the chart
	 * 
	 * @param g
	 *            graphics object which makes drawing possible
	 * @param r
	 *            rectangle that defines borders of the frame
	 * @param xOrigin
	 *            x coordinate of the origin point(0,0)
	 * @param yOrigin
	 *            y coordinate of the origin point(0,0)
	 * @param yAxisPixStep
	 *            step on y axis(in pixels)
	 */
	private void drawYPartOfChart(Graphics g, Rectangle r, int xOrigin, int yOrigin, int yAxisPixStep) {
		Graphics2D g2d = (Graphics2D) g;
		AffineTransform defaultAt = g2d.getTransform();

		g2d.rotate(-Math.PI / 2);
		g2d.drawString(chart.getyDescription(),
				-((r.y + POINTER_LENGTH + GAP_AFTER_POINTER + yOrigin) / 2
						+ g.getFontMetrics().stringWidth(chart.getyDescription()) / 2),
				r.x + g.getFontMetrics().getHeight());

		g2d.setTransform(defaultAt);

		g.setColor(Color.BLACK);

		for (int i = 0; chart.getyMin() + i * chart.getyStep() < chart.getyMax() + chart.getyStep(); i++) {
			int temp = chart.getyMin() + i * chart.getyStep();

			int y = yOrigin - yAxisPixStep * i;

			g.drawString(Integer.toString(temp),
					xOrigin - GAP2 - g.getFontMetrics().stringWidth(Integer.toString(temp)),
					y + g.getFontMetrics().getAscent() / 2);

			g.drawLine(xOrigin - GAP2 / 2, yOrigin - yAxisPixStep * i, xOrigin, yOrigin - yAxisPixStep * i);

			g.setColor(Color.LIGHT_GRAY);
			g.drawLine(xOrigin, y, r.x + r.width - POINTER_LENGTH, y);// drawing grid
			g.setColor(Color.BLACK);
		}
	}

	/**
	 * Method that using graphics object draws x part of the chart and columns of
	 * the graph
	 * 
	 * @param g
	 *            graphics object which makes drawing possible
	 * @param r
	 *            rectangle that defines borders of the frame
	 * @param xOrigin
	 *            x coordinate of the origin point(0,0)
	 * @param yOrigin
	 *            y coordinate of the origin point(0,0)
	 * @param yAxisPixStep
	 *            step on y axis(in pixels)
	 */
	private void drawXPartOfChartAndColumns(Graphics g, Rectangle r, int xOrigin, int yOrigin, int yAxisPixStep) {
		g.drawString(chart.getxDescription(),
				(xOrigin + r.x + r.width - POINTER_LENGTH) / 2
						- g.getFontMetrics().stringWidth(chart.getxDescription()) / 2,
				r.y + r.height - g.getFontMetrics().getAscent());

		List<XYValue> list = chart.getValuesList();
		list.sort((arg0, arg1) -> Integer.compare(arg0.getX(), arg1.getX()));

		int xAxisPixStep = (r.x + r.width - POINTER_LENGTH - GAP_AFTER_POINTER - xOrigin) / list.size();

		int i;
		for (i = 0; i < list.size(); i++) {
			int xVal = list.get(i).getX();

			int xCoord = (2 * xOrigin + (2 * i + 1) * xAxisPixStep) / 2
					- g.getFontMetrics().stringWidth(Integer.toString(xVal)) / 2;

			g.drawString(Integer.toString(xVal), xCoord, yOrigin + GAP2);

			g.drawLine(xOrigin + i * xAxisPixStep, yOrigin, xOrigin + i * xAxisPixStep, yOrigin + GAP2 / 2);

			g.setColor(Color.LIGHT_GRAY);
			g.drawLine(xOrigin + (i + 1) * xAxisPixStep, yOrigin, xOrigin + (i + 1) * xAxisPixStep,
					r.y + POINTER_LENGTH);// drawing grid

			int yVal = list.get(i).getY();
			int pillarHeight = ((yVal - chart.getyMin()) * yAxisPixStep) / chart.getyStep();
			if(pillarHeight < 0) {
				g.setColor(Color.BLACK);
				continue;
			}
			
			g.setColor(Color.ORANGE);
			g.drawRect(xOrigin + i * xAxisPixStep, yOrigin - pillarHeight, xAxisPixStep,
					pillarHeight);
			g.fillRect(xOrigin + i * xAxisPixStep, yOrigin - pillarHeight, xAxisPixStep,
					pillarHeight);

			if (i != 0) {
				g.setColor(Color.WHITE);
				g.drawLine(xOrigin + i * xAxisPixStep, yOrigin, xOrigin + i * xAxisPixStep,
						yOrigin - pillarHeight);
			}

			g.drawLine(xOrigin + (i + 1) * xAxisPixStep, yOrigin, xOrigin + (i + 1) * xAxisPixStep,
					yOrigin - pillarHeight);

			g.setColor(Color.BLACK);
		}

		g.drawLine(xOrigin + i * xAxisPixStep, yOrigin, xOrigin + i * xAxisPixStep, yOrigin + GAP2 / 2);
	}

	/**
	 * Method that using given graphics object draws axes and arrows on the end of
	 * those axes
	 * 
	 * @param g
	 *            graphics object which makes drawing possible
	 * @param r
	 *            rectangle that defines borders of the frame
	 * @param xOrigin
	 *            x coordinate of the origin point(0,0)
	 * @param yOrigin
	 *            y coordinate of the origin point(0,0)
	 */
	private void drawArrowsAndAxes(Graphics g, Rectangle r, int xOrigin, int yOrigin) {
		// drawing y axis
		g.drawLine(xOrigin, yOrigin, xOrigin, r.y + POINTER_LENGTH + GAP_AFTER_POINTER);

		// drawing y arrow
		g.drawLine(xOrigin, r.y + POINTER_LENGTH + GAP_AFTER_POINTER, xOrigin,
				r.y + GAP_AFTER_POINTER + POINTER_LENGTH / 2);
		Polygon yArrow = new Polygon(new int[] { xOrigin - 4, xOrigin + 4, xOrigin },
				new int[] { r.y + GAP_AFTER_POINTER + POINTER_LENGTH / 2, r.y + GAP_AFTER_POINTER + POINTER_LENGTH / 2,
						r.y + GAP_AFTER_POINTER },
				3);
		g.drawPolygon(yArrow);
		g.fillPolygon(yArrow);

		// drawing x axis
		g.drawLine(xOrigin, yOrigin, r.x + r.width - GAP_AFTER_POINTER, yOrigin);

		// drawing x arrow
		g.drawLine(r.x + r.width - POINTER_LENGTH, yOrigin, r.x + r.width - POINTER_LENGTH / 2, yOrigin);
		Polygon xArrow = new Polygon(
				new int[] { r.x + r.width - GAP_AFTER_POINTER - POINTER_LENGTH / 2,
						r.x + r.width - GAP_AFTER_POINTER - POINTER_LENGTH / 2, r.x + r.width - GAP_AFTER_POINTER },
				new int[] { yOrigin + 4, yOrigin - 4, yOrigin }, 3);
		g.drawPolygon(xArrow);
		g.fillPolygon(xArrow);
	}

}

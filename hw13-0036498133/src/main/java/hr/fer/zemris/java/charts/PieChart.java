package hr.fer.zemris.java.charts;

import java.awt.image.BufferedImage;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

/**
 * Class representing a simple pie chart used for showing comparison between
 * collected statistics and values.
 * 
 * @author Rafael Josip Penić
 *
 */
public class PieChart {

	/**
	 * JFreeChart object which gives option to create Image object of the pie chart
	 */
	private JFreeChart chart;

	/**
	 * Data set containing data to be showed on pie chart
	 */
	private PieDataset dataset = new DefaultPieDataset();

	/**
	 * Constructor for PieChart objects
	 * 
	 * @param chartTitle
	 *            title of the chart
	 */
	public PieChart(String chartTitle) {
		chart = createChart(dataset, chartTitle);
	}

	/**
	 * Creates a BufferedImage object showing the image of this pie chart
	 * 
	 * @return image representing this pie chart
	 */
	public BufferedImage createImage() {
		return chart.createBufferedImage(400, 400);
	}

	/**
	 * Adds data pair(name of the data and its value) into internal data set
	 * 
	 * @param name
	 *            "name" of the data
	 * @param value
	 *            value that determines which amount of the pie chart given data
	 *            will take
	 */
	public void addToDataset(String name, int value) {
		((DefaultPieDataset) dataset).setValue(name, value);
	}

	/**
	 * Creates a chart titled with the given title and that shows composition of the
	 * given data.
	 * 
	 * @param dataset
	 *            data from which the pie chart will be constructed
	 * @param title
	 *            title of the chart
	 * @return constructed JFreeChart object
	 */
	private JFreeChart createChart(PieDataset dataset, String title) {

		JFreeChart chart = ChartFactory.createPieChart3D(title, // chart title
				dataset, // data
				true, // include legend
				true, false);

		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		plot.setStartAngle(290);
		plot.setDirection(Rotation.CLOCKWISE);
		return chart;

	}
}

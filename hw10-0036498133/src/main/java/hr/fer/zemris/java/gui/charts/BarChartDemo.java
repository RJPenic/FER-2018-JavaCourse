package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Program that reads lines from file given via command line and draws
 * appropriate BarChart(if the file is formatted correctly and exists).
 * 
 * @author Rafael Josip Penić
 *
 */
public class BarChartDemo extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor for BarChartDemo objects
	 * 
	 * @param chart
	 *            Bar chart that will be drawn
	 * @param pathString
	 *            string of the files path which chart will be drawn
	 */
	public BarChartDemo(BarChart chart, String pathString) {
		setLocation(40, 40);
		setTitle("BarChart");
		setSize(500, 500);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initGUI(chart, pathString);
	}

	/**
	 * Method that initializes Graphical User Interface and sets all components in
	 * its places
	 * 
	 * @param chart bar chart that will be drawn
	 * @param pathString string of the files path which chart will be drawn
	 */
	private void initGUI(BarChart chart, String pathString) {
		setLayout(new BorderLayout());

		JLabel label = new JLabel(pathString);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		add(label, BorderLayout.NORTH);

		add(new BarChartComponent(chart), BorderLayout.CENTER);
	}

	/**
	 * Main method
	 * 
	 * @param args
	 *            command line arguments
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Invalid number of arguments.");
			System.exit(1);
		}

		Path p = Paths.get(args[0]);

		List<String> linesList = new ArrayList<>();
		try {
			Files.lines(p).limit(6).forEach(l -> linesList.add(l));
		} catch (IOException ex) {
			System.out.println("Error while reading from " + p.toString());
			System.exit(1);
		}

		if (linesList.size() < 6) {
			System.out.println("Given file needs to have at least 6 lines.");
			System.exit(1);
		}

		String xDescr = linesList.get(0);
		String yDescr = linesList.get(1);

		BarChart chart = null;
		try {
			String[] valuesArray = linesList.get(2).split("\\s+");
			List<XYValue> list = new ArrayList<>();

			for (String val : valuesArray) {
				String[] xAndYDivided = val.split(",");

				if (xAndYDivided.length != 2) {
					System.out.println("Given file is not formatted correctly");
					System.exit(1);
				}

				list.add(new XYValue(Integer.parseInt(xAndYDivided[0]), Integer.parseInt(xAndYDivided[1])));
			}

			int yMin = Integer.parseInt(linesList.get(3));
			int yMax = Integer.parseInt(linesList.get(4));
			int yStep = Integer.parseInt(linesList.get(5));

			chart = new BarChart(list, xDescr, yDescr, yMin, yMax, yStep);
		} catch (NumberFormatException ex) {
			System.out.println("Some parts of given file cannot be parsed correctly.");
			System.exit(1);
		} catch (IllegalArgumentException ex) {
			System.out.println("Chart cannot be constructed with given values.");
			System.exit(1);
		}

		BarChart finalChart = chart;

		SwingUtilities.invokeLater(() -> {
			JFrame frame = new BarChartDemo(finalChart, p.toAbsolutePath().normalize().toString());
			frame.setVisible(true);
		});
	}
}

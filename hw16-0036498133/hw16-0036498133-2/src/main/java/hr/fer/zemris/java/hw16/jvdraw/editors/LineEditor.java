package hr.fer.zemris.java.hw16.jvdraw.editors;

import java.awt.GridLayout;
import java.awt.Point;
import java.util.Objects;

import javax.swing.JLabel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw16.jvdraw.components.JColorArea;
import hr.fer.zemris.java.hw16.jvdraw.geometry.Line;

/**
 * Editor that can edit Line objects
 * 
 * @author Rafael Josip Penić
 *
 */
public class LineEditor extends GeometricalObjectEditor {

	private static final long serialVersionUID = 1L;

	/**
	 * Line that is edited
	 */
	private Line line;

	/**
	 * Text field that receives input about x-axis coordinate of the line start
	 */
	JTextField xStartField;

	/**
	 * Text field that receives input about y-axis coordinate of the line start
	 */
	JTextField yStartField;

	/**
	 * Text field that receives input about x-axis coordinate of the line end
	 */
	JTextField xEndField;

	/**
	 * Text field that receives input about y-axis coordinate of the line end
	 */
	JTextField yEndField;

	/**
	 * Color area that will determine lines color
	 */
	JColorArea colorArea;

	/**
	 * Constructor
	 * 
	 * @param line
	 *            line object that is edited
	 */
	public LineEditor(Line line) {
		Objects.requireNonNull(line, "Given line reference is null");

		this.line = line;

		xStartField = new JTextField(Integer.toString(line.getLineStart().x));
		yStartField = new JTextField(Integer.toString(line.getLineStart().y));

		xEndField = new JTextField(Integer.toString(line.getLineEnd().x));
		yEndField = new JTextField(Integer.toString(line.getLineEnd().y));

		colorArea = new JColorArea(line.getLineColor());

		setLayout(new GridLayout(0, 4));

		add(new JLabel("Line start, x coordinate : "));
		add(xStartField);

		add(new JLabel("Line start, y coordinate : "));
		add(yStartField);

		add(new JLabel("Line end, x coordinate : "));
		add(xEndField);

		add(new JLabel("Line end, y coordinate : "));
		add(yEndField);

		add(new JLabel("Line color : "));
		add(colorArea);
	}

	@Override
	public void checkEditing() {
		try {
			Integer.parseInt(xStartField.getText());
			Integer.parseInt(yStartField.getText());

			Integer.parseInt(xEndField.getText());
			Integer.parseInt(yEndField.getText());
		} catch (NumberFormatException ex) {
			throw new IllegalArgumentException("Entered values are not valid.");
		}
	}

	@Override
	public void acceptEditing() {
		line.setLineStart(new Point(Integer.parseInt(xStartField.getText()), Integer.parseInt(yStartField.getText())));
		line.setLineEnd(new Point(Integer.parseInt(xEndField.getText()), Integer.parseInt(yEndField.getText())));
		line.setLineColor(colorArea.getCurrentColor());
	}

}

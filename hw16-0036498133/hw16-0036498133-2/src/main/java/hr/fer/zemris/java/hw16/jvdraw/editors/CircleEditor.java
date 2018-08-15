package hr.fer.zemris.java.hw16.jvdraw.editors;

import java.awt.GridLayout;
import java.awt.Point;
import java.util.Objects;

import javax.swing.JLabel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw16.jvdraw.components.JColorArea;
import hr.fer.zemris.java.hw16.jvdraw.geometry.Circle;

/**
 * Editor that can edit circle objects
 * 
 * @author Rafael Josip Penić
 *
 */
public class CircleEditor extends GeometricalObjectEditor {

	private static final long serialVersionUID = 1L;

	/**
	 * circle that is edited
	 */
	private Circle circle;

	/**
	 * Text field that receives input about x-axis coordinate of the center
	 */
	JTextField xCenterField;

	/**
	 * Text field that receives input about y-axis coordinate of the center
	 */
	JTextField yCenterField;

	/**
	 * Text field that receives input about circle radius
	 */
	JTextField radiusField;

	/**
	 * Color area object that receives input about circles color
	 */
	JColorArea colorArea;

	/**
	 * Constructor
	 * 
	 * @param circle
	 *            circle object that will be edited
	 */
	public CircleEditor(Circle circle) {
		Objects.requireNonNull(circle, "Given circle reference is null");

		this.circle = circle;

		xCenterField = new JTextField(Integer.toString(circle.getCenter().x));
		yCenterField = new JTextField(Integer.toString(circle.getCenter().y));

		radiusField = new JTextField(Integer.toString(circle.getRadius()));

		colorArea = new JColorArea(circle.getLineColor());

		setLayout(new GridLayout(0, 2));

		add(new JLabel("Circle center, x coordinate : "));
		add(xCenterField);

		add(new JLabel("Circle center, y coordinate : "));
		add(yCenterField);

		add(new JLabel("Radius : "));
		add(radiusField);

		add(new JLabel("Outline color : "));
		add(colorArea);
	}

	@Override
	public void checkEditing() {
		try {
			Integer.parseInt(xCenterField.getText());
			Integer.parseInt(yCenterField.getText());

			Integer.parseInt(radiusField.getText());
		} catch (NumberFormatException ex) {
			throw new IllegalArgumentException("Entered values are not valid.");
		}
	}

	@Override
	public void acceptEditing() {
		circle.setCenter(new Point(Integer.parseInt(xCenterField.getText()), Integer.parseInt(yCenterField.getText())));
		circle.setRadius(Integer.parseInt(radiusField.getText()));
		circle.setLineColor(colorArea.getCurrentColor());
	}

}

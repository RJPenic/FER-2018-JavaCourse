package hr.fer.zemris.java.hw16.jvdraw.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.JColorChooser;
import javax.swing.JComponent;

/**
 * Swing component which at the same time "plays" the role of the color
 * provider. When pressed it opens up color chooser panel from which the user
 * can choose colors and JColorArea changes accordingly.
 * 
 * @author Rafael Josip Penić
 *
 */
public class JColorArea extends JComponent implements IColorProvider {

	/**
	 * Default preferred dimension = 15 x 15
	 */
	private static final Dimension DEFAULT_PREFERRED_DIMENSION = new Dimension(15, 15);

	private static final long serialVersionUID = 1L;

	/**
	 * Currently selected color
	 */
	private Color selectedColor;

	/**
	 * Collection containing listeners
	 */
	private List<ColorChangeListener> listeners = new ArrayList<>();

	/**
	 * Constructor for JColorArea objects
	 * 
	 * @param selectedColor
	 *            determines which color will be selected when the object is created
	 */
	public JColorArea(Color selectedColor) {
		Objects.requireNonNull(selectedColor, "Given color reference is null.");
		this.selectedColor = selectedColor;

		addInitialListener();
	}

	/**
	 * Adds initial mouse listener
	 */
	private void addInitialListener() {
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Color temp = JColorChooser.showDialog(JColorArea.this.getRootPane(), "Color chooser", selectedColor);

				if (temp != null) {
					Color oldColor = selectedColor;
					selectedColor = temp;
					notifyListeners(oldColor, temp);
					repaint();
				}
			}
		});
	}

	/**
	 * Notifies all listeners about color change
	 * 
	 * @param oldColor
	 *            old color
	 * @param newColor
	 *            new color
	 */
	private void notifyListeners(Color oldColor, Color newColor) {
		for (ColorChangeListener l : listeners) {
			l.newColorSelected(this, oldColor, newColor);
		}
	}

	@Override
	public Dimension getPreferredSize() {
		return DEFAULT_PREFERRED_DIMENSION;
	}

	@Override
	public void paint(Graphics g) {
		Dimension d = getSize();

		g.setColor(selectedColor);
		g.fillRect(0, 0, (int) d.getWidth(), (int) d.getHeight());
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, (int) d.getWidth(), (int) d.getHeight() - 1);
	}

	@Override
	public Color getCurrentColor() {
		return selectedColor;
	}

	@Override
	public void addColorChangeListener(ColorChangeListener l) {
		Objects.requireNonNull(l, "Given listener reference is null");
		listeners.add(l);
	}

	@Override
	public void removeColorChangeListener(ColorChangeListener l) {
		Objects.requireNonNull(l, "Given listener reference is null");
		listeners.remove(l);
	}
}

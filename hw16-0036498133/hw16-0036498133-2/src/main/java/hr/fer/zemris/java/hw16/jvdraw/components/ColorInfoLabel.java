package hr.fer.zemris.java.hw16.jvdraw.components;

import java.awt.Color;
import java.util.Objects;

import javax.swing.JLabel;

/**
 * Extends JLabel which according to color providers it listens to, changes its
 * content.
 * 
 * @author Rafael Josip Penić
 *
 */
public class ColorInfoLabel extends JLabel implements ColorChangeListener {

	private static final long serialVersionUID = 1L;

	/**
	 * Foreground color provider
	 */
	private IColorProvider fgColorProvider;

	/**
	 * Background color provider
	 */
	private IColorProvider bgColorProvider;

	/**
	 * Constructor for ColorInfoLabel objects
	 * 
	 * @param fgColorProvider
	 *            Foreground color provider
	 * @param bgColorProvider
	 *            Background color provider
	 */
	public ColorInfoLabel(IColorProvider fgColorProvider, IColorProvider bgColorProvider) {
		Objects.requireNonNull(fgColorProvider, "Given foreground color provider reference is null");
		Objects.requireNonNull(bgColorProvider, "Given background color provider reference is null");

		this.fgColorProvider = fgColorProvider;
		this.bgColorProvider = bgColorProvider;
		newColorSelected(null, null, null);
	}

	@Override
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor) {
		StringBuilder sb = new StringBuilder();

		sb.append("Foreground color: (");
		sb.append(fgColorProvider.getCurrentColor().getRed() + ", ");
		sb.append(fgColorProvider.getCurrentColor().getGreen() + ", ");
		sb.append(fgColorProvider.getCurrentColor().getBlue() + "), ");

		sb.append("background color: (");
		sb.append(bgColorProvider.getCurrentColor().getRed() + ", ");
		sb.append(bgColorProvider.getCurrentColor().getGreen() + ", ");
		sb.append(bgColorProvider.getCurrentColor().getBlue() + ").");

		super.setText(sb.toString());
		repaint();
	}
}

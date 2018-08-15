package hr.fer.zemris.java.hw16.jvdraw.components;

import java.awt.Color;

/**
 * Listener that is triggered whenever the given color provider changes current
 * color
 * 
 * @author Rafael Josip Penić
 *
 */
public interface ColorChangeListener {

	/**
	 * Method called whenever color provider changed color
	 * 
	 * @param source
	 *            source color provider
	 * @param oldColor
	 *            old color of the provider
	 * @param newColor
	 *            provider's new color
	 */
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor);
}

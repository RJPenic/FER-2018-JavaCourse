package hr.fer.zemris.java.hw16.jvdraw.components;

import java.awt.Color;

/**
 * Provider that provides colors needed for drawing
 * 
 * @author Rafael Josip Penić
 *
 */
public interface IColorProvider {

	/**
	 * Getter for current color of the provider
	 * 
	 * @return provider's current color
	 */
	public Color getCurrentColor();

	/**
	 * Adds change listener to the provider
	 * 
	 * @param l
	 *            listener
	 */
	public void addColorChangeListener(ColorChangeListener l);

	/**
	 * Removes listener from the provider
	 * 
	 * @param l
	 *            listener(that will be removed)
	 */
	public void removeColorChangeListener(ColorChangeListener l);
}

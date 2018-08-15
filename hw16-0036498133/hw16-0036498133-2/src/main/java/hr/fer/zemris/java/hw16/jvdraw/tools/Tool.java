package hr.fer.zemris.java.hw16.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

/**
 * Interface defining a simple tool used for drawing
 * 
 * @author Rafael Josip Penić
 *
 */
public interface Tool {
	/**
	 * Determines what happens when the mouse button is pressed
	 * 
	 * @param e
	 *            mouse event
	 */
	public void mousePressed(MouseEvent e);

	/**
	 * Determines what happens when the mouse button is released
	 * 
	 * @param e
	 *            mouse event
	 */
	public void mouseReleased(MouseEvent e);

	/**
	 * Determines what happens when the mouse button is clicked
	 * 
	 * @param e
	 *            mouse event
	 */
	public void mouseClicked(MouseEvent e);

	/**
	 * Determines what happens when the mouse is moved without any button pressed
	 * 
	 * @param e
	 *            mouse event
	 */
	public void mouseMoved(MouseEvent e);

	/**
	 * Determines what happens when the mouse is dragged
	 * 
	 * @param e
	 *            mouse event
	 */
	public void mouseDragged(MouseEvent e);

	/**
	 * Paints using given graphics object
	 * 
	 * @param g2d
	 *            graphics object that makes drawing possible
	 */
	public void paint(Graphics2D g2d);
}

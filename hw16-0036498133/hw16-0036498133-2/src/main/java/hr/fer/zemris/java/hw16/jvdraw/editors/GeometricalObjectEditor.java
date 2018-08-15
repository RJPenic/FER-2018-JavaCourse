package hr.fer.zemris.java.hw16.jvdraw.editors;

import javax.swing.JPanel;

/**
 * Class representing a simple geometry object editor which can change objects
 * color, position and similar.
 * 
 * @author Rafael Josip Penić
 *
 */
public abstract class GeometricalObjectEditor extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Checks if editing is valid, if not an exception is thrown
	 */
	public abstract void checkEditing();

	/**
	 * Accepts input and updates the objects
	 */
	public abstract void acceptEditing();
}

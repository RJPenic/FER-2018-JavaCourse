package hr.fer.zemris.java.hw16.jvdraw.geometry;

/**
 * Listeners that listen for geometry object changes
 * 
 * @author Rafael Josip Penić
 *
 */
public interface GeometricalObjectListener {
	/**
	 * Called when the listened object changed
	 * 
	 * @param o
	 *            geometry object that changed
	 */
	public void geometricalObjectChanged(GeometricalObject o);
}

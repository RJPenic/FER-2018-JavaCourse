package hr.fer.zemris.java.hw16.jvdraw.model;

import hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObject;

/**
 * Model which is used mainly as a storage for the currently drawn objects
 * 
 * @author Rafael Josip Penić
 *
 */
public interface DrawingModel {

	/**
	 * Return number of objects in the model
	 * 
	 * @return number of objects in the model
	 */
	public int getSize();

	/**
	 * Gets object on the given index
	 * 
	 * @param index
	 *            index from which the object will be gotten
	 * @return object stored on the given index
	 */
	public GeometricalObject getObject(int index);

	/**
	 * Adds given object into the model
	 * 
	 * @param object
	 *            object to be added
	 */
	public void add(GeometricalObject object);

	/**
	 * Adds listener to the model
	 * 
	 * @param l
	 *            listener
	 */
	public void addDrawingModelListener(DrawingModelListener l);

	/**
	 * Removes listener from the model
	 * 
	 * @param l
	 *            listener
	 */
	public void removeDrawingModelListener(DrawingModelListener l);

	/**
	 * Removes given object from the model
	 * 
	 * @param object
	 *            object to be removed
	 */
	void remove(GeometricalObject object);

	/**
	 * Changes position of the given object in the model
	 * 
	 * @param object
	 *            object to be repositioned
	 * @param offset
	 *            determines where the object will be moved
	 */
	void changeOrder(GeometricalObject object, int offset);
}

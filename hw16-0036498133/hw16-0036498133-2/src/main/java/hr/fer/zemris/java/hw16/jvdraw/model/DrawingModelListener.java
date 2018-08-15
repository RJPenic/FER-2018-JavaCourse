package hr.fer.zemris.java.hw16.jvdraw.model;

/**
 * Drawing model listener that triggers when there are changes in the drawing
 * model
 * 
 * @author Rafael Josip Penić
 *
 */
public interface DrawingModelListener {

	/**
	 * Called when objects are added into the model
	 * 
	 * @param source
	 *            model in which the objects are added
	 * @param index0
	 *            from
	 * @param index1
	 *            to
	 */
	public void objectsAdded(DrawingModel source, int index0, int index1);

	/**
	 * Called when objects are removed from the model
	 * 
	 * @param source
	 *            model from which the objects are removed
	 * @param index0
	 *            from
	 * @param index1
	 *            to
	 */
	public void objectsRemoved(DrawingModel source, int index0, int index1);

	/**
	 * Called when one or more objects in the drawing model changed
	 * 
	 * @param source
	 *            model in which the objects changed
	 * @param index0
	 *            from
	 * @param index1
	 *            to
	 */
	public void objectsChanged(DrawingModel source, int index0, int index1);

}

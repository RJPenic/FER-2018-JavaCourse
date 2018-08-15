package hr.fer.zemris.java.hw16.jvdraw.list;

import java.util.Objects;

import javax.swing.AbstractListModel;

import hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModelListener;

/**
 * List model which lets the JList be "connected" with the drawing model
 * 
 * @author Rafael Josip Penić
 *
 */
public class DrawingObjectListModel extends AbstractListModel<GeometricalObject> implements DrawingModelListener {

	private static final long serialVersionUID = 1L;

	/**
	 * Drawing model that stores draw objects for the model
	 */
	private DrawingModel dModel;

	/**
	 * Constructor
	 * 
	 * @param dModel
	 *            Drawing model that stores objects for the list
	 */
	public DrawingObjectListModel(DrawingModel dModel) {
		Objects.requireNonNull(dModel, "Given drawing model reference is null.");
		this.dModel = dModel;
		dModel.addDrawingModelListener(this);
	}

	@Override
	public GeometricalObject getElementAt(int index) {
		return dModel.getObject(index);
	}

	@Override
	public int getSize() {
		return dModel.getSize();
	}

	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		this.fireIntervalAdded(this, index0, index1);
	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		this.fireIntervalRemoved(this, index0, index1);
	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		this.fireContentsChanged(this, index0, index1);
	}

}

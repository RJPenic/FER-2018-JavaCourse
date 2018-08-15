package hr.fer.zemris.java.hw16.jvdraw.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObjectListener;

/**
 * Implementation of drawing model and geometry object listener interfaces
 * 
 * @author Rafael Josip Penić
 *
 */
public class DrawingModelImpl implements DrawingModel, GeometricalObjectListener {

	/**
	 * Stores current geometry objects
	 */
	private List<GeometricalObject> geometricalObjects = new ArrayList<>();

	/**
	 * Stores drawing model listeners
	 */
	private List<DrawingModelListener> listeners = new ArrayList<>();

	@Override
	public int getSize() {
		return geometricalObjects.size();
	}

	@Override
	public GeometricalObject getObject(int index) {
		return geometricalObjects.get(index);
	}

	@Override
	public void add(GeometricalObject object) {
		Objects.requireNonNull(object, "Given geometrical object reference is null.");
		geometricalObjects.add(object);
		for (DrawingModelListener l : listeners) {
			l.objectsAdded(this, geometricalObjects.indexOf(object), geometricalObjects.indexOf(object));
		}
	}

	@Override
	public void addDrawingModelListener(DrawingModelListener l) {
		Objects.requireNonNull(l, "Given listener reference is null.");
		listeners.add(l);
	}

	@Override
	public void removeDrawingModelListener(DrawingModelListener l) {
		Objects.requireNonNull(l, "Given listener reference is null.");
		listeners.remove(l);
	}

	@Override
	public void remove(GeometricalObject object) {
		Objects.requireNonNull(object, "Given geometrical object reference is null");
		int index = geometricalObjects.indexOf(object);
		geometricalObjects.remove(object);
		for (DrawingModelListener l : listeners) {
			l.objectsRemoved(this, index, index);
		}
	}

	@Override
	public void changeOrder(GeometricalObject object, int offset) {
		int index = geometricalObjects.indexOf(object) + offset;

		if (index < 0 || index >= getSize())
			return;

		geometricalObjects.remove(object);
		geometricalObjects.add(index, object);

		for (DrawingModelListener l : listeners) {
			l.objectsChanged(this, geometricalObjects.indexOf(object), geometricalObjects.indexOf(object));
		}

	}

	@Override
	public void geometricalObjectChanged(GeometricalObject o) {
		for (DrawingModelListener l : listeners) {
			l.objectsChanged(this, geometricalObjects.indexOf(o), geometricalObjects.indexOf(o));
		}
	}
}

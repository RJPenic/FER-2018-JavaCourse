package hr.fer.zemris.java.hw16.jvdraw.geometry;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import hr.fer.zemris.java.hw16.jvdraw.editors.GeometricalObjectEditor;

/**
 * Class representing standard geometry object
 * 
 * @author Rafael Josip Penić
 *
 */
public abstract class GeometricalObject {

	/**
	 * List containing current object listeners
	 */
	private List<GeometricalObjectListener> listeners = new ArrayList<>();

	/**
	 * Method that lets the given visitor visit the object
	 * 
	 * @param v
	 *            visitor
	 */
	public abstract void accept(GeometricalObjectVisitor v);

	/**
	 * Creates appropriate object editor
	 * 
	 * @return object editor
	 */
	public abstract GeometricalObjectEditor createGeometricalObjectEditor();

	/**
	 * Registers given listener to the object
	 * 
	 * @param l
	 *            listener that will be registered
	 */
	public void addGeometricalObjectListener(GeometricalObjectListener l) {
		Objects.requireNonNull(l, "Given listener reference is null");
		listeners.add(l);
	}

	/**
	 * Deregisters given listener from the object
	 * 
	 * @param l
	 *            lister that will be deregistered
	 */
	public void removeGeometricalObjectListener(GeometricalObjectListener l) {
		Objects.requireNonNull(l, "Given listener reference is null");
		listeners.remove(l);
	}

	/**
	 * Constructs string representation of the object in the file
	 * 
	 * @return file form
	 */
	public abstract String constructStringForFile();
}

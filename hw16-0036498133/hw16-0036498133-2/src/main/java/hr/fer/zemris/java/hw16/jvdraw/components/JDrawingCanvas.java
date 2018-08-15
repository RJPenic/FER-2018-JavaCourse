package hr.fer.zemris.java.hw16.jvdraw.components;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

import hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObjectPainter;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModelListener;

/**
 * Component representing a simple canvas on which the user can draw various
 * geometry shapes
 * 
 * @author Rafael Josip Penić
 *
 */
public class JDrawingCanvas extends JComponent implements DrawingModelListener {

	private static final long serialVersionUID = 1L;

	/**
	 * Drawing model which stores currently drawn objects
	 */
	private DrawingModel drawingModel;

	/**
	 * Foreground color provider
	 */
	private IColorProvider fgColProvider;

	/**
	 * Background color provider
	 */
	private IColorProvider bgColProvider;

	/**
	 * Constructor for canvas objects
	 * 
	 * @param drawingModel
	 *            model containing drawn objects
	 * @param fgColProvider
	 *            foreground color provider
	 * @param bgColProvider
	 *            background color provider
	 */
	public JDrawingCanvas(DrawingModel drawingModel, IColorProvider fgColProvider, IColorProvider bgColProvider) {
		super();
		this.drawingModel = drawingModel;
		this.fgColProvider = fgColProvider;
		this.bgColProvider = bgColProvider;
	}

	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		repaint();
	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		repaint();
	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		GeometricalObjectPainter painter = new GeometricalObjectPainter((Graphics2D) g, drawingModel, fgColProvider,
				bgColProvider, this);
		for (int i = 0; i < drawingModel.getSize(); i++) {
			drawingModel.getObject(i).accept(painter);
		}
	}

}

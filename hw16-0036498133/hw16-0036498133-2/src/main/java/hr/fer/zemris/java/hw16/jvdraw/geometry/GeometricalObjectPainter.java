package hr.fer.zemris.java.hw16.jvdraw.geometry;

import java.awt.Graphics2D;

import hr.fer.zemris.java.hw16.jvdraw.components.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.components.JDrawingCanvas;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.tools.CircleTool;
import hr.fer.zemris.java.hw16.jvdraw.tools.FilledCircleTool;
import hr.fer.zemris.java.hw16.jvdraw.tools.LineTool;

/**
 * Visitor that paints each object it visits onto the canvas
 * 
 * @author Rafael Josip Penić
 *
 */
public class GeometricalObjectPainter implements GeometricalObjectVisitor {

	/**
	 * Graphics object use for drawing
	 */
	private Graphics2D graphics;

	/**
	 * Drawing model that stores drawn objects
	 */
	private DrawingModel dModel;

	/**
	 * Foreground color provider
	 */
	private IColorProvider fgColProvider;

	/**
	 * Background color provider
	 */
	private IColorProvider bgColProvider;

	/**
	 * Canvas on which the objects are drawn
	 */
	private JDrawingCanvas canvas;

	/**
	 * Constructor
	 * 
	 * @param graphics
	 *            drawing graphics
	 * @param dModel
	 *            drawing model
	 * @param fgColProvider
	 *            color provider
	 * @param bgColProvider
	 *            color provider
	 * @param canvas
	 *            canvas on which the objects are drawn
	 */
	public GeometricalObjectPainter(Graphics2D graphics, DrawingModel dModel, IColorProvider fgColProvider,
			IColorProvider bgColProvider, JDrawingCanvas canvas) {

		this.graphics = graphics;
		this.dModel = dModel;
		this.fgColProvider = fgColProvider;
		this.bgColProvider = bgColProvider;
		this.canvas = canvas;
	}

	@Override
	public void visit(Line line) {
		LineTool lt = new LineTool(fgColProvider, dModel, canvas, line);
		lt.paint(graphics);
	}

	@Override
	public void visit(Circle circle) {
		CircleTool ct = new CircleTool(fgColProvider, dModel, canvas, circle);
		ct.paint(graphics);
	}

	@Override
	public void visit(FilledCircle filledCircle) {
		FilledCircleTool fct = new FilledCircleTool(dModel, filledCircle, fgColProvider, bgColProvider, canvas);
		fct.paint(graphics);
	}

}

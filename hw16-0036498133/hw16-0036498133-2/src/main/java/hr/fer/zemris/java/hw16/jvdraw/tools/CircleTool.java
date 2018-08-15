package hr.fer.zemris.java.hw16.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw16.jvdraw.components.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.components.JDrawingCanvas;
import hr.fer.zemris.java.hw16.jvdraw.geometry.Circle;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel;

/**
 * Tool used for drawing circles
 * 
 * @author Rafael Josip Penić
 *
 */
public class CircleTool implements Tool {

	/**
	 * Drawing model which stores all drawn objects
	 */
	private DrawingModel dModel;

	/**
	 * Currently drawn circle
	 */
	private Circle currentCircle;

	/**
	 * flag that says if the center has been determines or not
	 */
	private boolean centerPointDetermined = false;

	/**
	 * Foreground color provider
	 */
	private IColorProvider colorProvider;

	/**
	 * Drawing canvas
	 */
	private JDrawingCanvas canvas;

	/**
	 * Constructor
	 * 
	 * @param dModel
	 *            drawing model
	 * @param colorProviderFG
	 *            foreground provider
	 * @param canvas
	 *            drawing canvas
	 */
	public CircleTool(DrawingModel dModel, IColorProvider colorProvider, JDrawingCanvas canvas) {
		super();
		this.dModel = dModel;
		this.colorProvider = colorProvider;
		this.canvas = canvas;
	}

	/**
	 * Constructor with the object
	 * 
	 * @param dModel
	 *            drawing model
	 * @param currentCircle
	 *            currently drawn filled circle
	 * @param colorProviderFG
	 *            foreground color provider
	 * @param canvas
	 *            drawing canvas
	 */
	public CircleTool(IColorProvider coloProvider, DrawingModel dModel, JDrawingCanvas canvas, Circle currentCircle) {
		this(dModel, coloProvider, canvas);
		this.currentCircle = currentCircle;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (centerPointDetermined) {
			mouseReleased(e);
		} else {
			currentCircle = new Circle(e.getPoint(), 0, colorProvider.getCurrentColor());
			dModel.add(currentCircle);
			canvas.repaint();
		}
		dModel.changeOrder(currentCircle, 0);
		centerPointDetermined = !centerPointDetermined;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		currentCircle.setRadius((int) currentCircle.getCenter().distance(e.getPoint()));
		canvas.repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (centerPointDetermined) {
			mouseReleased(e);
			dModel.changeOrder(currentCircle, 0);
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mouseReleased(e);
		dModel.changeOrder(currentCircle, 0);
	}

	@Override
	public void paint(Graphics2D g2d) {
		g2d.setColor(currentCircle.getLineColor());

		int r = currentCircle.getRadius();

		g2d.drawOval(currentCircle.getCenter().x - r, currentCircle.getCenter().y - r, 2 * r, 2 * r);
	}

}

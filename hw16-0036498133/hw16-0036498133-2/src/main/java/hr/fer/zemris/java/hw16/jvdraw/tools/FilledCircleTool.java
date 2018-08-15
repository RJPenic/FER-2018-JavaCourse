package hr.fer.zemris.java.hw16.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw16.jvdraw.components.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.components.JDrawingCanvas;
import hr.fer.zemris.java.hw16.jvdraw.geometry.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel;

/**
 * Tool used for drawing filled circles
 * 
 * @author Rafael Josip Penić
 *
 */
public class FilledCircleTool implements Tool {

	/**
	 * Drawing model which stores all drawn objects
	 */
	private DrawingModel dModel;

	/**
	 * Currently drawn filled circle
	 */
	private FilledCircle currentFilledCircle;

	/**
	 * flag that says if the center has been determines or not
	 */
	private boolean centerPointDetermined = false;

	/**
	 * Foreground color provider
	 */
	private IColorProvider colorProviderFG;

	/**
	 * Background color provider
	 */
	private IColorProvider colorProviderBG;

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
	 * @param colorProviderBG
	 *            background provider
	 * @param canvas
	 *            drawing canvas
	 */
	public FilledCircleTool(DrawingModel dModel, IColorProvider colorProviderFG, IColorProvider colorProviderBG,
			JDrawingCanvas canvas) {
		super();
		this.dModel = dModel;
		this.colorProviderFG = colorProviderFG;
		this.colorProviderBG = colorProviderBG;
		this.canvas = canvas;
	}

	/**
	 * Constructor with the object
	 * 
	 * @param dModel
	 *            drawing model
	 * @param currentFilledCircle
	 *            currently drawn filled circle
	 * @param colorProviderFG
	 *            foreground color provider
	 * @param colorProviderBG
	 *            background color provider
	 * @param canvas
	 *            drawing canvas
	 */
	public FilledCircleTool(DrawingModel dModel, FilledCircle currentFilledCircle, IColorProvider colorProviderFG,
			IColorProvider colorProviderBG, JDrawingCanvas canvas) {
		super();
		this.dModel = dModel;
		this.currentFilledCircle = currentFilledCircle;
		this.colorProviderFG = colorProviderFG;
		this.colorProviderBG = colorProviderBG;
		this.canvas = canvas;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (centerPointDetermined) {
			mouseReleased(e);
		} else {
			currentFilledCircle = new FilledCircle(e.getPoint(), 0, colorProviderFG.getCurrentColor(),
					colorProviderBG.getCurrentColor());
			dModel.add(currentFilledCircle);
			canvas.repaint();
		}
		centerPointDetermined = !centerPointDetermined;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		currentFilledCircle.setRadius((int) currentFilledCircle.getCenter().distance(e.getPoint()));
		canvas.repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (centerPointDetermined) {
			mouseReleased(e);
			dModel.changeOrder(currentFilledCircle, 0);
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mouseReleased(e);
		dModel.changeOrder(currentFilledCircle, 0);
	}

	@Override
	public void paint(Graphics2D g2d) {
		g2d.setColor(currentFilledCircle.getBackgroundColor());

		int r = currentFilledCircle.getRadius();

		g2d.fillOval(currentFilledCircle.getCenter().x - r, currentFilledCircle.getCenter().y - r, 2 * r, 2 * r);

		g2d.setColor(currentFilledCircle.getLineColor());
		g2d.drawOval(currentFilledCircle.getCenter().x - r, currentFilledCircle.getCenter().y - r, 2 * r, 2 * r);
	}
}

package hr.fer.zemris.java.hw16.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.Objects;

import hr.fer.zemris.java.hw16.jvdraw.components.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.components.JDrawingCanvas;
import hr.fer.zemris.java.hw16.jvdraw.geometry.Line;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel;

/**
 * Tool used for drawing lines
 * 
 * @author Rafael Josip Penić
 *
 */
public class LineTool implements Tool {

	/**
	 * Drawing model which stores all drawn objects
	 */
	private DrawingModel dModel;

	/**
	 * flag that says if the line start has been determines or not
	 */
	private boolean lineStartDetermined = false;

	/**
	 * Color provider responsible for the color
	 */
	private IColorProvider colorProvider;

	/**
	 * Canvas on which the lines will be drawn
	 */
	private JDrawingCanvas canvas;

	/**
	 * Currently drawn line
	 */
	private Line currentLine;

	/**
	 * Constructor
	 * 
	 * @param colorProvider
	 *            provides color
	 * @param dModel
	 *            drawing model
	 * @param canvas
	 *            drawing canvas
	 */
	public LineTool(IColorProvider colorProvider, DrawingModel dModel, JDrawingCanvas canvas) {
		Objects.requireNonNull(colorProvider, "Given color provider reference is null.");
		Objects.requireNonNull(dModel, "Given model reference is null.");
		Objects.requireNonNull(canvas, "Given canvas reference is null.");
		this.dModel = dModel;
		this.colorProvider = colorProvider;
		this.canvas = canvas;
	}

	/**
	 * Constructor with the object
	 * 
	 * @param coloProvider
	 *            provides color
	 * @param dModel
	 *            drawing model
	 * @param canvas
	 *            drawing canvas
	 * @param currentLine
	 *            currently drawn line
	 */
	public LineTool(IColorProvider coloProvider, DrawingModel dModel, JDrawingCanvas canvas, Line currentLine) {
		this(coloProvider, dModel, canvas);

		this.currentLine = currentLine;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (lineStartDetermined) {
			mouseReleased(e);
		} else {
			currentLine = new Line(e.getPoint(), e.getPoint(), colorProvider.getCurrentColor());
			dModel.add(currentLine);
			canvas.repaint();
		}
		lineStartDetermined = !lineStartDetermined;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		currentLine.setLineEnd(e.getPoint());
		canvas.repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (lineStartDetermined) {
			mouseReleased(e);
			dModel.changeOrder(currentLine, 0);
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mouseReleased(e);
		dModel.changeOrder(currentLine, 0);
	}

	@Override
	public void paint(Graphics2D g2d) {
		g2d.setColor(currentLine.getLineColor());

		g2d.drawLine(currentLine.getLineStart().x, currentLine.getLineStart().y, currentLine.getLineEnd().x,
				currentLine.getLineEnd().y);
	}
}

package hr.fer.zemris.java.hw16.jvdraw.geometry;

import java.awt.Rectangle;

/**
 * Geometry object visitor that constructs a bounding box depending on height
 * and width of the objects
 * 
 * @author Rafael Josip Penić
 *
 */
public class GeometricalObjectBBCalculator implements GeometricalObjectVisitor {

	/**
	 * Maximal y
	 */
	private int ymax;

	/**
	 * Minimal y
	 */
	private int ymin;

	/**
	 * Maximal x
	 */
	private int xmax;

	/**
	 * Minimal x
	 */
	private int xmin;

	/**
	 * Flag that determines if the setBounds has been called for the first time or
	 * not
	 */
	private boolean initialBoundsSet = false;

	@Override
	public void visit(Line line) {
		int ymaxTemp = (line.getLineStart().y > line.getLineEnd().y) ? line.getLineStart().y : line.getLineEnd().y;
		int yminTemp = (line.getLineStart().y < line.getLineEnd().y) ? line.getLineStart().y : line.getLineEnd().y;

		int xmaxTemp = (line.getLineStart().x > line.getLineEnd().x) ? line.getLineStart().x : line.getLineEnd().x;
		int xminTemp = (line.getLineStart().x < line.getLineEnd().x) ? line.getLineStart().x : line.getLineEnd().x;

		setBounds(ymaxTemp, yminTemp, xmaxTemp, xminTemp);
	}

	@Override
	public void visit(Circle circle) {
		setBounds(circle.getCenter().y + circle.getRadius(), circle.getCenter().y - circle.getRadius(),
				circle.getCenter().x + circle.getRadius(), circle.getCenter().x - circle.getRadius());
	}

	@Override
	public void visit(FilledCircle filledCircle) {
		visit((Circle) filledCircle);
	}

	/**
	 * Sets bounds so all geometry objects can fit in the picture
	 * 
	 * @param ymaxTemp
	 *            maximum y
	 * @param yminTemp
	 *            minimum y
	 * @param xmaxTemp
	 *            maximum x
	 * @param xminTemp
	 *            minimum x
	 */
	private void setBounds(int ymaxTemp, int yminTemp, int xmaxTemp, int xminTemp) {
		if (!initialBoundsSet) {
			ymax = ymaxTemp;
			ymin = yminTemp;
			xmax = xmaxTemp;
			xmin = xminTemp;
			initialBoundsSet = true;
		} else {
			ymax = (ymax > ymaxTemp) ? ymax : ymaxTemp;
			ymin = (ymin < yminTemp) ? ymin : yminTemp;
			xmax = (xmax > xmaxTemp) ? xmax : xmaxTemp;
			xmin = (xmin < xminTemp) ? xmin : xminTemp;
		}
	}

	/**
	 * Constructs a bounding box and returns it
	 * 
	 * @return bounding box
	 */
	public Rectangle getBoundingBox() {
		return new Rectangle(xmin, ymin, Math.abs(xmax - xmin), Math.abs(ymax - ymin));
	}
}

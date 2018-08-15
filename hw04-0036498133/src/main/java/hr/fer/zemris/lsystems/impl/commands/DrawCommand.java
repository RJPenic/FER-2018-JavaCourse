package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.math.Vector2D;

/**
 * Class that represents a command that draws a line from one point to another.
 * 
 * @author Rafael Josip Penić
 * @version 1.0
 *
 */
public class DrawCommand implements Command {

	/**
	 * Variable that determines how long the drawn line will be (step *
	 * effectiveShift)
	 */
	private double step;

	/**
	 * Constructor for draw commands
	 * 
	 * @param step
	 *            double value that will be used as step value
	 */
	public DrawCommand(double step) {
		this.step = step;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		double currentX = ctx.getCurrentState().getCurrentPosition().getX();
		double currentY = ctx.getCurrentState().getCurrentPosition().getY();

		double newX = currentX
				+ ctx.getCurrentState().getTurtleOrientation().getX() * step * ctx.getCurrentState().getShift();
		double newY = currentY
				+ ctx.getCurrentState().getTurtleOrientation().getY() * step * ctx.getCurrentState().getShift();

		painter.drawLine(currentX, currentY, newX, newY, ctx.getCurrentState().getColor(), 1.f);

		ctx.getCurrentState().getCurrentPosition().translate(new Vector2D(newX - currentX, newY - currentY));
	}

}

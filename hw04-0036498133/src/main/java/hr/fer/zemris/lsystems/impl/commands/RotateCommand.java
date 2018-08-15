package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Class that represents rotate command that rotates turtle orientation vector.
 * 
 * @author Rafael Josip Penić
 * @version 1.0
 *
 */
public class RotateCommand implements Command {

	/**
	 * Angle of rotation
	 */
	private double angle;

	/**
	 * Constructor for rotate commands.
	 * 
	 * @param angle
	 *            Angle of rotation
	 */
	public RotateCommand(double angle) {
		this.angle = angle;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.getCurrentState().getTurtleOrientation().rotate(angle);
	}
}

package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Class that represents scale command that changes turtle shift value depending
 * on given factor.
 * 
 * @author Rafael Josip Penić
 * @version 1.0
 *
 */
public class ScaleCommand implements Command {

	/**
	 * Factor of scaling
	 */
	private double factor;

	/**
	 * Constructor for scale commands.
	 * 
	 * @param factor
	 *            factor of scaling
	 */
	public ScaleCommand(double factor) {
		this.factor = factor;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.getCurrentState().setShift(factor * ctx.getCurrentState().getShift());
	}

}

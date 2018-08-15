package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.lsystems.Painter;

/**
 * Interface that represents commands.
 * 
 * @author Rafael Josip Penić
 * @version 1.0
 *
 */
public interface Command {

	/**
	 * Method that executes the command.
	 * 
	 * @param ctx
	 *            current context
	 * @param painter
	 *            painter object that makes drawing possible
	 */
	void execute(Context ctx, Painter painter);
}

package hr.fer.zemris.lsystems.impl.commands;

import java.awt.Color;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Class that implements Command interface and represents the command that
 * changes the color of the line.
 * 
 * @author Rafael Josip Penić
 * @version 1.0
 *
 */
public class ColorCommand implements Command {

	/**
	 * Color that will be stored in the current Turtle State.
	 */
	private Color color;

	/**
	 * Constructor for color commands.
	 * 
	 * @param color
	 *            color that will be stored in the current Turtle State
	 */
	public ColorCommand(Color color) {
		this.color = color;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.getCurrentState().setColor(color);
	}

}

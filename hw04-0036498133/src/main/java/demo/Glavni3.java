package demo;

import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;

/**
 * Demo class that draws various fractals depending on imported file.
 * 
 * @author Rafael Josip Penić
 * @version 1.0
 *
 */
public class Glavni3 {

	/**
	 * Main method.
	 * 
	 * @param args
	 *            command line arguments
	 */
	public static void main(String[] args) {
		LSystemViewer.showLSystem(LSystemBuilderImpl::new);
	}
}

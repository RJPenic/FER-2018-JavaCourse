package demo;

import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilderProvider;
import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;

/**
 * Demo class that shows how configureFromText method works.
 * 
 * @author Rafael Josip Penić
 * @version 1.0
 *
 */
public class Glavni2 {

	/**
	 * Main method
	 * 
	 * @param args
	 *            Command line arguments
	 */
	public static void main(String[] args) {
		LSystemViewer.showLSystem(createKochCurve(LSystemBuilderImpl::new));
	}

	/**
	 * Static method that builds LSystem object using configureFromTextMethod
	 * 
	 * @param provider
	 *            creates LSystemBuilder that will then be modified
	 * @return LSystem object gotten as a result of provider and configureFromText
	 *         usage
	 */
	private static LSystem createKochCurve(LSystemBuilderProvider provider) {
		String[] data = new String[] { "origin 0.05 0.4", "angle 0", "unitLength 0.9",
				"unitLengthDegreeScaler 1.0/3.0", "   ", "command F draw 1", "command + rotate 60",
				"command - rotate 	-60", "", "axiom F", "", "production F F+F--F+F " };

		return provider.createLSystemBuilder().configureFromText(data).build();
	}

}

package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * Class used for drawing a Newton fractal from given complex numbers using
 * Newton-Raphson iteration
 * 
 * @author Rafael Josip Penić
 *
 */
public class Newton {

	/**
	 * Main method in which the Newton fractal is drawn
	 * 
	 * @param args
	 *            command line arguments
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
		System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");

		int i = 1;
		List<Complex> tempComplexList = new ArrayList<>();

		while (true) {
			System.out.print("Root " + i + "> ");
			String temp = sc.nextLine();
			if (temp.toLowerCase().equals("done")) {
				if (i > 2) {
					break;
				} else {
					System.out.println("You must enter at least two roots.");
					continue;
				}
			}

			Complex c = null;
			try {
				c = parseComplex(temp);
			} catch (NumberFormatException ex) {
				System.out.println("Invalid entry. Please enter the root again.");
				continue;
			}

			tempComplexList.add(c);
			i++;
		}

		sc.close();
		System.out.println("Image of fractal will appear shortly. Thank you.");

		Complex[] tempArray = new Complex[tempComplexList.size()];
		tempComplexList.toArray(tempArray);
		ComplexRootedPolynomial poly = new ComplexRootedPolynomial(tempArray);

		try {
			FractalViewer.show(new IFractalProducerImpl(poly));
		} catch (RuntimeException ex) {
			System.out.println("Error while drawing.");
		}
	}

	/**
	 * Parses given string into corresponding complex number
	 * 
	 * @param s
	 *            string to be parsed
	 * @return complex number gotten as result of parsing
	 * @throws NumberFormatException
	 *             in case given string cannot be parsed into complex number
	 */
	private static Complex parseComplex(String s) throws NumberFormatException {
		s = s.trim();

		if (!s.contains("i")) {
			return new Complex(Double.parseDouble(s), 0);
		} else {
			String temp = s.replaceAll("\\s+", "");
			int dividingIndex = temp.indexOf('i');

			if (dividingIndex == 0) {
				String imag = temp.replaceFirst("i", "");
				return (imag.isEmpty()) ? new Complex(0, 1) : new Complex(0, Double.parseDouble(imag));
			} else {
				String imag = temp.substring(dividingIndex - 1).replaceFirst("i", "");
				String real = temp.substring(0, dividingIndex - 1);

				if (imag.equals("-")) {
					imag = "-1";
				} else if (imag.equals("+")) {
					imag = "1";
				}

				return (real.isEmpty()) ? new Complex(0, Double.parseDouble(imag))
						: new Complex(Double.parseDouble(real), Double.parseDouble(imag));
			}
		}
	}
}

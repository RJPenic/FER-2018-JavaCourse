package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet which constructs a list of threetuple objects(number, its sine and
 * cosine) and then forwards it. List consists of threetuples of numbers between
 * parameters a and b.
 * 
 * @author Rafael Josip Penić
 *
 */
public class TrigonometryComputer extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String sa = req.getParameter("a");
		String sb = req.getParameter("b");

		Integer a;
		try {
			a = Integer.parseInt(sa);
		} catch (NumberFormatException ex) {
			a = 0;
		}

		Integer b;
		try {
			b = Integer.parseInt(sb);
		} catch (NumberFormatException ex) {
			b = 360;
		}

		if (a > b) {
			// swapping values of a and b
			int temp = a;
			a = b;
			b = temp;
		}

		if (b > a + 720) {
			b = 720;
		}

		List<Threetuple> list = new ArrayList<>();

		for (int i = a; i <= b; i++) {
			list.add(new Threetuple(i, Math.sin(Math.toRadians(i)), Math.cos(Math.toRadians(i))));
		}

		req.setAttribute("values", list);
		req.getRequestDispatcher("/WEB-INF/pages/trigonometric.jsp").forward(req, resp);
	}

	/**
	 * Class representing a triplet that consists of a number, its sine and its
	 * cosine.
	 * 
	 * @author Rafael Josip Penić
	 *
	 */
	public static class Threetuple {

		/**
		 * Can be any integer number which sine and cosine value will be stored in this
		 * object
		 */
		private int number;

		/**
		 * Numbers sine
		 */
		private double sine;

		/**
		 * Numbers cosine
		 */
		private double cosine;

		/**
		 * Constructor for Threetuple objects
		 * 
		 * @param sine
		 *            sine of the given number
		 * @param cosine
		 *            cosine of the given number
		 */
		public Threetuple(int number, double sine, double cosine) {
			this.number = number;
			this.sine = sine;
			this.cosine = cosine;
		}

		/**
		 * Getter for the number
		 * 
		 * @return number
		 */
		public int getNumber() {
			return number;
		}

		/**
		 * Getter for sine of the number
		 * 
		 * @return numbers sine
		 */
		public double getSine() {
			return sine;
		}

		/**
		 * Getter for cosine of the number
		 * 
		 * @return numbers cosine
		 */
		public double getCosine() {
			return cosine;
		}

	}
}

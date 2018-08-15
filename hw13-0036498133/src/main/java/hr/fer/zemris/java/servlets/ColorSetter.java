package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.awt.Color;

/**
 * Servlet that changes background color of the web application depending on the
 * color parameter. In case given color is not available, background color will
 * be set to white.
 * 
 * @author Rafael Josip Penić
 *
 */
public class ColorSetter extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * Map storing all currently available colors
	 */
	private static Map<String, Color> colorMap = Map.of("RED", Color.RED, "WHITE", Color.WHITE, "GREEN", Color.GREEN,
			"CYAN", Color.CYAN);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String colorString = req.getParameter("color");

		if (colorString == null) {
			colorString = "";
		}

		Color color = colorMap.get(colorString.toUpperCase());

		if (color == null) {
			color = Color.WHITE;
		}

		String hexColor = "#" + Integer.toHexString(color.getRGB()).substring(2);

		req.getSession().setAttribute("pickedBgColor", hexColor);
		req.getRequestDispatcher("color.jsp").forward(req, resp);
	}
}

package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet that sets text color and text font attribute on random value and
 * forwards it to appropriate jsp file.
 * 
 * @author Rafael Josip Penić
 *
 */
public class FunnyStoryFontGenerator extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * List from which the color of the text will be randomly chosen
	 */
	private static List<String> colorList = List.of("blue", "black", "magenta", "orange", "pink", "gray");

	/**
	 * List from which the font name of the text will be randomly chosen
	 */
	private static List<String> fontList = List.of("Comic Sans MS", "Times New Roman", "Verdana", "Courier New");

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Random rand = new Random();

		String color = colorList.get(rand.nextInt(colorList.size()));
		String font = fontList.get(rand.nextInt(fontList.size()));

		req.setAttribute("textColor", color);
		req.setAttribute("textFont", font);
		req.getRequestDispatcher("/WEB-INF/pages/stories/funny.jsp").forward(req, resp);
	}
}

package hr.fer.zemris.java.servletDB;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "polls-index", urlPatterns = { "/index.html" })
/**
 * Servlet that redirects to index which offers user to choose between various
 * polls.
 * 
 * @author Rafael Josip Penić
 *
 */
public class IndexServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendRedirect(req.getContextPath() + "/servleti/index.html");
	}
}

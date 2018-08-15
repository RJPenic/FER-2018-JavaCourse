package hr.fer.zemris.java.servletDB;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.dao.DAOException;
import hr.fer.zemris.java.dao.DAOProvider;
import hr.fer.zemris.java.model.Poll;

@WebServlet(name = "obtain-poll", urlPatterns = { "/servleti/index.html" })
/**
 * Servlet that constructs list with all available current polls and forwards it
 * to appropriate JSP file
 * 
 * @author Rafael Josip Penić
 *
 */
public class PollsServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Poll> pollList = null;
		try {
			pollList = DAOProvider.getDao().getPollList();
		} catch (DAOException e) {
			resp.sendError(500, "Internal Server Error");
			return;
		}

		req.setAttribute("pollList", pollList);
		req.getRequestDispatcher("/WEB-INF/pages/polls.jsp").forward(req, resp);
	}
}

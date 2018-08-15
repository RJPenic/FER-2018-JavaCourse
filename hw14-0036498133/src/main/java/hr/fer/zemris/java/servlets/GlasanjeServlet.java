package hr.fer.zemris.java.servlets;

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
import hr.fer.zemris.java.model.PollOption;

@WebServlet(name = "voting", urlPatterns = { "/servleti/glasanje" })
/**
 * Servlet that loads entries from database and stores them in appropriate
 * attribute as a list and then forwards it.
 * 
 * @author Rafael Josip Penić
 *
 */
public class GlasanjeServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		long pollID;
		try {
			pollID = Long.parseLong(req.getParameter("pollID"));
		} catch (NumberFormatException | NullPointerException ex) {
			resp.sendError(400, "Bad Request");
			return;
		}

		List<PollOption> list = null;
		try {
			list = DAOProvider.getDao().getPollOptionsFromPoll(pollID);
			Poll poll = DAOProvider.getDao().getPollWithId(pollID);
			
			req.setAttribute("title", poll.getTitle());
			req.setAttribute("message", poll.getMessage());
		} catch (DAOException ex) {
			resp.sendError(500, "Internal Server Error");
			return;
		} catch (NullPointerException ex) {
			resp.sendError(404, "Not Found");
			return;
		}

		req.setAttribute("list", list);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
	}
}

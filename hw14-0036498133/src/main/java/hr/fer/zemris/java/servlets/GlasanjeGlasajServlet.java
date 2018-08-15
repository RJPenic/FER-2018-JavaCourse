package hr.fer.zemris.java.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.dao.DAOException;
import hr.fer.zemris.java.dao.DAOProvider;
import hr.fer.zemris.java.model.PollOption;

@WebServlet(name = "voting-vote", urlPatterns = { "/servleti/glasanje-glasaj" })
/**
 * Servlet responsible for registering votes and updating database that contains
 * total results of the voting
 * 
 * @author Rafael Josip Penić
 *
 */
public class GlasanjeGlasajServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String idString = req.getParameter("id");
		
		long id = 0;
		try {
			id = Long.parseLong(idString);
		} catch(NumberFormatException|NullPointerException ex) {
			resp.sendError(400, "Bad Request");
		}
		
		long pollID = 0;
		synchronized (this) {
			try {
				DAOProvider.getDao().incrementPollOptionVotes(id);
				PollOption pollOption = DAOProvider.getDao().getPollOptionWithId(id);
				pollID = pollOption.getPollID();
			} catch(DAOException ex) {
				resp.sendError(500, "Internal server error");
				return;
			}
		}

		resp.sendRedirect(req.getContextPath() + "/servleti/glasanje-rezultati?pollID=" + pollID);
	}
}

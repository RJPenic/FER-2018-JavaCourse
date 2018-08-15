package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.dao.DAOException;
import hr.fer.zemris.java.dao.DAOProvider;
import hr.fer.zemris.java.model.PollOption;

@WebServlet(name = "voting-results", urlPatterns = { "/servleti/glasanje-rezultati" })
/**
 * Servlet responsible for showing results to the user after his/her vote has
 * been registered. It constructs a list with total results and a list
 * containing winning bands.
 * 
 * @author Rafael Josip Penić
 *
 */
public class GlasanjeRezultatiServlet extends HttpServlet {

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

		List<PollOption> resultsList = null;
		try {
			resultsList = DAOProvider.getDao().getPollOptionsFromPoll(pollID);
		} catch (DAOException e) {
			resp.sendError(500, "Internal Server Error");
			return;
		}

		resultsList.sort((v1, v2) -> v2.getVotesCount().compareTo(v1.getVotesCount()));

		List<PollOption> winners = constructWinnersList(resultsList);

		req.setAttribute("winners", winners);
		req.setAttribute("results", resultsList);
		req.setAttribute("pollID", pollID);

		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
	}

	/**
	 * Method that constructs a list of winners using the given sorted results list
	 * 
	 * @param resultsList
	 *            sorted result list
	 * @return list containing winner entries
	 */
	private static List<PollOption> constructWinnersList(List<PollOption> resultsList) {
		List<PollOption> winners = new ArrayList<>();

		if (!resultsList.isEmpty()) {
			long winningResult = resultsList.get(0).getVotesCount();

			for (PollOption res : resultsList) {
				if (res.getVotesCount() != winningResult)
					break;

				winners.add(res);
			}
		}

		return winners;
	}
}

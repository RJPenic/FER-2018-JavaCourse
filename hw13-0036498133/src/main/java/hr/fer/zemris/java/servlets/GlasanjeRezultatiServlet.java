package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.util.UtilLoader;
import hr.fer.zemris.java.util.UtilLoader.BandResult;

@WebServlet(name = "voting-results", urlPatterns = { "/glasanje-rezultati" })
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

		List<BandResult> bandResultsList = UtilLoader.mergeDefinitionAndResult(req, "/WEB-INF/glasanje-rezultati.txt",
				"/WEB-INF/glasanje-definicija.txt");

		List<BandResult> bandsWinners = new ArrayList<>();
		if (!bandResultsList.isEmpty()) {
			int winningResult = bandResultsList.get(0).getVoteCount();

			for (BandResult res : bandResultsList) {
				if (res.getVoteCount() != winningResult)
					break;

				bandsWinners.add(res);
			}
		}

		req.setAttribute("winners", bandsWinners);
		req.setAttribute("bandResults", bandResultsList);

		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
	}
}

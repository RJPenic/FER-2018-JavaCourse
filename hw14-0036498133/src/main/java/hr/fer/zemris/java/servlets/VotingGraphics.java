package hr.fer.zemris.java.servlets;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.charts.PieChart;
import hr.fer.zemris.java.dao.DAOException;
import hr.fer.zemris.java.dao.DAOProvider;
import hr.fer.zemris.java.model.PollOption;

@WebServlet(name = "voting-chart", urlPatterns = { "/servleti/glasanje-grafika" })
/**
 * Servlet that constructs image of a pie chart showing results of voting and
 * then sends that picture to response output stream
 * 
 * @author Rafael Josip Penić
 *
 */
public class VotingGraphics extends HttpServlet {

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
		
		PieChart chart = new PieChart("Statistika prikazana grafikonom");
		
		try {
			List<PollOption> list = DAOProvider.getDao().getPollOptionsFromPoll(pollID);
			for(PollOption opt : list) {
				chart.addToDataset(opt.getOptionTitle(), opt.getVotesCount().intValue());
			}
		} catch (DAOException ex) {
			resp.sendError(500, "Internal server error");
			return;
		}

		BufferedImage chartImage = chart.createImage();

		resp.setContentType("image/png");

		BufferedOutputStream bos = new BufferedOutputStream(resp.getOutputStream());
		ImageIO.write(chartImage, "png", bos);
		bos.close();
	}

}

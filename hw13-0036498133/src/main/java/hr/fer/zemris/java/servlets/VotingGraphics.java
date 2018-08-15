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
import hr.fer.zemris.java.util.UtilLoader;
import hr.fer.zemris.java.util.UtilLoader.BandResult;

@WebServlet(name = "voting-chart", urlPatterns = { "/glasanje-grafika" })
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
		PieChart chart = new PieChart("Najdraži bend");

		List<BandResult> bandResultsList = UtilLoader.mergeDefinitionAndResult(req, "/WEB-INF/glasanje-rezultati.txt",
				"/WEB-INF/glasanje-definicija.txt");

		for (BandResult res : bandResultsList) {
			chart.addToDataset(res.getBand().getName(), res.getVoteCount());
		}

		BufferedImage chartImage = chart.createImage();

		resp.setContentType("image/png");

		BufferedOutputStream bos = new BufferedOutputStream(resp.getOutputStream());
		ImageIO.write(chartImage, "png", bos);
		bos.close();
	}

}

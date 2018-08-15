package hr.fer.zemris.java.servlets;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.charts.PieChart;

/**
 * Servlet that draws an image of a pie chart on response output stream.
 * 
 * @author Rafael Josip Penić
 *
 */
public class ImageDrawer extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PieChart chart = new PieChart("Which operating system are you using?");

		chart.addToDataset("Windows", 70);
		chart.addToDataset("Linux", 20);
		chart.addToDataset("Mac", 10);

		BufferedImage chartImage = chart.createImage();

		resp.setContentType("image/png");

		BufferedOutputStream bos = new BufferedOutputStream(resp.getOutputStream());
		ImageIO.write(chartImage, "png", bos);
		bos.close();
	}
}

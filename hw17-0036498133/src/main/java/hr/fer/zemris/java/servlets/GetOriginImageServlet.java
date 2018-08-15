package hr.fer.zemris.java.servlets;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = { "/servlets/getOriginImage" })
/**
 * Servlet responsible for "forwarding" original image to the web-app
 * 
 * @author Rafael Josip Penić
 *
 */
public class GetOriginImageServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * String representation of the "main" images directory
	 */
	private static final String REAL_IMAGE_PATH = "WEB-INF/slike";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String src = req.getParameter("src");

		Path originPath = Paths.get(req.getServletContext().getRealPath(REAL_IMAGE_PATH + "/" + src));

		BufferedImage bImage = ImageIO.read(new BufferedInputStream(Files.newInputStream(originPath)));

		resp.setContentType("image/png");

		BufferedOutputStream bos = new BufferedOutputStream(resp.getOutputStream());
		ImageIO.write(bImage, "png", bos);
		bos.flush();
		bos.close();
	}
}

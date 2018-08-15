package hr.fer.zemris.java.servlets;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
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

@WebServlet(urlPatterns = { "/servlets/getImage" })
/**
 * Servlet used to "forward" thumbnails(and create them if needed) to the
 * web-app
 * 
 * @author Rafael Josip Penić
 *
 */
public class GetImageServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * String representation of the thumbnails directory
	 */
	private static final String THUMBNAILS_PATH = "WEB-INF/thumbnails";

	/**
	 * String representation of the "main" images directory
	 */
	private static final String REAL_IMAGE_PATH = "WEB-INF/slike";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String src = req.getParameter("src");

		if (!new File(req.getServletContext().getRealPath(THUMBNAILS_PATH)).exists()) {
			new File(req.getServletContext().getRealPath(THUMBNAILS_PATH)).mkdir();
		}

		Path originPath = Paths.get(req.getServletContext().getRealPath(REAL_IMAGE_PATH + "/" + src));
		Path thumbnailPath = Paths.get(req.getServletContext().getRealPath(THUMBNAILS_PATH + "/" + src));

		BufferedImage bim = ImageIO.read(new BufferedInputStream(Files.newInputStream(originPath)));

		if (!thumbnailPath.toFile().exists()) {
			// create thumbnail image
			Image scaledIm = bim.getScaledInstance(150, 150, Image.SCALE_DEFAULT);

			BufferedImage bScaledIm = new BufferedImage(scaledIm.getWidth(null), scaledIm.getHeight(null),
					BufferedImage.TYPE_INT_ARGB);

			Graphics2D g2d = bScaledIm.createGraphics();
			g2d.drawImage(scaledIm, null, null);
			g2d.dispose();

			ImageIO.write(bScaledIm, "png", new BufferedOutputStream(Files.newOutputStream(thumbnailPath)));
		}

		BufferedImage bScaled = ImageIO.read(new BufferedInputStream(Files.newInputStream(thumbnailPath)));

		resp.setContentType("image/png");

		BufferedOutputStream bos = new BufferedOutputStream(resp.getOutputStream());
		ImageIO.write(bScaled, "png", bos);
		bos.flush();
		bos.close();
	}
}

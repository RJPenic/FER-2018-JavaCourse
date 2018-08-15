package hr.fer.zemris.java.webserver.workers;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Worker which draws a circle to a context output stream
 * 
 * @author Rafael Josip Penić
 *
 */
public class CircleWorker implements IWebWorker {

	/**
	 * Radius of the circle that will be drawn
	 */
	private static final int CIRCLE_RADIUS = 50;

	@Override
	public void processRequest(RequestContext context) throws Exception {
		BufferedImage bim = new BufferedImage(200, 200, BufferedImage.TYPE_3BYTE_BGR);

		Graphics2D g2d = bim.createGraphics();
		g2d.setColor(Color.RED);
		g2d.drawOval(CIRCLE_RADIUS, CIRCLE_RADIUS, 2 * CIRCLE_RADIUS, 2 * CIRCLE_RADIUS);
		g2d.fillOval(CIRCLE_RADIUS, CIRCLE_RADIUS, 2 * CIRCLE_RADIUS, 2 * CIRCLE_RADIUS);
		g2d.dispose();

		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		context.setMimeType("image/png");

		try {
			ImageIO.write(bim, "png", bos);
			context.write(bos.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}

package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.util.UtilLoader;

@WebServlet(name = "voting", urlPatterns = { "/glasanje" })
/**
 * Servlet that loads bands from bands definition file and stores them in
 * appropriate attribute as a list and then forwards it.
 * 
 * @author Rafael Josip Penić
 *
 */
public class GlasanjeServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<BandInfo> bands = UtilLoader.loadDefinition(req, "/WEB-INF/glasanje-definicija.txt");

		req.setAttribute("bands", bands);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
	}

	/**
	 * Class representing a band and contains basic information about band like its
	 * id, name and example of the song
	 * 
	 * @author Rafael Josip Penić
	 *
	 */
	public static class BandInfo {

		/**
		 * Name of the band
		 */
		private String name;

		/**
		 * String representing a link leading to a song of the band
		 */
		private String songLink;

		/**
		 * Bands identification sequence
		 */
		private String id;

		/**
		 * Constructor for BandInfo objects
		 * 
		 * @param name
		 *            bands name
		 * @param songLink
		 *            example of bands song(link to song)
		 * @param id
		 *            bands ID
		 */
		public BandInfo(String name, String songLink, String id) {
			Objects.requireNonNull(name, "Given name reference is null.");
			Objects.requireNonNull(songLink, "Given link string reference is null.");
			Objects.requireNonNull(id, "Given id string reference is null.");

			this.name = name;
			this.songLink = songLink;
			this.id = id;
		}

		/**
		 * Getter for bands name
		 * 
		 * @return bands name
		 */
		public String getName() {
			return name;
		}

		/**
		 * Getter for bands song link
		 * 
		 * @return bands song link
		 */
		public String getSongLink() {
			return songLink;
		}

		/**
		 * Getter for bands ID
		 * 
		 * @return bands ID
		 */
		public String getId() {
			return id;
		}

	}
}

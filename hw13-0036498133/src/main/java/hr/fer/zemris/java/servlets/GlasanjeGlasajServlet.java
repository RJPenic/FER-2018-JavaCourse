package hr.fer.zemris.java.servlets;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "voting-vote", urlPatterns = { "/glasanje-glasaj" })
/**
 * Servlet responsible for registering votes and updating file that contains
 * total results of the voting
 * 
 * @author Rafael Josip Penić
 *
 */
public class GlasanjeGlasajServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = req.getParameter("id");

		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");

		Path p = Paths.get(fileName);

		synchronized (this) {
			if (!p.toFile().exists()) {
				registerVoteWhenNotPresent(id, p, StandardOpenOption.CREATE);
			} else {
				incrementVoteCount(id, p);
			}
		}

		resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");
	}

	/**
	 * Method used to register vote when ID of the band that is voted for is not
	 * present in a file or if the result file does not exist
	 * 
	 * @param id
	 *            ID of the band that is voted for
	 * @param p
	 *            Path to file that contains results of the voting
	 * @param option
	 *            option that determines if the value will be appended on a file,
	 *            will file be created and etc.
	 * @throws IOException
	 *             in case of an error while writing
	 */
	private void registerVoteWhenNotPresent(String id, Path p, StandardOpenOption option) throws IOException {
		BufferedOutputStream bos = new BufferedOutputStream(Files.newOutputStream(p, option));
		bos.write((id + "\t1\n").getBytes());
		bos.close();
	}

	/**
	 * Method responsible for incrementing number of votes in the file
	 * 
	 * @param id
	 *            ID of the band that got vote
	 * @param p
	 *            path to a file containing results
	 * @throws IOException
	 *             in case of an error while writing or reading
	 */
	private void incrementVoteCount(String id, Path p) throws IOException {
		boolean alreadyPresentInFile = false;

		List<String> lines = Files.readAllLines(p);

		BufferedOutputStream bosTruncate = new BufferedOutputStream(
				Files.newOutputStream(p, StandardOpenOption.TRUNCATE_EXISTING));

		for (String line : lines) {
			if (line.startsWith(id + "\t")) {
				alreadyPresentInFile = true;
				String[] splittedArray = line.split("\t");
				bosTruncate
						.write((splittedArray[0] + "\t" + (1 + Integer.parseInt(splittedArray[1])) + "\n").getBytes());
			} else {
				bosTruncate.write((line + "\n").getBytes());
			}
		}

		bosTruncate.close();

		if (!alreadyPresentInFile) {
			registerVoteWhenNotPresent(id, p, StandardOpenOption.APPEND);
		}
	}
}

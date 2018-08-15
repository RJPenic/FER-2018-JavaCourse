package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.forms.CommentForm;
import hr.fer.zemris.java.forms.EditPostForm;
import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

@WebServlet("/servleti/author/*")
/**
 * Servlet used mainly to show entries of a certain user and is also responsible
 * for creating and editing posts
 * 
 * @author Rafael Josip Penić
 *
 */
public class BlogEntriesServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	/**
	 * Processes given request and response and can also edit and create new posts
	 * 
	 * @param req
	 *            request
	 * @param resp
	 *            response
	 */
	private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pathInfo = req.getPathInfo();

		String[] args = pathInfo.substring(1).split("/");

		if (args.length == 1) {
			List<BlogUser> list = DAOProvider.getDAO().getBlogUserWithNick(args[0]);
			if (list.isEmpty()) {
				resp.sendError(404, "Not Found");
				return;
			}

			List<BlogEntry> entryList = DAOProvider.getDAO().getBlogEntriesForUser(list.get(0));

			req.setAttribute("entryList", entryList);
			req.setAttribute("nickEnt", args[0]);

			req.getRequestDispatcher("/WEB-INF/pages/BlogEntriesList.jsp").forward(req, resp);
			return;
		} else if (args.length == 2) {
			if (args[1].equals("new")) {
				if (!args[0].equals(req.getSession().getAttribute("nick"))) {
					resp.sendError(403, "Forbidden");
					return;
				}

				EditPostForm f = new EditPostForm();

				req.setAttribute("form", f);

				req.getRequestDispatcher("/WEB-INF/pages/Create.jsp").forward(req, resp);

			} else if (args[1].endsWith("edit")) {
				if (!args[0].equals(req.getSession().getAttribute("nick"))) {
					resp.sendError(403, "Forbidden");
					return;
				}

				long id = 0;
				try {
					id = Long.parseLong(req.getParameter("blogID"));
				} catch (NumberFormatException ex) {
					resp.sendError(400, "Bad Request");
					return;
				}

				EditPostForm f = new EditPostForm();

				req.setAttribute("form", f);

				BlogEntry entry = DAOProvider.getDAO().getBlogEntry(id);

				if(entry == null) {
					resp.sendError(404, "Not Found");
					return;
				}
				
				f.setText(entry.getText());
				f.setTitle(entry.getTitle());
				f.setID(id);

				req.setAttribute("blogID", id);

				req.getRequestDispatcher("/WEB-INF/pages/Edit.jsp").forward(req, resp);

			} else {
				long id = 0;
				try {
					id = Long.parseLong(args[1]);
				} catch (NumberFormatException ex) {
					resp.sendError(404, "Not Found");
					return;
				}

				BlogEntry blogEntry = DAOProvider.getDAO().getBlogEntry(id);

				if (blogEntry == null) {
					resp.sendError(400, "Bad Request");
					return;
				}

				req.setAttribute("blogEntry", blogEntry);
				req.setAttribute("nickEnt", args[0]);
				req.setAttribute("commentList", blogEntry.getComments());

				CommentForm f = new CommentForm();
				req.setAttribute("form", f);

				req.getRequestDispatcher("/WEB-INF/pages/ShowEntry.jsp").forward(req, resp);
			}
		} else {
			resp.sendError(404, "Not Found");
		}
	}
}

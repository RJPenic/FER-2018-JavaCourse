package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.forms.EditPostForm;
import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;

@WebServlet("/servleti/updateEntry")
/**
 * Servlet responsible for updating entries after they are edited
 * 
 * @author Rafael Josip Penić
 *
 */
public class UpdateEntry extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Long blogID = null;
		try {
			blogID = Long.valueOf(req.getParameter("blogID"));
		} catch (NumberFormatException ex) {
			resp.sendError(400, "Bad Request");
			return;
		}

		String method = req.getParameter("method");

		if (!"Done".equals(method)) {
			resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/author/"
					+ req.getSession().getAttribute("nick"));
			return;
		}

		EditPostForm f = new EditPostForm();
		f.fillFromHttpRequest(req);
		f.validateProperties();

		if (f.errorsExist()) {
			req.setAttribute("form", f);
			req.setAttribute("blogID", blogID);
			req.getRequestDispatcher("/WEB-INF/pages/Edit.jsp").forward(req, resp);
			return;
		}

		BlogEntry entry = DAOProvider.getDAO().getBlogEntry(blogID);

		if (entry == null) {
			resp.sendError(400, "Bad Request");
			return;
		}

		entry.setText(f.getText());
		entry.setTitle(f.getTitle());

		req.setAttribute("form", f);

		resp.sendRedirect(
				req.getServletContext().getContextPath() + "/servleti/author/" + req.getSession().getAttribute("nick"));
	}

}

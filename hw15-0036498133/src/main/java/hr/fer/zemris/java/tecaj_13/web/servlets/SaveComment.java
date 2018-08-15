package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.forms.CommentForm;
import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogComment;

@WebServlet("/servleti/saveComment")
/**
 * Servlet responsible for saving comments in database
 * 
 * @author Rafael Josip Penić
 *
 */
public class SaveComment extends HttpServlet {

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
		CommentForm f = new CommentForm();
		f.fillFromHttpRequest(req);

		f.validateProperties();

		if (f.errorsExist()) {
			req.setAttribute("form", f);
			req.getRequestDispatcher("/servleti/author/" + req.getParameter("nick") + "/" + req.getParameter("blogID"))
					.forward(req, resp);
			return;
		}

		Long id = null;
		try {
			id = Long.parseLong(req.getParameter("blogID"));
		} catch (NumberFormatException | NullPointerException ex) {
			resp.sendError(400, "Bad Request");
			return;
		}

		BlogComment comment = new BlogComment();

		comment.setPostedOn(new Date());
		comment.setMessage(f.getMessage());
		comment.setBlogEntry(DAOProvider.getDAO().getBlogEntry(id));

		String email = f.getEmail();
		if (email == null) {
			comment.setUsersEMail(req.getSession().getAttribute("email").toString());
		} else {
			comment.setUsersEMail(f.getEmail());
		}

		DAOProvider.getDAO().insertNewComment(comment);

		resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/author/" + req.getParameter("nick")
				+ "/" + req.getParameter("blogID"));
	}
}

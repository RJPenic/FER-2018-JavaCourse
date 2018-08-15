package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.forms.EditPostForm;
import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

@WebServlet("/servleti/saveEntry")
/**
 * Servlet that handles process of saving entries after they are created
 * 
 * @author Rafael Josip Penić
 *
 */
public class SaveEntry extends HttpServlet {

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
		req.setCharacterEncoding("UTF-8");

		String method = req.getParameter("method");

		if (!"Done".equals(method)) {
			resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/main");
			return;
		}

		EditPostForm f = new EditPostForm();
		f.fillFromHttpRequest(req);
		f.validateProperties();

		if (f.errorsExist()) {
			req.setAttribute("form", f);
			req.getRequestDispatcher("/WEB-INF/pages/Create.jsp").forward(req, resp);
			return;
		}

		String nick = req.getSession().getAttribute("nick").toString();
		BlogEntry entry = new BlogEntry();

		entry.setComments(new ArrayList<>());
		entry.setCreatedAt(new Date());
		entry.setLastModifiedAt(new Date());

		BlogUser user = DAOProvider.getDAO().getBlogUserWithNick(nick).get(0);
		entry.setCreator(user);

		entry.setText(f.getText());
		entry.setTitle(f.getTitle());

		DAOProvider.getDAO().updateEntry(entry);

		resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/author/" + nick);
	}

}

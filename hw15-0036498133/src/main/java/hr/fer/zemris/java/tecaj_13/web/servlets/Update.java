package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.forms.RegistrationForm;
import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

@WebServlet("/servleti/save")
/**
 * Servlet that saves just registered users
 * 
 * @author Rafael Josip Penić
 *
 */
public class Update extends HttpServlet {

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
	 * Makes sure just registered users are safely stored in database
	 * 
	 * @param req
	 *            HTTP request
	 * @param resp
	 *            response
	 */
	private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");

		String method = req.getParameter("method");

		if (!"Register".equals(method)) {
			resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/main");
			return;
		}

		RegistrationForm f = new RegistrationForm();
		f.fillFromHttpRequest(req);
		f.validateProperties();

		if (f.errorsExist()) {
			req.setAttribute("form", f);
			req.getRequestDispatcher("/WEB-INF/pages/UserRegistration.jsp").forward(req, resp);
			return;
		}

		BlogUser user = new BlogUser();
		f.fillInUser(user);

		DAOProvider.getDAO().insertNewUser(user);

		resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/main");
	}
}

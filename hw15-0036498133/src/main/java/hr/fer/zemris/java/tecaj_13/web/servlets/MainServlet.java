package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.forms.LogInForm;
import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

@WebServlet("/servleti/main")
/**
 * Main servlet which mainly handles log in process
 * 
 * @author Rafael Josip Penić
 *
 */
public class MainServlet extends HttpServlet {

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
	 * Processes given request and response and handles log in process
	 * 
	 * @param req
	 *            request
	 * @param resp
	 *            response
	 */
	private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<String> userList = DAOProvider.getDAO().getBlogUsers().stream().map(v -> v.getNick())
				.collect(Collectors.toList());
		req.setAttribute("userList", userList);

		if (req.getParameter("nick") == null || req.getParameter("password") == null) {
			req.getRequestDispatcher("/WEB-INF/pages/main.jsp").forward(req, resp);
			return;
		}

		LogInForm f = new LogInForm();
		f.fillFromHttpRequest(req);

		List<BlogUser> list = DAOProvider.getDAO().getBlogUser(req.getParameter("nick"), req.getParameter("password"));

		if (list.isEmpty()) {
			f.setError("Invalid nickname and/or password!");
			req.setAttribute("form", f);
			req.getRequestDispatcher("/WEB-INF/pages/main.jsp").forward(req, resp);
			return;
		}

		req.getSession().setAttribute("id", list.get(0).getId());
		req.getSession().setAttribute("fn", list.get(0).getFirstName());
		req.getSession().setAttribute("ln", list.get(0).getLastName());
		req.getSession().setAttribute("nick", list.get(0).getNick());
		req.getSession().setAttribute("email", list.get(0).getEmail());

		resp.sendRedirect(req.getContextPath() + "/servleti/author/" + list.get(0).getNick());
	}
}

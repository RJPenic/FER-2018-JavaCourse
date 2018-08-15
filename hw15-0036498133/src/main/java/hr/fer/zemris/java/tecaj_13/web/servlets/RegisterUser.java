package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.forms.RegistrationForm;

@WebServlet(urlPatterns = { "/servleti/register", "/new" })
/**
 * Servlet responsible for registering users
 * 
 * @author Rafael Josip Penić
 *
 */
public class RegisterUser extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RegistrationForm f = new RegistrationForm();

		req.setAttribute("form", f);

		req.getRequestDispatcher("/WEB-INF/pages/UserRegistration.jsp").forward(req, resp);
	}
}

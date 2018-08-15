package hr.fer.zemris.java.servlets;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
/**
 * Servlet context listener that stores start time of the application when
 * context is initialized.
 * 
 * @author Rafael Josip Penić
 *
 */
public class RunningTimeListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// nothing to do
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		arg0.getServletContext().setAttribute("startTime", System.currentTimeMillis());
	}

}

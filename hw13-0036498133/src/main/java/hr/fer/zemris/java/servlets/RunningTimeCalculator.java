package hr.fer.zemris.java.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet that computes how long application has been running and then forwards
 * created string(that contains info about duration)
 * 
 * @author Rafael Josip Penić
 *
 */
public class RunningTimeCalculator extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String startTimeString = req.getServletContext().getAttribute("startTime").toString();

		Long startTime = Long.parseLong(startTimeString);
		Long currentTime = System.currentTimeMillis();

		Long runningTime = currentTime - startTime;

		Long days = runningTime / 1000 / 3600 / 24;
		Long hours = runningTime / 1000 / 3600;
		Long minutes = runningTime / 1000 / 60;
		Long seconds = runningTime / 1000;

		String runningTimeString = days + " days " + (hours - 24 * days) + " hours " + (minutes - 60 * hours)
				+ " minutes " + (seconds - 60 * minutes) + " seconds and " + (runningTime - 1000 * seconds)
				+ " miliseconds";

		req.setAttribute("runningTime", runningTimeString);
		req.getRequestDispatcher("/WEB-INF/pages/appinfo.jsp").forward(req, resp);
	}
}

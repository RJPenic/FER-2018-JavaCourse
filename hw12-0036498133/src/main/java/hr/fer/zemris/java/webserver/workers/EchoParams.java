package hr.fer.zemris.java.webserver.workers;

import java.util.Set;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Constructs a table with the given parameters and writes it to a output as
 * html
 * 
 * @author Rafael Josip Penić
 *
 */
public class EchoParams implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) throws Exception {
		Set<String> names = context.getParameterNames();

		StringBuilder sb = new StringBuilder();

		sb.append("<table border = \"1\">\n");

		for (String name : names) {
			sb.append("<tr>\n");
			sb.append("<th>" + name + "</th>" + "\n");
			sb.append("<th>" + context.getParameter(name) + "</th>" + "\n");
			sb.append("<tr>\n");
		}

		sb.append("</table>");

		context.setMimeType("text/html");

		context.write(sb.toString());
	}

}

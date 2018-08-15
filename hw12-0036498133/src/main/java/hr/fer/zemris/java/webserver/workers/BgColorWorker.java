package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Worker which colors the background of the index2.html
 * 
 * @author Rafael Josip Penić
 *
 */
public class BgColorWorker implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) throws Exception {
		String value = context.getParameter("bgcolor");

		if (value != null && value.matches("-?[0-9a-fA-F]+") && value.length() == 6) {
			context.setPersistentParameter("bgcolor", value);
			context.getDispatcher().dispatchRequest("/index2.html");
		} else {
			context.setMimeType("text/html");
			context.write("<a href=\"http://www.localhost.com:5721/index2.html\">HOME</a>");
			context.write("<p>Color hasn't been changed.</p>");
		}
	}

}

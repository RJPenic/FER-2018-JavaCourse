package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Worker which makes a simple "home" site providing some links and features
 * 
 * @author Rafael Josip Penić
 *
 */
public class Home implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) throws Exception {
		String value = context.getPersistentParameter("bgcolor");

		if (value != null) {
			context.setTemporaryParameter("background", value);
		} else {
			context.setTemporaryParameter("background", "7F7F7F");
		}

		context.getDispatcher().dispatchRequest("/private/home.smscr");
	}

}

package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Worker which sums up arguments and then constructs a table containing
 * arguments and result
 * 
 * @author Rafael Josip Penić
 *
 */
public class SumWorker implements IWebWorker {

	/**
	 * path in root directory(of the server) where calc smart script is located
	 */
	private static final String PRIVATE_CALC_SCRIPT = "/private/calc.smscr";

	@Override
	public void processRequest(RequestContext context) throws Exception {
		int a;
		try {
			a = Integer.parseInt(context.getParameter("a"));
		} catch (NumberFormatException ex) {
			a = 1;
		}

		int b;
		try {
			b = Integer.parseInt(context.getParameter("b"));
		} catch (NumberFormatException ex) {
			b = 2;
		}

		context.setTemporaryParameter("a", Integer.toString(a));
		context.setTemporaryParameter("b", Integer.toString(b));
		context.setTemporaryParameter("zbroj", Integer.toString(a + b));

		context.getDispatcher().dispatchRequest(PRIVATE_CALC_SCRIPT);
	}

}

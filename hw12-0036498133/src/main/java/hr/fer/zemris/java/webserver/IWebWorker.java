package hr.fer.zemris.java.webserver;

/**
 * Interface that defines web workers which process specific requests
 * 
 * @author Rafael Josip Penić
 *
 */
public interface IWebWorker {

	/**
	 * Processes the given request using the given context
	 * 
	 * @param context
	 *            request context used to process the request
	 * @throws Exception
	 *             in case of an error while writing(or reading)
	 */
	public void processRequest(RequestContext context) throws Exception;
}

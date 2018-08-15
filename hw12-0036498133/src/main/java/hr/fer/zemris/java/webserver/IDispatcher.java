package hr.fer.zemris.java.webserver;

/**
 * Interface often implemented by certain workers where analyzing of certain
 * path is necessary
 * 
 * @author Rafael Josip Penić
 *
 */
public interface IDispatcher {

	/**
	 * Method that will analyze the given path and determine how to process it.
	 * 
	 * @param urlPath
	 *            path to be analyzed
	 * @throws Exception
	 *             in case of an error
	 */
	void dispatchRequest(String urlPath) throws Exception;
}

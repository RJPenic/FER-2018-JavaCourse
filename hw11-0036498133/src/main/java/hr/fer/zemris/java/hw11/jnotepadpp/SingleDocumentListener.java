package hr.fer.zemris.java.hw11.jnotepadpp;

/**
 * Listener that is notified whenever there are changes on the single document
 * model
 * 
 * @author Rafael Josip Penić
 *
 */
public interface SingleDocumentListener {

	/**
	 * Notifies listener about update of modify status
	 * 
	 * @param model
	 *            model which got modify status changed
	 */
	void documentModifyStatusUpdated(SingleDocumentModel model);

	/**
	 * Notifies listener about update of documents file path
	 * 
	 * @param model
	 *            document which changed file path
	 */
	void documentFilePathUpdated(SingleDocumentModel model);
}
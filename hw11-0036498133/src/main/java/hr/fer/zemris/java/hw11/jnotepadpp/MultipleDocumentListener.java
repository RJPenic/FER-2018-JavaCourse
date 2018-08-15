package hr.fer.zemris.java.hw11.jnotepadpp;

/**
 * Listener that is notified when there are changes on multiple document model
 * 
 * @author Rafael Josip Penić
 *
 */
public interface MultipleDocumentListener {

	/**
	 * Notifies listener about current document change
	 * 
	 * @param previousModel
	 *            previous current document
	 * @param currentModel
	 *            new current document
	 */
	void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel);

	/**
	 * Notifies listener about document addition
	 * 
	 * @param model
	 *            document that was added
	 */
	void documentAdded(SingleDocumentModel model);

	/**
	 * Notifies listener about document removal
	 * 
	 * @param model
	 *            document that was removed
	 */
	void documentRemoved(SingleDocumentModel model);
}
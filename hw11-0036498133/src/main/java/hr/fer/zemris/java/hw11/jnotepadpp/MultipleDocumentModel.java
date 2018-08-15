package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;

/**
 * Represents a model capable of holding zero, one or more documents, where each
 * document and having a concept of current document – the one which is shown to
 * the user and on which user works
 * 
 * @author Rafael Josip Penić
 *
 */
public interface MultipleDocumentModel extends Iterable<SingleDocumentModel> {

	/**
	 * Creates a new single document model(with null path) and adds it into this
	 * multiple document model
	 * 
	 * @return created single document model
	 */
	SingleDocumentModel createNewDocument();

	/**
	 * Getter for a current document of the model
	 * 
	 * @return current document
	 */
	SingleDocumentModel getCurrentDocument();

	/**
	 * Loads document in the model from given path
	 * 
	 * @param path
	 *            path from which the single document model will be loaded
	 * @return loaded single document model
	 */
	SingleDocumentModel loadDocument(Path path);

	/**
	 * Saves given model in a given path
	 * 
	 * @param model
	 *            model that will be saved
	 * @param newPath
	 *            path in which the given model will be saved
	 */
	void saveDocument(SingleDocumentModel model, Path newPath);

	/**
	 * Closes given model and removes it from the intern document collection
	 * 
	 * @param model
	 *            document that will be closed
	 */
	void closeDocument(SingleDocumentModel model);

	/**
	 * Registers a listener to the model
	 * 
	 * @param l
	 *            listener to be registered
	 */
	void addMultipleDocumentListener(MultipleDocumentListener l);

	/**
	 * Unregisters given listener from the model
	 * 
	 * @param l
	 *            listener to be unregistered
	 */
	void removeMultipleDocumentListener(MultipleDocumentListener l);

	/**
	 * Returns number of documents currently in the model
	 * 
	 * @return number of documents
	 */
	int getNumberOfDocuments();

	/**
	 * Returns a document stored on the given index
	 * 
	 * @param index
	 *            index of the document that will be returned as result
	 * @return document stored on the given index
	 */
	SingleDocumentModel getDocument(int index);
}
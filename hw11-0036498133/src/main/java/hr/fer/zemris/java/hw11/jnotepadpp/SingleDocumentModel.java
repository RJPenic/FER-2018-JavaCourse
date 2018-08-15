package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;

import javax.swing.JTextArea;

/**
 * Represents a model of single document, having information about file path
 * from which document was loaded (can be null for new document), document
 * modification status and reference to Swing component which is used for
 * editing (each document has its own editor component).
 * 
 * @author Rafael Josip Penić
 *
 */
public interface SingleDocumentModel {

	/**
	 * Getter for a text area of the model
	 * 
	 * @return models text area component
	 */
	JTextArea getTextComponent();

	/**
	 * Getter for a file path of the model
	 * 
	 * @return models file path
	 */
	Path getFilePath();

	/**
	 * Setter for models file path
	 * 
	 * @param path
	 *            models new path
	 */
	void setFilePath(Path path);

	/**
	 * Getter for a modification flag
	 * 
	 * @return modification flag(true if the document has been modified)
	 */
	boolean isModified();

	/**
	 * Setter for models modification flag
	 * 
	 * @param modified
	 *            new value of the modification flag
	 */
	void setModified(boolean modified);

	/**
	 * Registers given listener to a model
	 * 
	 * @param l
	 *            listener that will be registered to a model
	 */
	void addSingleDocumentListener(SingleDocumentListener l);

	/**
	 * Unregisters given listener from the model
	 * 
	 * @param l
	 *            listener that will be unregistered from the model
	 */
	void removeSingleDocumentListener(SingleDocumentListener l);
}
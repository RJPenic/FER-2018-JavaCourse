package hr.fer.zemris.java.hw11.jnotepadpp;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;

/**
 * Default implementation of MultipleDocumentModel interface. Also extends
 * JTabbedPane
 * 
 * @author Rafael Josip Penić
 *
 */
public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {

	private static final long serialVersionUID = 1L;

	/**
	 * List containing all currently stored documents
	 */
	private List<SingleDocumentModel> singleDocumentList = new ArrayList<>();

	/**
	 * Current document of the multiple document model
	 */
	private SingleDocumentModel currentDocument;

	/**
	 * List containing registered listeners
	 */
	private List<MultipleDocumentListener> listeners = new ArrayList<>();

	@Override
	public Iterator<SingleDocumentModel> iterator() {
		return singleDocumentList.iterator();
	}

	@Override
	public SingleDocumentModel createNewDocument() {
		SingleDocumentModel tempDoc = new DefaultSingleDocumentModel(null, "");
		singleDocumentList.add(tempDoc);
		notifyAllAboutDocumentAddition(tempDoc);

		SingleDocumentModel previousCurrent = currentDocument;
		currentDocument = tempDoc;
		notifyAllAboutCurrentDocumentChange(previousCurrent, currentDocument);

		addListenerToNewDocument(tempDoc);
		tempDoc.setModified(true);

		return tempDoc;
	}

	@Override
	public SingleDocumentModel getCurrentDocument() {
		return currentDocument;
	}

	@Override
	public SingleDocumentModel loadDocument(Path path) {
		Objects.requireNonNull(path, "Given path reference is null.");

		byte[] byteArray;
		try {
			byteArray = Files.readAllBytes(path);
		} catch (IOException ex) {
			throw new RuntimeException("Error while reading from " + path.toAbsolutePath());
		}

		String text = new String(byteArray, StandardCharsets.UTF_8);

		SingleDocumentModel tempDoc = new DefaultSingleDocumentModel(path, text);

		int index = getIndexOfDocumentWithPath(path);
		if (index == -1) {
			singleDocumentList.add(tempDoc);
			notifyAllAboutDocumentAddition(tempDoc);
			addListenerToNewDocument(tempDoc);
			tempDoc.setModified(false);
			SingleDocumentModel previousCurrent = currentDocument;
			currentDocument = tempDoc;
			notifyAllAboutCurrentDocumentChange(previousCurrent, currentDocument);
			tempDoc.setFilePath(path);
			return tempDoc;
		} else {
			SingleDocumentModel previousCurrent = currentDocument;
			currentDocument = getDocument(index);
			notifyAllAboutCurrentDocumentChange(previousCurrent, currentDocument);
			return currentDocument;
		}
	}

	@Override
	public void saveDocument(SingleDocumentModel model, Path newPath) {
		Objects.requireNonNull(model, "Given single document model reference is null.");

		if (newPath == null && model.getFilePath() == null)
			throw new IllegalArgumentException("It is not possible to determine where should document be stored.");

		for (SingleDocumentModel doc : singleDocumentList) {
			if (!doc.equals(model) && doc.getFilePath() != null && doc.getFilePath().equals(newPath))
				throw new IllegalArgumentException("File " + newPath.toAbsolutePath() + " is already opened.");
		}

		Path tempPath;
		if (newPath == null) {
			tempPath = model.getFilePath();
		} else {
			tempPath = newPath;
		}

		byte[] byteArray = model.getTextComponent().getText().getBytes(StandardCharsets.UTF_8);

		try {
			Files.write(tempPath, byteArray);
			model.setFilePath(tempPath);
		} catch (IOException ex) {
			throw new RuntimeException("Error while writing in " + tempPath.toAbsolutePath());
		}

		model.setModified(false);
	}

	@Override
	public void closeDocument(SingleDocumentModel model) {

		notifyAllAboutDocumentRemoval(model);
		singleDocumentList.remove(model);

		if (currentDocument == model) {

			if (singleDocumentList.isEmpty()) {
				currentDocument = null;
			} else {
				currentDocument = singleDocumentList.get(singleDocumentList.size() - 1);
			}

			notifyAllAboutCurrentDocumentChange(model, currentDocument);
		}
	}

	@Override
	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		Objects.requireNonNull(l, "Given listener reference is null.");
		listeners.add(l);
	}

	@Override
	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		Objects.requireNonNull(l, "Given listener reference is null.");
		listeners.remove(l);
	}

	@Override
	public int getNumberOfDocuments() {
		return singleDocumentList.size();
	}

	@Override
	public SingleDocumentModel getDocument(int index) {
		return singleDocumentList.get(index);
	}

	/**
	 * Notifies all registered listeners about current document change
	 * 
	 * @param previousModel
	 *            previous current document
	 * @param currentModel
	 *            new current document
	 */
	private void notifyAllAboutCurrentDocumentChange(SingleDocumentModel previousModel,
			SingleDocumentModel currentModel) {
		for (MultipleDocumentListener listener : listeners) {
			listener.currentDocumentChanged(previousModel, currentModel);
		}
	}

	/**
	 * Notifies all registered listeners about document addition
	 * 
	 * @param model
	 *            added document
	 */
	private void notifyAllAboutDocumentAddition(SingleDocumentModel model) {
		for (MultipleDocumentListener listener : listeners) {
			listener.documentAdded(model);
		}
	}

	/**
	 * Notifies all registered listeners about document removal
	 * 
	 * @param model
	 *            removed document
	 */
	private void notifyAllAboutDocumentRemoval(SingleDocumentModel model) {
		for (MultipleDocumentListener listener : listeners) {
			listener.documentRemoved(model);
		}
	}

	/**
	 * Method that returns index of given document in the multiple document model
	 * 
	 * @param model
	 *            model which index is requested
	 * @return index of the given model or -1 if the model is not present
	 */
	public int getIndexOfSingleDocumentModel(SingleDocumentModel model) {
		for (int i = 0; i < singleDocumentList.size(); i++) {
			if (singleDocumentList.get(i) == model)
				return i;
		}

		return -1;
	}

	/**
	 * Method that returns index of document with given path
	 * 
	 * @param p
	 *            Path of the searched document
	 * @return index of the document with given path or -1 if the model with given
	 *         path is not present
	 */
	public int getIndexOfDocumentWithPath(Path p) {
		Objects.requireNonNull(p, "Given path reference is null.");
		
		for (int i = 0; i < singleDocumentList.size(); i++) {
			if (p.equals(singleDocumentList.get(i).getFilePath()))
				return i;
		}

		return -1;
	}

	/**
	 * Loads icon from the resource stream with the given name
	 * 
	 * @param s
	 *            name of the resource stream
	 * @return loaded icon
	 * @throws IOException
	 *             in case of an error while loading the icon
	 */
	private ImageIcon loadIcon(String s) throws IOException {
		InputStream is = this.getClass().getResourceAsStream(s);
		if (is == null)
			throw new RuntimeException("Error while loading icon.");
		byte[] bytes = is.readAllBytes();
		is.close();
		return new ImageIcon(bytes);
	}

	/**
	 * Method that adds initial listener when the document is created
	 * 
	 * @param tempDoc
	 *            created document
	 */
	private void addListenerToNewDocument(SingleDocumentModel tempDoc) {
		int index = getIndexOfSingleDocumentModel(tempDoc);

		singleDocumentList.get(index).addSingleDocumentListener(new SingleDocumentListener() {
			@Override
			public void documentModifyStatusUpdated(SingleDocumentModel model) {
				try {
					setIconAt(getIndexOfSingleDocumentModel(model), loadIcon(
							model.isModified() ? "icons/rsz_11red-diskette.png" : "icons/rsz_green-diskette.png"));
				} catch (IOException ex) {
					throw new RuntimeException("Error while loading the icon");
				}
			}

			@Override
			public void documentFilePathUpdated(SingleDocumentModel model) {
				int index = getIndexOfSingleDocumentModel(model);
				Path p = model.getFilePath();

				setTitleAt(index, p.getFileName().toString());
				setToolTipTextAt(index, p.toAbsolutePath().normalize().toString());
			}
		});
	}

	/**
	 * Setter for a current document
	 * 
	 * @param document
	 *            new current document
	 */
	public void setCurrentDocument(SingleDocumentModel document) {
		SingleDocumentModel previous = currentDocument;
		currentDocument = document;
		notifyAllAboutCurrentDocumentChange(previous, currentDocument);
	}
}

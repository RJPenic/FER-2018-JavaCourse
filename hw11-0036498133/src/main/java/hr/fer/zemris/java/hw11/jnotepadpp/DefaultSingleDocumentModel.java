package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Default implementation of SingleDocumentModel interface
 * 
 * @author Rafael Josip Penić
 *
 */
public class DefaultSingleDocumentModel implements SingleDocumentModel {

	/**
	 * Documents path
	 */
	private Path filePath;

	/**
	 * Text area showing content of the document
	 */
	private JTextArea textComponent;

	/**
	 * List containing registered listeners
	 */
	private List<SingleDocumentListener> listeners = new ArrayList<>();

	/**
	 * Modification status(modified = true)
	 */
	private boolean isModified = true;

	/**
	 * Constructor for DefaultSingleDocumentModel objects
	 * 
	 * @param filePath
	 *            documents file path
	 * @param text
	 *            text of the document
	 */
	public DefaultSingleDocumentModel(Path filePath, String text) {
		Objects.requireNonNull(text, "Given text string reference is null");

		this.filePath = filePath;
		textComponent = new JTextArea(text);

		addInitialModificationListener();
	}

	/**
	 * Adds initial listener to the text component which monitors sets modification
	 * flag whenever document is modified
	 */
	private void addInitialModificationListener() {
		textComponent.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				changedUpdate(e);
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				changedUpdate(e);
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				setModified(true);
			}
		});
	}

	@Override
	public JTextArea getTextComponent() {
		return textComponent;
	}

	@Override
	public Path getFilePath() {
		return filePath;
	}

	@Override
	public void setFilePath(Path path) {
		Objects.requireNonNull(path, "Given path reference is null.");
		this.filePath = path;
		notifyAllListenersAboutPathChange();
	}

	private void notifyAllListenersAboutPathChange() {
		for (SingleDocumentListener listener : listeners) {
			listener.documentFilePathUpdated(this);
		}
	}

	@Override
	public boolean isModified() {
		return isModified;
	}

	@Override
	public void setModified(boolean modified) {
		isModified = modified;
		notifyAllListenersAboutModificationFlagChange();
	}

	private void notifyAllListenersAboutModificationFlagChange() {
		for (SingleDocumentListener listener : listeners) {
			listener.documentModifyStatusUpdated(this);
		}
	}

	@Override
	public void addSingleDocumentListener(SingleDocumentListener l) {
		Objects.requireNonNull(l, "Given listener reference is null.");
		listeners.add(l);
	}

	@Override
	public void removeSingleDocumentListener(SingleDocumentListener l) {
		Objects.requireNonNull(l, "Given listener reference is null.");
		listeners.remove(l);
	}
}

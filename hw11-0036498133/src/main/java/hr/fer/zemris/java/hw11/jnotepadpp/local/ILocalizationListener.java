package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * Defines listeners which want to be notified when the language is changed
 * 
 * @author Rafael Josip Penić
 *
 */
public interface ILocalizationListener {
	/**
	 * Method that notifies a listener that the language changed
	 */
	void localizationChanged();
}

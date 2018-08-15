package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * Interface that can get localized texts
 * 
 * @author Rafael Josip Penić
 *
 */
public interface ILocalizationProvider {
	/**
	 * Adds a listener to a provider
	 * 
	 * @param l
	 *            listener to be added
	 */
	void addLocalizationListener(ILocalizationListener l);

	/**
	 * Removes a listener from a provider
	 * 
	 * @param l
	 *            listener to be removed
	 */
	void removeLocalizationListener(ILocalizationListener l);

	/**
	 * Gets appropriate text registered under the certain key depending on a current
	 * language
	 * 
	 * @param key
	 *            text key
	 * @return text in the current language of the provider
	 */
	String getString(String key);
}

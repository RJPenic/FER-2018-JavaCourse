package hr.fer.zemris.java.hw11.jnotepadpp.local;

import javax.swing.AbstractAction;

/**
 * Action which sets current name as value of localized key
 * 
 * @author Rafael Josip Penić
 *
 */
public abstract class LocalizableAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	/**
	 * Actions localization provider
	 */
	private ILocalizationProvider prov;

	/**
	 * Key of the translation
	 */
	private String key;

	/**
	 * Listener which "sets" new translation when the language is changed
	 */
	private ILocalizationListener listener = new ILocalizationListener() {

		@Override
		public void localizationChanged() {
			putValue(key, prov.getString(key));
		}
	};

	/**
	 * Constructor for LocalizableAction objects
	 * 
	 * @param key
	 *            key of the translation
	 * @param lp
	 *            action localization provider(used for translation)
	 */
	public LocalizableAction(String key, ILocalizationProvider lp) {
		this.key = key;
		this.prov = lp;
		putValue(key, lp.getString(key));
		lp.addLocalizationListener(listener);
	}

}

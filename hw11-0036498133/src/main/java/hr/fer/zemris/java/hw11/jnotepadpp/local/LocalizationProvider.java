package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Implementation of AbstractLocalizationProvider which gets localized texts
 * 
 * @author Rafael Josip Penić
 *
 */
public class LocalizationProvider extends AbstractLocalizationProvider {
	/**
	 * Current language of the provider
	 */
	private String language;

	/**
	 * Resource bundle from which the translations are gotten
	 */
	private ResourceBundle bundle;

	/**
	 * static instance of localization provider
	 */
	private static LocalizationProvider instance = new LocalizationProvider();

	/**
	 * Private constructor for the provider which sets English as the provider
	 * language
	 */
	private LocalizationProvider() {
		setLanguage("en");
	}

	/**
	 * Getter for stored instance of the localization provider
	 * 
	 * @return
	 */
	public static LocalizationProvider getInstance() {
		return instance;
	}

	/**
	 * Setter for providers language
	 * 
	 * @param language
	 *            new language
	 */
	public void setLanguage(String language) {
		this.language = language;
		fire();
	}

	@Override
	public String getString(String key) {
		Locale locale = Locale.forLanguageTag(language);
		bundle = ResourceBundle.getBundle("hr.fer.zemris.java.hw11.jnotepadpp.local.prijevodi", locale);
		return bundle.getString(key);
	}

	/**
	 * Getter for providers language
	 * 
	 * @return language
	 */
	public String getLanguage() {
		return language;
	}

}

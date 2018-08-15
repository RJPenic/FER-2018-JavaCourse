package hr.fer.zemris.java.hw11.jnotepadpp.local;

import javax.swing.JLabel;

/**
 * Class representing a localized label which changes content whenever the
 * language is changed
 * 
 * @author Rafael Josip Penić
 *
 */
public class LJLabel extends JLabel {

	private static final long serialVersionUID = 1L;

	/**
	 * Labels localization provider
	 */
	private ILocalizationProvider prov;

	/**
	 * Key that determines labels content
	 */
	private String key;

	/**
	 * listener that will change text of the label whenever the language is changed
	 */
	private ILocalizationListener listener = new ILocalizationListener() {

		@Override
		public void localizationChanged() {
			updateLabel();
		}
	};

	/**
	 * Constructor for LJLabel objects
	 * 
	 * @param key
	 *            key of the label content
	 * @param lp
	 *            Labels localization provider
	 */
	public LJLabel(String key, ILocalizationProvider lp) {
		this.key = key;
		this.prov = lp;
		updateLabel();
		lp.addLocalizationListener(listener);
	}

	/**
	 * Updates text of the label
	 */
	private void updateLabel() {
		this.setText(prov.getString(key));
	}
}

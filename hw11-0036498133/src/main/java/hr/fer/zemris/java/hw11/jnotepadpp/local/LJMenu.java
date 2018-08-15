package hr.fer.zemris.java.hw11.jnotepadpp.local;

import javax.swing.JMenu;

/**
 * Localized JMenu Swing component which changes its name whenever the language
 * changes
 * 
 * @author Rafael Josip Penić
 *
 */
public class LJMenu extends JMenu {

	private static final long serialVersionUID = 1L;

	/**
	 * Components provider
	 */
	private ILocalizationProvider prov;

	/**
	 * Key that determines name of the menu
	 */
	private String key;

	/**
	 * Listener that changes menu name whenever the language changes
	 */
	private ILocalizationListener listener = new ILocalizationListener() {

		@Override
		public void localizationChanged() {
			updateMenu();
		}
	};

	/**
	 * Constructor for LJMenu objects
	 * 
	 * @param key
	 *            key for the LJMenu title
	 * @param lp
	 *            Menus localization provider
	 */
	public LJMenu(String key, ILocalizationProvider lp) {
		this.key = key;
		this.prov = lp;
		updateMenu();
		lp.addLocalizationListener(listener);
	}

	/**
	 * Updates menus name(title) whenever the language changes
	 */
	private void updateMenu() {
		this.setText(prov.getString(key));
	}
}

package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * Bridge(proxy) between source object which stores localized texts and
 * components that need them
 * 
 * @author Rafael Josip Penić
 *
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider {

	/**
	 * Parent provider which provides translations
	 */
	private ILocalizationProvider parent;

	/**
	 * status of connection(true = connected)
	 */
	private boolean connected;

	/**
	 * Listener which notifies all listeners about language change
	 */
	private ILocalizationListener listener = new ILocalizationListener() {

		@Override
		public void localizationChanged() {
			fire();
		}
	};

	/**
	 * Constructor for LocalizationProviderBridge objects
	 * 
	 * @param parent
	 *            parent provider
	 */
	public LocalizationProviderBridge(ILocalizationProvider parent) {
		this.parent = parent;
	}

	/**
	 * Method that connects and registers intern listener to parent provider
	 */
	public void connect() {
		connected = true;

		if (listener != null) {
			parent.addLocalizationListener(listener);
		}

	}

	/**
	 * Method that disconnects and unregisters intern listener from parent provider
	 */
	public void disconnect() {
		connected = false;

		if (listener != null) {
			parent.removeLocalizationListener(listener);
		}
	}

	@Override
	public String getString(String key) {
		return parent.getString(key);
	}

	/**
	 * Getter for connection flag
	 * 
	 * @return connection flag
	 */
	public boolean isConnected() {
		return connected;
	}
}

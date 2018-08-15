package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class that implements memory of listeners, their registering and
 * unregistering and sending notification about language change.
 * 
 * @author Rafael Josip Penić
 *
 */
public abstract class AbstractLocalizationProvider implements ILocalizationProvider {

	/**
	 * Registered listeners of AbstractLocalizationProvider
	 */
	private List<ILocalizationListener> listeners;

	/**
	 * Default constructor which allocates listeners list
	 */
	public AbstractLocalizationProvider() {
		listeners = new ArrayList<>();
	}

	@Override
	public void addLocalizationListener(ILocalizationListener l) {
		listeners.add(l);
	}

	@Override
	public void removeLocalizationListener(ILocalizationListener l) {
		listeners.add(l);
	}

	/**
	 * Method that notifies all registered listeners about language change
	 */
	public void fire() {
		for (ILocalizationListener listener : listeners) {
			listener.localizationChanged();
		}
	}
}

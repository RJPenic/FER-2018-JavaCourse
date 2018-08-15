package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

/**
 * Implementation of Localization bridge which creates a windows listener on a
 * frame which connects when the window is opened and disconnects when the
 * window is closed.
 * 
 * @author Rafael Josip Penić
 *
 */
public class FormLocalizationProvider extends LocalizationProviderBridge {

	/**
	 * Constructor which registers a windows listener to a frame
	 * 
	 * @param parent
	 *            parent provider
	 * @param frame
	 *            frame on which the listener will be registered
	 */
	public FormLocalizationProvider(ILocalizationProvider parent, JFrame frame) {
		super(parent);

		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				connect();
			}

			@Override
			public void windowClosed(WindowEvent e) {
				disconnect();
			}
		});
	}

}

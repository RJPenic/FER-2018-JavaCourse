package hr.fer.zemris.java.fractals;

import java.util.concurrent.ThreadFactory;

/**
 * Thread factory that produces daemon threads
 * 
 * @author Rafael Josip Penić
 *
 */
public class DaemonThreadFactory implements ThreadFactory {

	@Override
	public Thread newThread(Runnable r) {
		Thread worker = new Thread(r);
		worker.setDaemon(true);
		return worker;
	}

}

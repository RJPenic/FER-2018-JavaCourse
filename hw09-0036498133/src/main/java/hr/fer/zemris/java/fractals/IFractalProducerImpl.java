package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * Implementation of the IFractalProducer interface
 * 
 * @author Rafael Josip Penić
 *
 */
public class IFractalProducerImpl implements IFractalProducer {

	/**
	 * Pool of threads used for thread synchronization.
	 */
	private static ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(),
			new DaemonThreadFactory());

	private static final int NUMBER_OF_ITERATIONS = 16 * 16 * 16;
	private static final int NUMBER_OF_LANES = 8;

	/**
	 * Polynomial that determines which fractal will be drawn
	 */
	private ComplexRootedPolynomial poly;

	/**
	 * Constructor for IFractalProducer objects
	 * 
	 * @param poly
	 *            polynomial that will be used for drawing a fractal
	 */
	public IFractalProducerImpl(ComplexRootedPolynomial poly) {
		this.poly = poly;
	}

	@Override
	public void produce(double reMin, double reMax, double imMin, double imMax, int width, int height, long requestNo,
			IFractalResultObserver observer) {
		int m = NUMBER_OF_ITERATIONS;
		short[] data = new short[width * height];
		final int lanes = NUMBER_OF_LANES * Runtime.getRuntime().availableProcessors();
		int numberOfYPerLane = height / lanes;

		List<Future<Void>> results = new ArrayList<>();

		for (int i = 0; i < lanes; i++) {
			int yMin = i * numberOfYPerLane;
			int yMax = (i + 1) * numberOfYPerLane - 1;
			
			if (i == lanes - 1) {
				yMax = height - 1;
			}
			
			CalculatingProcess work = new CalculatingProcess(reMin, reMax, imMin, imMax, width, height, yMin, yMax, m,
					data, poly);
			results.add(pool.submit(work));
		}

		for (Future<Void> tempWork : results) {
			try {
				tempWork.get();
			} catch (InterruptedException | ExecutionException ex) {
				throw new RuntimeException("Error while waiting for the result.");
			}
		}

		observer.acceptResult(data, (short) (poly.toComplexPolynom().order() + 1), requestNo);
	}

}

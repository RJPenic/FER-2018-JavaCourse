package hr.fer.zemris.java.raytracer;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

import hr.fer.zemris.java.raytracer.model.GraphicalObject;
import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.LightSource;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 * Class representing a simple ray caster which produces rays and colors the
 * models. ForkJoinPool and Recursive action are used to make drawing process
 * faster
 * 
 * @author Rafael Josip Penić
 *
 */
public class RayCasterParallel {

	/**
	 * Main method
	 * 
	 * @param args
	 *            command line arguments
	 */
	public static void main(String[] args) {
		RayTracerViewer.show(getIRayTracerProducer(), new Point3D(10, 0, 0), new Point3D(0, 0, 0),
				new Point3D(0, 0, 10), 20, 20);
	}

	/**
	 * Implementation of RecursiveAction used along with ForkJoin framework to make
	 * drawing a bit faster.
	 * 
	 * @author Rafael Josip Penić
	 *
	 */
	private static class Work extends RecursiveAction {

		private static final long serialVersionUID = 1L;

		private static final int treshold = 16;

		/**
		 * position of human observer
		 */
		private Point3D eye;

		/**
		 * Vector representing yAxis
		 */
		private Point3D yAxis;

		/**
		 * Vector representing xAxis
		 */
		private Point3D xAxis;

		/**
		 * height of the screen
		 */
		private int height;

		/**
		 * width of the screen
		 */
		private int width;

		/**
		 * array used when coloring pixels
		 */
		private short[] rgb;

		/**
		 * array representing intensity of red color
		 */
		private short[] red;

		/**
		 * array representing intensity of green color
		 */
		private short[] green;

		/**
		 * array representing intensity of blue color
		 */
		private short[] blue;

		/**
		 * Minimal y
		 */
		private int yMin;

		/**
		 * Maximal y
		 */
		private int yMax;

		/**
		 * Point that is in the corner of the screen
		 */
		private Point3D screenCorner;

		/**
		 * Vertical height of observed space
		 */
		private double vertical;

		/**
		 * Horizontal width of observed space
		 */
		private double horizontal;

		/**
		 * group of graphical objects
		 */
		private Scene scene;

		/**
		 * Constructor for Work objects
		 * 
		 * @param eye
		 *            position of human observer
		 * @param xAxis
		 *            vector representing xAxis
		 * @param yAxis
		 *            vector representing yAxis
		 * @param height
		 *            height of the screen
		 * @param width
		 *            width of the screen
		 * @param yMin
		 *            minimal y
		 * @param yMax
		 *            maximal y
		 * @param rgb
		 *            array used for coloring pixels
		 * @param red
		 *            array representing red intensity
		 * @param green
		 *            array representing green intensity
		 * @param blue
		 *            array representing blue intensity
		 * @param screenCorner
		 *            corner of the screen
		 * @param vertical
		 *            vertical height of observed space
		 * @param horizontal
		 *            horizontal width of observed space
		 * @param scene
		 *            scene that is observed
		 */
		public Work(Point3D eye, Point3D xAxis, Point3D yAxis, int height, int width, int yMin, int yMax, short[] rgb,
				short[] red, short[] green, short[] blue, Point3D screenCorner, double vertical, double horizontal,
				Scene scene) {
			this.eye = eye;
			this.xAxis = xAxis;
			this.yAxis = yAxis;
			this.height = height;
			this.width = width;
			this.rgb = rgb;
			this.red = red;
			this.green = green;
			this.blue = blue;
			this.yMin = yMin;
			this.yMax = yMax;
			this.screenCorner = screenCorner;
			this.vertical = vertical;
			this.horizontal = horizontal;
			this.scene = scene;
		}

		@Override
		protected void compute() {
			if (yMax - yMin + 1 <= treshold) {
				for (int y = yMin; y <= yMax; y++) {
					for (int x = 0; x < width; x++) {
						Point3D screenPoint = screenCorner.add(xAxis.scalarMultiply(horizontal * x / (width - 1)))
								.sub(yAxis.scalarMultiply(vertical * y / (height - 1)));

						Ray ray = Ray.fromPoints(eye, screenPoint);

						short[] tempArray = rgb.clone();

						tracer(scene, ray, tempArray);

						red[y * width + x] = tempArray[0] > 255 ? 255 : tempArray[0];
						green[y * width + x] = tempArray[1] > 255 ? 255 : tempArray[1];
						blue[y * width + x] = tempArray[2] > 255 ? 255 : tempArray[2];
					}
				}

				return;
			}

			invokeAll(
					new Work(eye, xAxis, yAxis, height, width, yMin, yMin + (yMax - yMin) / 2, rgb, red, green, blue,
							screenCorner, vertical, horizontal, scene),
					new Work(eye, xAxis, yAxis, height, width, yMin + (yMax - yMin) / 2 + 1, yMax, rgb, red, green,
							blue, screenCorner, vertical, horizontal, scene));
		}

	}

	/**
	 * Method that constructs new IRayTracerProducer which will then be used for
	 * producing rays
	 * 
	 * @return constructed IRayTracerProducer
	 */
	private static IRayTracerProducer getIRayTracerProducer() {
		return new IRayTracerProducer() {
			@Override
			public void produce(Point3D eye, Point3D view, Point3D viewUp, double horizontal, double vertical,
					int width, int height, long requestNo, IRayTracerResultObserver observer) {

				System.out.println("Započinjem izračune...");

				short[] red = new short[width * height];
				short[] green = new short[width * height];
				short[] blue = new short[width * height];

				Point3D yAxis = viewUp.normalize()
						.sub(view.sub(eye).scalarMultiply(view.sub(eye).scalarProduct(viewUp.normalize()))).normalize();
				Point3D xAxis = view.sub(eye).vectorProduct(yAxis).normalize();

				Point3D screenCorner = view.sub(xAxis.scalarMultiply(horizontal / 2))
						.add(yAxis.scalarMultiply(vertical / 2));

				Scene scene = RayTracerViewer.createPredefinedScene();

				short[] rgb = new short[3];

				ForkJoinPool pool = new ForkJoinPool();
				pool.invoke(new Work(eye, xAxis, yAxis, height, width, 0, height - 1, rgb, red, green, blue,
						screenCorner, vertical, horizontal, scene));
				pool.shutdown();

				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");
			}
		};
	}

	/**
	 * Traces given ray according to the given scene
	 * 
	 * @param scene
	 *            scene which on which the ray is projected
	 * @param ray
	 *            ray that is traced
	 * @param rgb
	 *            array used for coloring the pixels
	 */
	protected static void tracer(Scene scene, Ray ray, short[] rgb) {
		rgb[0] = 0;
		rgb[1] = 0;
		rgb[2] = 0;

		RayIntersection closest = findClosestIntersection(scene, ray);

		if (closest == null) {
			return;
		}

		determineColorFor(scene, closest, rgb, ray);
	}

	/**
	 * Determines color for the given intersection
	 * 
	 * @param scene
	 *            scene in which the intersection is
	 * @param closest
	 *            intersection to be colored
	 * @param rgb
	 *            color array which will determine in which color the pixel will be
	 *            colored
	 * @param ray
	 *            ray that determines intersection along with the scene
	 */
	private static void determineColorFor(Scene scene, RayIntersection closest, short[] rgb, Ray ray) {
		rgb[0] = 15;
		rgb[1] = 15;
		rgb[2] = 15;

		double treshold = 0.001;

		for (LightSource light : scene.getLights()) {
			Ray tempRay = Ray.fromPoints(light.getPoint(), closest.getPoint());
			RayIntersection intersect = findClosestIntersection(scene, tempRay);

			if (intersect != null) {
				double closestDist = closest.getPoint().sub(light.getPoint()).norm();
				double intersectDist = intersect.getPoint().sub(light.getPoint()).norm();

				if (intersectDist + treshold > closestDist) {

					Point3D l = light.getPoint().sub(closest.getPoint()).normalize();

					double cosValueDiff = closest.getNormal().scalarProduct(l);

					if (cosValueDiff < 0) {
						cosValueDiff = 0;
					}

					Point3D reflVector = closest.getNormal().scalarMultiply(l.scalarProduct(closest.getNormal()))
							.scalarMultiply(2).sub(l);
					Point3D viewVector = ray.start.sub(closest.getPoint());

					double cosValueRefl = reflVector.scalarProduct(viewVector)
							/ (reflVector.norm() * viewVector.norm());

					if (cosValueRefl < 0) {
						cosValueRefl = 0;
					}

					rgb[0] += closest.getKdr() * light.getR() * cosValueDiff
							+ closest.getKrr() * light.getR() * Math.pow(cosValueRefl, closest.getKrn());
					rgb[1] += closest.getKdg() * light.getG() * cosValueDiff
							+ closest.getKrg() * light.getG() * Math.pow(cosValueRefl, closest.getKrn());
					rgb[2] += closest.getKdb() * light.getB() * cosValueDiff
							+ closest.getKrb() * light.getB() * Math.pow(cosValueRefl, closest.getKrn());
				}
			}
		}
	}

	/**
	 * Finds closest intersection of given ray and the given scene
	 * 
	 * @param scene
	 *            scene containing various graphical objects
	 * @param ray
	 *            ray which intersections will be taken in consideration
	 * @return closest intersection
	 */
	private static RayIntersection findClosestIntersection(Scene scene, Ray ray) {
		double min = 0;
		RayIntersection result = null;
		boolean first = true;

		for (GraphicalObject object : scene.getObjects()) {
			if (first) {
				if (object.findClosestRayIntersection(ray) != null) {
					result = object.findClosestRayIntersection(ray);
					min = result.getDistance();
					first = false;
				}
			} else {
				if (object.findClosestRayIntersection(ray) != null
						&& min > object.findClosestRayIntersection(ray).getDistance()) {
					result = object.findClosestRayIntersection(ray);
					min = object.findClosestRayIntersection(ray).getDistance();
				}
			}
		}

		return result;
	}
}

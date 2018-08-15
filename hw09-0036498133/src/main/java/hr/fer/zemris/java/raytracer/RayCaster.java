package hr.fer.zemris.java.raytracer;

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
 * models.
 * 
 * @author Rafael Josip Penić
 *
 */
public class RayCaster {

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
				int offset = 0;
				for (int y = 0; y < height; y++) {
					for (int x = 0; x < width; x++) {

						Point3D screenPoint = screenCorner.add(xAxis.scalarMultiply(horizontal * x / (width - 1)))
								.sub(yAxis.scalarMultiply(vertical * y / (height - 1)));

						Ray ray = Ray.fromPoints(eye, screenPoint);

						tracer(scene, ray, rgb);

						red[offset] = rgb[0] > 255 ? 255 : rgb[0];
						green[offset] = rgb[1] > 255 ? 255 : rgb[1];
						blue[offset] = rgb[2] > 255 ? 255 : rgb[2];

						offset++;
					}
				}

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

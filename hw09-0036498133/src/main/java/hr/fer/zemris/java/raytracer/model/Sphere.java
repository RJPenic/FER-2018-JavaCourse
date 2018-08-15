package hr.fer.zemris.java.raytracer.model;

/**
 * Class representing graphical object sphere
 * 
 * @author Rafael Josip Penić
 *
 */
public class Sphere extends GraphicalObject {

	/**
	 * Center of the sphere
	 */
	private Point3D center;

	/**
	 * Radius of the sphere
	 */
	private double radius;

	/**
	 * coefficient for diffuse component for color red
	 */
	private double kdr;

	/**
	 * coefficient for diffuse component for color green
	 */
	private double kdg;

	/**
	 * coefficient for diffuse component for color blue
	 */
	private double kdb;

	/**
	 * coefficient for reflective component for color red
	 */
	private double krr;

	/**
	 * coefficient for reflective component for color green
	 */
	private double krg;

	/**
	 * coefficient for reflective component for color blue
	 */
	private double krb;

	/**
	 * coefficient n for reflective component
	 */
	private double krn;

	/**
	 * Constructor for sphere objects
	 * 
	 * @param center
	 *            center of the sphere
	 * @param radius
	 *            radius of the sphere
	 * @param kdr
	 *            red diffuse component
	 * @param kdg
	 *            green diffuse component
	 * @param kdb
	 *            blue diffuse component
	 * @param krr
	 *            red reflective component
	 * @param krg
	 *            green reflective component
	 * @param krb
	 *            blue reflective component
	 * @param krn
	 *            coefficient n for reflective component
	 */
	public Sphere(Point3D center, double radius, double kdr, double kdg, double kdb, double krr, double krg, double krb,
			double krn) {
		super();
		this.center = center;
		this.radius = radius;
		this.kdr = kdr;
		this.kdg = kdg;
		this.kdb = kdb;
		this.krr = krr;
		this.krg = krg;
		this.krb = krb;
		this.krn = krn;
	}

	@Override
	public RayIntersection findClosestRayIntersection(Ray arg0) {
		double b = 2 * arg0.direction.scalarProduct(arg0.start.sub(center));
		double c = arg0.start.sub(center).norm() * arg0.start.sub(center).norm() - radius * radius;

		boolean outer = true;

		double det = b * b / 4 - c;

		if (det < 0) {
			return null;
		}

		if (arg0.start.sub(center).norm() < radius) {
			outer = false;
		}

		double distance1 = -b / 2 + Math.sqrt(det);
		double distance2 = -b / 2 - Math.sqrt(det);

		if (distance2 < 0 && distance1 >= 0)
			return constructRayIntersection(arg0.start.add(arg0.direction.normalize().scalarMultiply(distance1)),
					distance1, outer);

		else if (distance2 >= 0)
			return constructRayIntersection(arg0.start.add(arg0.direction.normalize().scalarMultiply(distance2)),
					distance2, outer);

		return null;
	}

	/**
	 * Method that constructs RayIntersection object from given arguments
	 * 
	 * @param intersection
	 *            point of intersection
	 * @param distance
	 *            distance from eye to intersection
	 * @param outer
	 *            boolean value that is true if the eye is in sphere and false
	 *            otherwise
	 * @return constructed RayIntersection
	 */
	private RayIntersection constructRayIntersection(Point3D intersection, double distance, boolean outer) {

		return new RayIntersection(intersection, distance, outer) {

			@Override
			public Point3D getNormal() {
				return intersection.sub(center).normalize();
			}

			@Override
			public double getKrr() {
				return krr;
			}

			@Override
			public double getKrn() {
				return krn;
			}

			@Override
			public double getKrg() {
				return krg;
			}

			@Override
			public double getKrb() {
				return krb;
			}

			@Override
			public double getKdr() {
				return kdr;
			}

			@Override
			public double getKdg() {
				return kdg;
			}

			@Override
			public double getKdb() {
				return kdb;
			}
		};
	}

}

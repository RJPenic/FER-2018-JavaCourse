package hr.fer.zemris.math;

import org.junit.Test;
import org.junit.Assert;

public class Vector3Test {

	private final static double THRESHOLD = 1E-6;
	
	@Test
	public void testNorm() {
		Vector3 v = new Vector3(3, -1 , 2);
		
		Assert.assertTrue(Math.abs(v.norm() - 3.7416574) < THRESHOLD);
	}
	
	@Test
	public void testNormWhenNullVector() {
		Vector3 v = new Vector3(0, 0, 0);
		
		Assert.assertTrue(Math.abs(v.norm() - 0) < THRESHOLD);
	}
	
	@Test
	public void testAdd() {
		Vector3 v1 = new Vector3(3, -1 , 2);
		Vector3 v2 = new Vector3(0, 15, -11);
		
		Vector3 v = v1.add(v2);
		Vector3 vAlt = v2.add(v1);
		
		Assert.assertTrue(Math.abs(v.getX() - vAlt.getX()) < THRESHOLD);
		Assert.assertTrue(Math.abs(v.getY() - vAlt.getY()) < THRESHOLD);
		Assert.assertTrue(Math.abs(v.getZ() - vAlt.getZ()) < THRESHOLD);
		
		Assert.assertTrue(Math.abs(v.getX() - 3) < THRESHOLD);
		Assert.assertTrue(Math.abs(v.getY() - 14) < THRESHOLD);
		Assert.assertTrue(Math.abs(v.getZ() + 9) < THRESHOLD);
	}
	
	@Test
	public void testAddWhenOneArgZero() {
		Vector3 v1 = new Vector3(3, 52 , -12);
		Vector3 v2 = new Vector3(0, 0, 0);
		
		Vector3 v = v1.add(v2);
		
		Assert.assertTrue(Math.abs(v.getX() - 3) < THRESHOLD);
		Assert.assertTrue(Math.abs(v.getY() - 52) < THRESHOLD);
		Assert.assertTrue(Math.abs(v.getZ() + 12) < THRESHOLD);
	}
	
	@Test
	public void testSub() {
		Vector3 v1 = new Vector3(3, -1 , 2);
		Vector3 v2 = new Vector3(0, 15, -11);
		
		Vector3 v = v1.sub(v2);
		
		Assert.assertTrue(Math.abs(v.getX() - 3) < THRESHOLD);
		Assert.assertTrue(Math.abs(v.getY() + 16) < THRESHOLD);
		Assert.assertTrue(Math.abs(v.getZ() - 13) < THRESHOLD);
	}
	
	@Test
	public void testSubWhenSubbingZero() {
		Vector3 v1 = new Vector3(3, -1 , 2);
		Vector3 v2 = new Vector3(0, 0, 0);
		
		Vector3 v = v1.sub(v2);
		
		Assert.assertTrue(Math.abs(v.getX() - 3) < THRESHOLD);
		Assert.assertTrue(Math.abs(v.getY() + 1) < THRESHOLD);
		Assert.assertTrue(Math.abs(v.getZ() - 2) < THRESHOLD);
	}
	
	@Test
	public void testDot() {
		Vector3 v1 = new Vector3(3, -1 , 2);
		Vector3 v2 = new Vector3(4, 5, -2);
		
		double val = v1.dot(v2);
		double valAlt = v2.dot(v1);
		
		Assert.assertTrue(Math.abs(val - valAlt) < THRESHOLD);
		
		Assert.assertTrue(Math.abs(val - 3) < THRESHOLD);
	}
	
	@Test
	public void testDotWhenOneArgZero() {
		Vector3 v1 = new Vector3(0, 0, 0);
		Vector3 v2 = new Vector3(4, 5, -2);
		
		double val = v1.dot(v2);
		
		
		Assert.assertTrue(Math.abs(val - 0) < THRESHOLD);
	}
	
	@Test
	public void testCross() {
		Vector3 v1 = new Vector3(4, -2, -2);
		Vector3 v2 = new Vector3(3, 0, -5);
		
		Vector3 v = v1.cross(v2);
		
		Assert.assertTrue(Math.abs(v.getX() - 10) < THRESHOLD);
		Assert.assertTrue(Math.abs(v.getY() - 14) < THRESHOLD);
		Assert.assertTrue(Math.abs(v.getZ() - 6) < THRESHOLD);
	}
	
	@Test
	public void testCrossWhenOneArgZero() {
		Vector3 v1 = new Vector3(4, -2, -2);
		Vector3 v2 = new Vector3(0, 0, 0);
		
		Vector3 v = v1.cross(v2);
		
		Assert.assertTrue(Math.abs(v.getX() - 0) < THRESHOLD);
		Assert.assertTrue(Math.abs(v.getY() - 0) < THRESHOLD);
		Assert.assertTrue(Math.abs(v.getZ() - 0) < THRESHOLD);
	}
	
	@Test
	public void testScaleWhenScaleFactorPositive() {
		Vector3 v1 = new Vector3(4, -2, -2);
		
		Vector3 v = v1.scale(5);
		
		Assert.assertTrue(Math.abs(v.getX() - 20) < THRESHOLD);
		Assert.assertTrue(Math.abs(v.getY() + 10) < THRESHOLD);
		Assert.assertTrue(Math.abs(v.getZ() + 10) < THRESHOLD);
	}
	
	@Test
	public void testScaleWhenScaleFactorZero() {
		Vector3 v1 = new Vector3(4, -2, -2);
		
		Vector3 v = v1.scale(0);
		
		Assert.assertTrue(Math.abs(v.getX() - 0) < THRESHOLD);
		Assert.assertTrue(Math.abs(v.getY() + 0) < THRESHOLD);
		Assert.assertTrue(Math.abs(v.getZ() + 0) < THRESHOLD);
	}
	
	@Test
	public void testScaleWhenScaleFactorNegative() {
		Vector3 v1 = new Vector3(4, -2, -2);
		
		Vector3 v = v1.scale(-5);
		
		Assert.assertTrue(Math.abs(v.getX() + 20) < THRESHOLD);
		Assert.assertTrue(Math.abs(v.getY() - 10) < THRESHOLD);
		Assert.assertTrue(Math.abs(v.getZ() - 10) < THRESHOLD);
	}
	
	@Test
	public void testCosAngle1() {
		Vector3 v1 = new Vector3(21, -3, 4);
		Vector3 v2 = new Vector3(0, 5, 2);
		
		double val = v1.cosAngle(v2);
		
		Assert.assertTrue(Math.abs(val + 0.060215193) < THRESHOLD);
	}
	
	@Test
	public void testCosAngle2() {
		Vector3 v1 = new Vector3(-1, 0, -3);
		Vector3 v2 = new Vector3(0, -5, -2);
		
		double val = v1.cosAngle(v2);
		
		Assert.assertTrue(Math.abs(val - 0.35233213170) < THRESHOLD);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCosAngleWhenOneArgumentNullVector() {
		Vector3 v1 = new Vector3(0, 0, 0);
		Vector3 v2 = new Vector3(115, -5, -2);
		
		v1.cosAngle(v2);//throws
	}
}

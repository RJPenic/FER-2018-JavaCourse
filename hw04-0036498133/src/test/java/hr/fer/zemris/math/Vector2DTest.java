package hr.fer.zemris.math;

import org.junit.Assert;
import org.junit.Test;

public class Vector2DTest {
	
	@Test
	public void testTranslated() {
		Vector2D vec1 = new Vector2D(1,1);
		Vector2D vec2 = new Vector2D(1,0);
		
		Assert.assertEquals(new Vector2D(2,1), vec1.translated(vec2));
	}
	
	@Test
	public void testTranslate() {
		Vector2D vec1 = new Vector2D(1,1);
		Vector2D vec2 = new Vector2D(1,0).translated(vec1);
		
		Assert.assertEquals(new Vector2D(2,1), vec2);
	}
	
	@Test
	public void testRotated() {
		Vector2D vec = new Vector2D(1,1);
		
		Assert.assertEquals(new Vector2D(-1, -1), vec.rotated(180));
	}
	
	@Test
	public void testRotate() {
		Vector2D vec = new Vector2D(1,1);
		vec.rotate(180);
		
		Assert.assertEquals(new Vector2D(-1, -1), vec);
	}
	
	@Test
	public void testRotateWhenAngleZero() {
		Vector2D vec = new Vector2D(1,1);
		vec.rotate(0);
		
		Assert.assertEquals(new Vector2D(1, 1), vec);
	}
	
	@Test
	public void testScaled() {
		Vector2D vec = new Vector2D(1,1);
		
		Assert.assertEquals(new Vector2D(3,3), vec.scaled(3));
	}
	
	@Test
	public void testScale() {
		Vector2D vec = new Vector2D(1,1);
		vec.scale(3);
		
		Assert.assertEquals(new Vector2D(3,3), vec);
	}
	
	@Test
	public void testCopy() {
		Vector2D vec = new Vector2D(2.2, 3.13);
		
		Assert.assertEquals(vec, vec.copy());
	}
	
	@Test(expected = NullPointerException.class)
	public void testTranslateWhenOffsetVectorNull() {
		Vector2D vec = new Vector2D(1, 1);
		Vector2D vec1 = null;
		
		vec.translate(vec1);
	}
	
	@Test(expected = NullPointerException.class)
	public void testTranslatedWhenOffsetVectorNull() {
		Vector2D vec = new Vector2D(1, 1);
		Vector2D vec1 = null;
		
		vec.translated(vec1);
	}
}

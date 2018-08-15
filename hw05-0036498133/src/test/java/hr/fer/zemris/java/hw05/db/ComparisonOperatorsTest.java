package hr.fer.zemris.java.hw05.db;

import org.junit.Assert;
import org.junit.Test;

import hr.fer.zemris.java.hw05.db.ComparisonOperators.ComparisonOperators;
import hr.fer.zemris.java.hw05.db.IComparisonOperator.IComparisonOperator;

public class ComparisonOperatorsTest {

	@Test
	public void testLessWhenTrue() {
		IComparisonOperator opr = ComparisonOperators.LESS;
		
		Assert.assertTrue(opr.satisfied("Adr", "Svsd"));
	}
	
	@Test
	public void testLessWhenFalse() {
		IComparisonOperator opr = ComparisonOperators.LESS;
		
		Assert.assertFalse(opr.satisfied("Svsd", "Adr"));
	}
	
	@Test
	public void testLessEqualsWhenTrue() {
		IComparisonOperator opr = ComparisonOperators.LESS_OR_EQUALS;
		
		Assert.assertTrue(opr.satisfied("Adr", "Svsd"));
		Assert.assertTrue(opr.satisfied("Avs", "Avs"));
	}
	
	@Test
	public void testLessEqualsWhenFalse() {
		IComparisonOperator opr = ComparisonOperators.LESS_OR_EQUALS;
		
		Assert.assertFalse(opr.satisfied("Svsd", "Adr"));
	}
	
	@Test
	public void testGreaterWhenTrue() {
		IComparisonOperator opr = ComparisonOperators.GREATER;
		
		Assert.assertTrue(opr.satisfied("Svsd", "Avs"));
	}
	
	@Test
	public void testGreaterWhenFalse() {
		IComparisonOperator opr = ComparisonOperators.GREATER;
		
		Assert.assertFalse(opr.satisfied("Agsdb", "Sttt"));
	}
	
	@Test
	public void testGreaterEqualsWhenTrue() {
		IComparisonOperator opr = ComparisonOperators.GREATER_OR_EQUALS;
		
		Assert.assertTrue(opr.satisfied("Svd", "Agda"));
		Assert.assertTrue(opr.satisfied("Avs", "Avs"));
	}
	
	@Test
	public void testGreaterEqualsWhenFalse() {
		IComparisonOperator opr = ComparisonOperators.GREATER_OR_EQUALS;
		
		Assert.assertFalse(opr.satisfied("Afrsfav", "Sgsb"));
	}
	
	@Test
	public void testEqualsWhenTrue() {
		IComparisonOperator opr = ComparisonOperators.EQUALS;
		
		Assert.assertTrue(opr.satisfied("fvnd", "fvnd"));
	}
	
	@Test
	public void testEqualsWhenFalse() {
		IComparisonOperator opr = ComparisonOperators.EQUALS;
		
		Assert.assertFalse(opr.satisfied("Afrsfav", "Sgsb"));
	}
	
	@Test
	public void testNotEqualsWhenTrue() {
		IComparisonOperator opr = ComparisonOperators.NOT_EQUALS;
		
		Assert.assertTrue(opr.satisfied("asva", "fvnd"));
	}
	
	@Test
	public void testNotEqualsWhenFalse() {
		IComparisonOperator opr = ComparisonOperators.NOT_EQUALS;
		
		Assert.assertFalse(opr.satisfied("Afrsfav", "Afrsfav"));
	}
	
	@Test
	public void testLikeWhenPatternEmpty() {
		IComparisonOperator opr = ComparisonOperators.LIKE;
		
		Assert.assertFalse(opr.satisfied("Def  s", ""));
		Assert.assertTrue(opr.satisfied("", ""));
	}
	
	@Test
	public void testLikeWhenPatternIsWildCard() {
		IComparisonOperator opr = ComparisonOperators.LIKE;
		
		Assert.assertTrue(opr.satisfied("avadvamaaA ** dvv", "*"));
		Assert.assertTrue(opr.satisfied("", "*"));
	}
	
	@Test
	public void testLikeWhenWildCardAtBeginning() {
		IComparisonOperator opr = ComparisonOperators.LIKE;
		
		Assert.assertTrue(opr.satisfied("Tihomir", "*mir"));
		Assert.assertTrue(opr.satisfied("sfs", "*sfs"));
		Assert.assertFalse(opr.satisfied("vab  ", "*b"));
	}
	
	@Test
	public void testLikeWhenWildCardAtEnd() {
		IComparisonOperator opr = ComparisonOperators.LIKE;
		
		Assert.assertTrue(opr.satisfied("Tihomir", "Tih*"));
		Assert.assertTrue(opr.satisfied("sfs", "sfs*"));
		Assert.assertFalse(opr.satisfied("vab  ", " v*"));
	}
	
	@Test
	public void testLikeWhenWildCardInMiddle() {
		IComparisonOperator opr = ComparisonOperators.LIKE;
		
		Assert.assertTrue(opr.satisfied("Tihomir", "Tih*r"));
		Assert.assertTrue(opr.satisfied("sfs", "sf*s"));
		Assert.assertFalse(opr.satisfied("vab  ", " v*ab  "));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testLikeWhenTwoWildCards() {
		IComparisonOperator opr = ComparisonOperators.LIKE;
		
		Assert.assertTrue(opr.satisfied("Tihomir", "Tih*r*"));
	}
}

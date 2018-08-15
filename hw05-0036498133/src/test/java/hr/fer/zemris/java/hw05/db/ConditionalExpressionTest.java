package hr.fer.zemris.java.hw05.db;

import org.junit.Test;

import hr.fer.zemris.java.hw05.db.ComparisonOperators.ComparisonOperators;
import hr.fer.zemris.java.hw05.db.ConditionalExpression.ConditionalExpression;
import hr.fer.zemris.java.hw05.db.FieldValueGetters.FieldValueGetters;

import org.junit.Assert;

public class ConditionalExpressionTest {
	
	@Test
	public void testGetFieldGetter() {
		ConditionalExpression ex = new ConditionalExpression(FieldValueGetters.FIRST_NAME,
															"test",
															ComparisonOperators.EQUALS);
		
		Assert.assertEquals(FieldValueGetters.FIRST_NAME, ex.getFieldGetter());
	}
	
	@Test
	public void testGetStringLiteral() {
		ConditionalExpression ex = new ConditionalExpression(FieldValueGetters.LAST_NAME,
															"test23",
															ComparisonOperators.NOT_EQUALS);
		
		Assert.assertEquals("test23", ex.getStringLiteral());
	}
	
	@Test
	public void testGetComparisonOperator() {
		ConditionalExpression ex = new ConditionalExpression(FieldValueGetters.JMBAG,
															"te  -st 23",
															ComparisonOperators.NOT_EQUALS);

		Assert.assertEquals(ComparisonOperators.NOT_EQUALS, ex.getComparisonOperator());
	}
}

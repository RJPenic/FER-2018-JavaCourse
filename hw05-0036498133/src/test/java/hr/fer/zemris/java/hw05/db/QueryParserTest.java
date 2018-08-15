package hr.fer.zemris.java.hw05.db;

import org.junit.Test;

import hr.fer.zemris.java.hw05.db.ComparisonOperators.ComparisonOperators;
import hr.fer.zemris.java.hw05.db.ConditionalExpression.ConditionalExpression;
import hr.fer.zemris.java.hw05.db.FieldValueGetters.FieldValueGetters;
import hr.fer.zemris.java.hw05.db.QueryParser.QueryParser;

import java.util.List;

import org.junit.Assert;

public class QueryParserTest {
	
	@Test
	public void testIsDirectWhenTrue() {
		QueryParser qp = new QueryParser(" jmbag =\"0123456789\" ");
		Assert.assertTrue(qp.isDirectQuery());
	}
	
	@Test
	public void testIsDirectWhenFalse() {
		QueryParser qp = new QueryParser("jmbag=\"0123456789\" and lastName>\"J\"");
		Assert.assertFalse(qp.isDirectQuery());
	}
	
	@Test
	public void testGetQueriedJMBAGWhenDirect() {
		QueryParser qp = new QueryParser(" jmbag =\"0123456789\" ");
		Assert.assertEquals("0123456789", qp.getQueriedJMBAG());
	}
	
	@Test(expected = IllegalStateException.class)
	public void testGetQueriedJMBAGWhenNotDirect() {
		QueryParser qp = new QueryParser("jmbag=\"0123456789\" and lastName>\"J\"");
		qp.getQueriedJMBAG(); //throws
	}
	
	@Test
	public void testGetQuery() {
		QueryParser qp = new QueryParser("jmbag=\"0123456789\" anD lastName>\"J\" and firstName!=\"Marija\"");
		List<ConditionalExpression> tempList = qp.getQuery();
		
		Assert.assertEquals(3, tempList.size());
		
		ConditionalExpression exp = tempList.get(0);
		Assert.assertEquals(FieldValueGetters.JMBAG, exp.getFieldGetter());
		Assert.assertEquals(ComparisonOperators.EQUALS, exp.getComparisonOperator());
		Assert.assertEquals("0123456789", exp.getStringLiteral());
		
		exp = tempList.get(1);
		Assert.assertEquals(FieldValueGetters.LAST_NAME, exp.getFieldGetter());
		Assert.assertEquals(ComparisonOperators.GREATER, exp.getComparisonOperator());
		Assert.assertEquals("J", exp.getStringLiteral());
		
		exp = tempList.get(2);
		Assert.assertEquals(FieldValueGetters.FIRST_NAME, exp.getFieldGetter());
		Assert.assertEquals(ComparisonOperators.NOT_EQUALS, exp.getComparisonOperator());
		Assert.assertEquals("Marija", exp.getStringLiteral());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testGetQueryWhenEndsAfterAnd() {
		QueryParser qp = new QueryParser("jmbag=\"0123456789\" and lastName>\"J\" and ");
		qp.getQuery();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testGetQueryWhenUndefinedOperator() {
		QueryParser qp = new QueryParser("jmbag=\"0123456789\" and lastName?\"J\"");
		qp.getQuery();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testGetQueryWhenUndefinedDirective() {
		QueryParser qp = new QueryParser("JMBag=\"0123456789\" and lastName>\"J\"");
		qp.getQuery();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testGetQueryWhenAndWrong() {
		QueryParser qp = new QueryParser("    jmbag=\"0123456789\" ande lastName>\"J\"");
		qp.getQuery();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testGetQueryWhenNoQuotationClose() {
		QueryParser qp = new QueryParser(" jmbag=\"0123456789\" and lastName>\"J");
		qp.getQuery();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testGetQueryWhenNoQuotationOpen() {
		QueryParser qp = new QueryParser(" jmbag=\"0123456789\" and lastName>J\"");
		qp.getQuery();
	}
	
	@Test
	public void testGetQueryWhenEmptyString() {
		QueryParser qp = new QueryParser("");
		List<ConditionalExpression> tempList = qp.getQuery();
		Assert.assertEquals(0, tempList.size());
	}
}

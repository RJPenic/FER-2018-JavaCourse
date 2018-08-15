package hr.fer.zemris.java.hw05.db.QueryParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import hr.fer.zemris.java.hw05.db.ComparisonOperators.ComparisonOperators;
import hr.fer.zemris.java.hw05.db.ConditionalExpression.ConditionalExpression;
import hr.fer.zemris.java.hw05.db.FieldValueGetters.FieldValueGetters;
import hr.fer.zemris.java.hw05.db.IComparisonOperator.IComparisonOperator;
import hr.fer.zemris.java.hw05.db.IFieldValueGetter.IFieldValueGetter;

/**
 * Class that represents query parser
 * 
 * @author Rafael Josip Penić
 *
 */
public class QueryParser {
	
	/**
	 * String that will be parsed
	 */
	private String queryString;
	
	/**
	 * Constructor for QueryParser objects
	 * 
	 * @param queryString
	 *            text of the parser that will be constructed
	 */
	public QueryParser(String queryString) {
		Objects.requireNonNull(queryString, "String given to parse is not allowed to be null.");
		this.queryString = queryString;
	}
	
	/**
	 * Method that says if the query is direct or not
	 * 
	 * @return true if the query is direct, false otherwise
	 * 
	 */
	public boolean isDirectQuery() {
		List<ConditionalExpression> tempList = getQuery();
		
		if(tempList.size() != 1)	// if the size of the query list is not 1 that means there is either none or more than 1
			return false;			// conditions that have to be fulfilled which means that the query is not direct
		
		ConditionalExpression exp = tempList.get(0);	// size has been tested in previous if so now here we don't have to worry
														// about IndexOutOfBoundsException
		
		if(exp.getFieldGetter() != FieldValueGetters.JMBAG) // notice that here we can use comparators like "==" and "!=".
			return false;									// That is because FieldValueGetters.JMBAG is allocated in only one place in the memory(it is static)
		
		if(exp.getComparisonOperator() != ComparisonOperators.EQUALS)
			return false;
		
		return true;
	}
	
	/**
	 * Method that gets jmbag of the direct query
	 * 
	 * @return string that represents jmbag
	 * @throws IllegalStateException
	 *             if the query is not direct
	 */
	public String getQueriedJMBAG() {
		if(!isDirectQuery())
			throw new IllegalStateException("Query is not direct.");
		
		QueryLexer lexer = new QueryLexer(queryString);
		lexer.nextToken();
		lexer.nextToken();
		
		String resultString = formatLiteral(lexer.nextToken());
		
		return resultString;
	}
	
	/**
	 * Method that constructs conditional expressions list from the query
	 * 
	 * @return list containing conditional expressions
	 * @throws IllegalArgumentException
	 *             in case the query text is not valid
	 */
	public List<ConditionalExpression> getQuery() {
		List<ConditionalExpression> resultList = new ArrayList<>();
		QueryLexer lexer = new QueryLexer(queryString);
		
		if(queryString.trim().isEmpty()) {
			return resultList;
		}
		
		while(true) {
			String stringFieldGetter = lexer.nextToken();
			if(stringFieldGetter == null)
				throw new IllegalArgumentException("Field getter missing.");
			
			IFieldValueGetter fieldGetter = identifyFieldGetter(stringFieldGetter);
			
			String stringOperator = lexer.nextToken();
			if(stringOperator == null)
				throw new IllegalArgumentException("Operator missing.");
			
			IComparisonOperator operator = identifyOperator(stringOperator);
			
			String unformattedLiteral = lexer.nextToken();
			if(unformattedLiteral == null)
				throw new IllegalArgumentException("Literal missing.");
			
			String literal = formatLiteral(unformattedLiteral);
			
			resultList.add(new ConditionalExpression(fieldGetter, literal, operator));
			
			String end = lexer.nextToken();
			
			if(end == null) 
				break;
			else if(!end.toLowerCase().equals("and"))
				throw new IllegalArgumentException("Conditional expressions must be divided by" +
						 					" \"AND\" and not by \"" + end + "\".");
		}
		
		return resultList;
	}
	
	/**
	 * Method that identifies field value getter from the given string
	 * 
	 * @param stringFieldGetter
	 *            string that will be "identified"
	 * @return Field value getter identified from the given string
	 * @throws IllegalArgumentException
	 *             in case the string cannot be identified
	 */
	public IFieldValueGetter identifyFieldGetter(String stringFieldGetter) {
		
		switch(stringFieldGetter) {
			case("jmbag") 		:	return FieldValueGetters.JMBAG;
			case("firstName") 	: 	return FieldValueGetters.FIRST_NAME;
			case("lastName")	: 	return FieldValueGetters.LAST_NAME;
			
			default				: 	throw new IllegalArgumentException(stringFieldGetter
																	+ " is not a recognizable Field getter.");
		}
	}
	
	/**
	 * Method that identifies comparison operator from the given string
	 * 
	 * @param stringOperator
	 *            string that will be "identified"
	 * @return Comparison operator identified from the given string
	 * @throws IllegalArgumentException
	 *             in case the string cannot be identified
	 */
	public IComparisonOperator identifyOperator(String stringOperator) {
		
		switch(stringOperator) {
			case("<")	: 	return ComparisonOperators.LESS;
			case("<=")	: 	return ComparisonOperators.LESS_OR_EQUALS;
			case("=")	: 	return ComparisonOperators.EQUALS;
			case(">")	:	return ComparisonOperators.GREATER;
			case(">=")	: 	return ComparisonOperators.GREATER_OR_EQUALS;
			case("LIKE"):	return ComparisonOperators.LIKE;
			case("!=")	: 	return ComparisonOperators.NOT_EQUALS;
			
			default		: 	throw new IllegalArgumentException(stringOperator + " is not a recognizable operator.");
		}
	}
	
	/**
	 * Method that formats literal by removing quotation marks
	 * 
	 * @param unformattedLiteral
	 *            literal that is not formatted
	 * @return formatted literal
	 */
	private String formatLiteral(String unformattedLiteral) {
		if(unformattedLiteral.indexOf("\"") != 0 || 
				unformattedLiteral.lastIndexOf("\"") != unformattedLiteral.length() - 1)
			throw new IllegalArgumentException("Literal must be surrounded with '\"' characters.");
		
		if(unformattedLiteral.length() == 2)
			return "";
		else
			return unformattedLiteral.substring(1, unformattedLiteral.length() - 1);
	}
}

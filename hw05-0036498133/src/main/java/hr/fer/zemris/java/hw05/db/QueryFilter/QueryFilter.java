package hr.fer.zemris.java.hw05.db.QueryFilter;

import java.util.List;
import java.util.Objects;

import hr.fer.zemris.java.hw05.db.ConditionalExpression.ConditionalExpression;
import hr.fer.zemris.java.hw05.db.IFilter.IFilter;
import hr.fer.zemris.java.hw05.db.StudentRecord.StudentRecord;

/**
 * Class that implements IFilter interface and represents query filter that
 * contains list where condition expressions are stored
 * 
 * @author Rafael Josip Penić
 *
 */
public class QueryFilter implements IFilter {

	/**
	 * List that can store one or more condition expressions
	 */
	private List<ConditionalExpression> expressionList;

	/**
	 * Constructor for QueryFilter objects
	 * 
	 * @param expressionList
	 *            condition expression list of the query filter that will be
	 *            constructed
	 */
	public QueryFilter(List<ConditionalExpression> expressionList) {
		Objects.requireNonNull(expressionList, "Given list must not be null.");
		this.expressionList = expressionList;
	}

	@Override
	public boolean accepts(StudentRecord record) {
		Objects.requireNonNull(record, "Given student record is null.");

		for (ConditionalExpression exp : expressionList) {
			if (!exp.getComparisonOperator().satisfied(exp.getFieldGetter().get(record), exp.getStringLiteral()))
				return false;
		}

		return true;
	}
}

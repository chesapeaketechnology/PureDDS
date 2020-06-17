package me.coley.puredds.sub;

import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.sub.DataReader;
import org.omg.dds.sub.QueryCondition;
import org.omg.dds.sub.Subscriber;

import java.util.Collections;
import java.util.List;

/**
 * QueryCondition impl.
 *
 * @param <T>
 * 		Topic data type.
 *
 * @author Matt Coley
 */
public class QueryConditionImpl<T> extends ReadConditionImpl<T> implements QueryCondition<T> {
	private final String queryExpression;
	private List<String> queryParameters;

	/**
	 * @param environment
	 * 		Environment context.
	 * @param parent
	 * 		DataReader context.
	 * @param states
	 * 		Data states accessor.
	 * @param queryExpression
	 * 		TODO: Describe
	 * @param queryParameters
	 * 		TODO: Describe
	 */
	public QueryConditionImpl(ServiceEnvironment environment, DataReader<T> parent, Subscriber.DataState states,
							  String queryExpression, List<String> queryParameters) {
		super(environment, parent, states);
		this.queryExpression = queryExpression;
		this.queryParameters = queryParameters;
	}

	@Override
	public boolean getTriggerValue() {
		// TODO: How to incorporate the query?
		return super.getTriggerValue();
	}

	@Override
	public String getQueryExpression() {
		return queryExpression;
	}

	@Override
	public List<String> getQueryParameters() {
		return Collections.unmodifiableList(queryParameters);
	}

	@Override
	public void setQueryParameters(List<String> queryParameters) {
		this.queryParameters = queryParameters;
	}
}

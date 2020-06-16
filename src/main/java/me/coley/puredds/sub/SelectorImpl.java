package me.coley.puredds.sub;

import org.omg.dds.core.InstanceHandle;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.sub.DataReader;
import org.omg.dds.sub.ReadCondition;
import org.omg.dds.sub.Sample;
import org.omg.dds.sub.Subscriber;

import java.util.Collections;
import java.util.List;

/**
 * Selector impl.
 *
 * @param <T>
 * 		Topic data type.
 *
 * @author Matt Coley
 */
public class SelectorImpl<T> implements DataReader.Selector<T> {
	private final ServiceEnvironment environment;
	private final DataReaderImpl<T> reader;
	private InstanceHandle handle;
	private Subscriber.DataState dataState;
	private int maxSamples = -1;
	// DOCS: These are unused locally. Is this intended? There's literally no javadoc for this class.
	private boolean retrieveNextInstance;
	private String queryExpression;
	private List<String> queryParameters;

	/**
	 * @param environment
	 * 		Environment context.
	 * @param reader
	 * 		Wrapped reader.
	 */
	public SelectorImpl(ServiceEnvironment environment, DataReaderImpl<T> reader) {
		this.environment = environment;
		this.reader = reader;
	}

	@Override
	public DataReader.Selector<T> instance(InstanceHandle handle) {
		this.handle = handle;
		return this;
	}

	@Override
	public DataReader.Selector<T> dataState(Subscriber.DataState state) {
		this.dataState = state;
		return this;
	}

	@Override
	public DataReader.Selector<T> nextInstance(boolean retrieveNextInstance) {
		this.retrieveNextInstance = retrieveNextInstance;
		return this;
	}

	@Override
	public DataReader.Selector<T> maxSamples(int maxSamples) {
		this.maxSamples = maxSamples;
		return this;
	}

	@Override
	public DataReader.Selector<T> Content(String queryExpression, List<String> queryParameters) {
		this.queryExpression = queryExpression;
		this.queryParameters = Collections.unmodifiableList(queryParameters);
		return this;
	}

	@Override
	public InstanceHandle getInstance() {
		return handle;
	}

	@Override
	public boolean retrieveNextInstance() {
		return retrieveNextInstance;
	}

	@Override
	public Subscriber.DataState getDataState() {
		return dataState;
	}

	@Override
	public String getQueryExpression() {
		return queryExpression;
	}

	@Override
	public List<String> getQueryParameters() {
		return queryParameters;
	}

	@Override
	public int getMaxSamples() {
		return maxSamples;
	}

	@Override
	public ReadCondition<T> getCondition() {
		return reader.createReadCondition(getDataState());
	}

	@Override
	public Sample.Iterator<T> read() {
		return reader.read(this);
	}

	@Override
	public List<Sample<T>> read(List<Sample<T>> samples) {
		return reader.read(samples, this);
	}

	@Override
	public Sample.Iterator<T> take() {
		return reader.take(this);
	}

	@Override
	public List<Sample<T>> take(List<Sample<T>> samples) {
		return reader.take(samples, this);
	}

	@Override
	public ServiceEnvironment getEnvironment() {
		return environment;
	}
}

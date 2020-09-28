package me.coley.puredds.sub;

import me.coley.puredds.core.EntityBase;
import me.coley.puredds.core.handle.InstanceHandleImpl;
import me.coley.puredds.core.status.DataAvailableStatusImpl;
import me.coley.puredds.core.status.LivelinessChangedStatusImpl;
import me.coley.puredds.core.status.RequestedDeadlineMissedStatusImpl;
import me.coley.puredds.core.status.RequestedIncompatibleQosStatusImpl;
import me.coley.puredds.core.status.SampleLostStatusImpl;
import me.coley.puredds.core.status.SampleRejectedStatusImpl;
import me.coley.puredds.core.status.SubscriptionMatchedStatusImpl;
import org.omg.dds.core.Duration;
import org.omg.dds.core.Entity;
import org.omg.dds.core.InstanceHandle;
import org.omg.dds.core.ModifiableInstanceHandle;
import org.omg.dds.core.QosProvider;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.status.LivelinessChangedStatus;
import org.omg.dds.core.status.RequestedDeadlineMissedStatus;
import org.omg.dds.core.status.RequestedIncompatibleQosStatus;
import org.omg.dds.core.status.SampleLostStatus;
import org.omg.dds.core.status.SampleRejectedStatus;
import org.omg.dds.core.status.Status;
import org.omg.dds.core.status.SubscriptionMatchedStatus;
import org.omg.dds.sub.DataReader;
import org.omg.dds.sub.DataReaderListener;
import org.omg.dds.sub.DataReaderQos;
import org.omg.dds.sub.QueryCondition;
import org.omg.dds.sub.ReadCondition;
import org.omg.dds.sub.Sample;
import org.omg.dds.sub.SampleState;
import org.omg.dds.sub.Subscriber;
import org.omg.dds.topic.PublicationBuiltinTopicData;
import org.omg.dds.topic.TopicDescription;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * DataReader impl.
 *
 * @param <T>
 * 		Topic data type.
 *
 * @author Matt Coley
 */
public class DataReaderImpl<T> extends EntityBase<DataReader<T>, DataReaderListener<T>, DataReaderQos>
		implements DataReader<T> {
	private final Subscriber parent;
	private final TopicDescription<T> topic;
	private final List<ReadCondition<T>> conditions = new ArrayList<>();

	/**
	 * @param environment
	 * 		Environment context.
	 * @param parent
	 * 		Subscriber that this topic belongs to.
	 * @param topic
	 * 		Topic to read from.
	 * @param qos
	 * 		Quality of service of the reader.
	 * @param listener
	 * 		Optional reader listener.
	 * @param listenerStatuses
	 * 		Status filter mask for the listener.
	 */
	public DataReaderImpl(ServiceEnvironment environment, Subscriber parent, TopicDescription<T> topic,
						  DataReaderQos qos, DataReaderListener<T> listener,
						  Collection<Class<? extends Status>> listenerStatuses) {
		super(environment);
		this.parent = parent;
		this.topic = topic;
		setQos(qos);
		setListener(listener, listenerStatuses);
	}

	@Override
	protected void addInitialStatuses() {
		// TODO: Should consider non-null default values for some of these?
		registerStatus(new DataAvailableStatusImpl(getEnvironment()));
		registerStatus(new SampleLostStatusImpl(getEnvironment(), 0, 0));
		registerStatus(new RequestedDeadlineMissedStatusImpl(getEnvironment(), 0, 0, null));
		registerStatus(new RequestedIncompatibleQosStatusImpl(getEnvironment(), 0, 0, null, null));
		registerStatus(new LivelinessChangedStatusImpl(getEnvironment(), 0, 0, 0, 0, null));
		registerStatus(new SubscriptionMatchedStatusImpl(getEnvironment(), 0, 0, 0, 0, null));
		registerStatus(new SampleRejectedStatusImpl(getEnvironment(), 0, 0,
				SampleRejectedStatus.Kind.NOT_REJECTED, null));
	}

	@Override
	public void closeContainedEntities() {
		conditions.forEach(ReadCondition::close);
		conditions.clear();
	}

	@Override
	public void close() {
		super.close();
		// TODO: Additional work on closure
		// "A DataReader cannot be closed if it has any outstanding loans as a result of
		//  a call to DataReader.read(), DataReader.take(), or one of the variants thereof."
	}

	@Override
	public void retain() {
		// TODO: What to do here?
	}

	@Override
	@SuppressWarnings("unchecked")
	public <X> DataReader<X> cast() {
		return (DataReader<X>) this;
	}

	@Override
	public Selector<T> select() {
		// TODO: Investigate enforcing defined constraints wherever this is used:
		//  - https://www.javadoc.io/static/org.omg.dds/java5-psm/1.0/org/omg/dds/sub/DataReader.html#take(org.omg.dds.sub.DataReader.Selector)
		return new SelectorImpl<>(getEnvironment(), this);
	}

	@Override
	public ReadCondition<T> createReadCondition(Subscriber.DataState states) {
		ReadCondition<T> condition = new ReadConditionImpl<>(getEnvironment(), this, states);
		conditions.add(condition);
		return condition;
	}

	@Override
	public QueryCondition<T> createQueryCondition(String queryExpression, List<String> queryParameters) {
		return createQueryCondition(getParent().createDataState(), queryExpression, queryParameters);
	}

	@Override
	public QueryCondition<T> createQueryCondition(Subscriber.DataState states, String queryExpression,
												  List<String> queryParameters) {
		QueryCondition<T> condition =
				new QueryConditionImpl<>(getEnvironment(), this, states, queryExpression, queryParameters);
		conditions.add(condition);
		return condition;
	}

	@Override
	public SampleRejectedStatus getSampleRejectedStatus() {
		// TODO: Track this info
		//  - Total samples rejected
		//  - Samples rejected since last query
		//  - Most recent rejection reason
		return getStatus(SampleRejectedStatus.class);
	}

	@Override
	public LivelinessChangedStatus getLivelinessChangedStatus() {
		// TODO: Track this info
		//  - Current number of alive writers on the topic this reads from
		//  - Current number of dead writers on the topic this reads from
		//  - differences in these numbers from the last query
		return getStatus(LivelinessChangedStatus.class);
	}

	@Override
	public RequestedDeadlineMissedStatus getRequestedDeadlineMissedStatus() {
		// TODO: Track this info
		//  - Total number of missed deadlines
		//  - Difference from last query
		return getStatus(RequestedDeadlineMissedStatus.class);
	}

	@Override
	public RequestedIncompatibleQosStatus getRequestedIncompatibleQosStatus() {
		// TODO: Track this info
		//  - Total number of incompatibile writers on the same topic
		//  - Difference in number from last query
		//  - Last policy that was incompatible
		//  - Set of policy counts to summarize all total-incompatibilities for all policies
		return getStatus(RequestedIncompatibleQosStatus.class);
	}

	@Override
	public SubscriptionMatchedStatus getSubscriptionMatchedStatus() {
		// TODO: Track this info
		//  - Total number of reader/writer compatibility matches
		//  - Difference in number from last query
		//  - Current number of reader/writer compatibility matches
		//  - Difference in number from last query
		return getStatus(SubscriptionMatchedStatus.class);
	}

	@Override
	public SampleLostStatus getSampleLostStatus() {
		// TODO: Track this info
		//  - Lost samples across ALL instances (so it should be static?)
		//  - Difference from last query
		return getStatus(SampleLostStatus.class);
	}

	@Override
	public void waitForHistoricalData(long maxWait, TimeUnit unit) throws TimeoutException {
		waitForHistoricalData(getEnvironment().getSPI().newDuration(maxWait, unit));
	}

	@Override
	public void waitForHistoricalData(Duration maxWait) throws TimeoutException {
		// Only used when (durability != Durability.Kind.VOLATILE)
		// TODO: Wait until history data is received, block the current thread
		//  - Throw TimeoutException :: if maxWait elapsed before all the data was received.
	}

	@Override
	public Set<InstanceHandle> getMatchedPublications() {
		// TODO: Record matched DataWriters on the topic
		//  - Ignore when ignored by the parent DomainParticipant
		//  - Returned as a new collection
		throw new UnsupportedOperationException();
	}

	@Override
	public PublicationBuiltinTopicData getMatchedPublicationData(InstanceHandle publicationHandle) {
		// TODO: Record matched DataWriters on the topic
		//  - Ignore when ignored by the parent DomainParticipant
		throw new UnsupportedOperationException();
	}

	@Override
	public Sample.Iterator<T> read() {
		return read(select());
	}

	@Override
	public Sample.Iterator<T> read(int maxSamples) {
		return read(select().maxSamples(maxSamples));
	}

	@Override
	public Sample.Iterator<T> read(Selector<T> query) {
		return new SampleIteratorImpl<>(read(new ArrayList<>(), query));
	}

	@Override
	public List<Sample<T>> read(List<Sample<T>> samples) {
		return read(samples, select());
	}

	@Override
	public List<Sample<T>> read(List<Sample<T>> samples, Selector<T> selector) {
		// TODO: Read operation
		//  - Samples are not "loaned" but deep copied into the list
		//  - if (samples read < list.size(), the list will be trimmed to fit the samples read
		//  - if (list == null), the list size will be unbounded
		//  - if (samples read == 0), the list will be empty
		throw new UnsupportedOperationException();
		// return samples;
	}

	@Override
	public Sample.Iterator<T> take() {
		return new SampleIteratorImpl<>(take(new ArrayList<>()));
	}

	@Override
	public Sample.Iterator<T> take(int maxSamples) {
		return new SampleIteratorImpl<>(take(new ArrayList<>(), select().maxSamples(maxSamples)));
	}

	@Override
	public Sample.Iterator<T> take(Selector<T> query) {
		return new SampleIteratorImpl<>(take(new ArrayList<>(), query));
	}

	@Override
	public List<Sample<T>> take(List<Sample<T>> samples) {
		return take(samples, select());
	}

	@Override
	public List<Sample<T>> take(List<Sample<T>> samples, Selector<T> query) {
		// TODO: Take operation
		//  - Samples are not "loaned" but deep copied into the list
		//  - if (samples read < list.size(), the list will be trimmed to fit the samples read
		//  - if (list == null), the list size will be unbounded
		//  - if (samples read == 0), the list will be empty
		throw new UnsupportedOperationException();
		// return samples;
	}

	@Override
	public boolean readNextSample(Sample<T> sample) {
		List<Sample<T>> read = read(new ArrayList<>(), select()
				.maxSamples(1)
				.dataState(getParent().createDataState()
					.with(SampleState.NOT_READ)
						.withAnyViewState()
						.withAnyInstanceState()
				));
		// TODO: Copy read value into "sample"
		//  - What is the point if we're not caching this anywhere to use?
		return !read.isEmpty();
	}

	@Override
	public boolean takeNextSample(Sample<T> sample) {
		List<Sample<T>> take = take(new ArrayList<>(), select()
				.maxSamples(1)
				.dataState(getParent().createDataState()
						.with(SampleState.NOT_READ)
						.withAnyViewState()
						.withAnyInstanceState()
				));
		// TODO: Copy take value into "sample"
		//  - What is the point if we're not caching this anywhere to use?
		return !take.isEmpty();
	}

	@Override
	public T getKeyValue(T keyHolder, InstanceHandle handle) {
		// TODO: Implement?
		//  - "handle of the instance whose value to place into the holder
		//  - IllegalArgumentException - when handle does not map to a known object in the DataReader
		//  - Unspecified behavior if implementation does not track invalid handles
		return keyHolder;
	}

	@Override
	public ModifiableInstanceHandle lookupInstance(ModifiableInstanceHandle handle, T keyHolder) {
		if (handle instanceof InstanceHandleImpl && keyHolder instanceof Entity) {
			return ((InstanceHandleImpl) handle).withEntity((Entity<?, ?>) keyHolder);
		} else {
			// The spec says this needs to be a nil-handle, which should be an immutable handle to "null"...
			// But the return type won't allow that, so we use a mutable handle that points to "null"...
			return new InstanceHandleImpl(getEnvironment()).withEntity(null);
		}
	}

	@Override
	public InstanceHandle lookupInstance(T keyHolder) {
		if (keyHolder instanceof Entity) {
			return ((Entity<?, ?>) keyHolder).getInstanceHandle();
		} else {
			return getEnvironment().getSPI().nilHandle();
		}
	}

	@Override
	public TopicDescription<T> getTopicDescription() {
		return topic;
	}

	@Override
	public Subscriber getParent() {
		return parent;
	}

	@Override
	protected DataReaderQos fetchProviderQos(QosProvider provider) {
		return provider.getDataReaderQos();
	}
}

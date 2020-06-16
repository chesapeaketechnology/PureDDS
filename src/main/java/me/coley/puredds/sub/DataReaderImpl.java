package me.coley.puredds.sub;

import me.coley.puredds.core.handle.InstanceHandleImpl;
import me.coley.puredds.core.status.LivelinessChangedStatusImpl;
import me.coley.puredds.core.status.RequestedDeadlineMissedStatusImpl;
import me.coley.puredds.core.status.RequestedIncompatibleQosStatusImpl;
import me.coley.puredds.core.status.SampleLostStatusImpl;
import me.coley.puredds.core.status.SampleRejectedStatusImpl;
import me.coley.puredds.core.status.SubscriptionMatchedStatusImpl;
import org.omg.dds.core.Duration;
import org.omg.dds.core.InstanceHandle;
import org.omg.dds.core.ModifiableInstanceHandle;
import org.omg.dds.core.QosProvider;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.StatusCondition;
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
public class DataReaderImpl<T> implements DataReader<T> {
	private final InstanceHandle handle = new InstanceHandleImpl(getEnvironment());
	private final ServiceEnvironment environment;
	private final Subscriber parent;
	private final TopicDescription<T> topic;
	private DataReaderQos qos;
	private DataReaderListener<T> listener;
	private Collection<Class<? extends Status>> listenerStatuses;
	private boolean enabled; // TODO: Check with this
	private boolean closed; // TODO: Check with this

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
	 * 		TODO: Describe
	 * @param listenerStatuses
	 * 		TODO: Describe
	 */
	public DataReaderImpl(ServiceEnvironment environment, Subscriber parent, TopicDescription<T> topic,
						  DataReaderQos qos, DataReaderListener<T> listener,
						  Collection<Class<? extends Status>> listenerStatuses) {
		this.environment = environment;
		this.parent = parent;
		this.topic = topic;
		this.qos = qos;
		this.listener = listener;
		this.listenerStatuses = listenerStatuses;
	}

	@Override
	public void enable() {
		this.enabled = true;
	}

	@Override
	public void closeContainedEntities() {
		// TODO: Close created entities
		//  - conditions
	}

	@Override
	public void close() {
		this.enabled = false;
		this.closed = true;
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
		return new SelectorImpl<>(getEnvironment(), this);
	}

	@Override
	public ReadCondition<T> createReadCondition(Subscriber.DataState states) {
		return createQueryCondition(states, null, null);
	}

	@Override
	public QueryCondition<T> createQueryCondition(String queryExpression, List<String> queryParameters) {
		return createQueryCondition(null, queryExpression, queryParameters);
	}

	@Override
	public QueryCondition<T> createQueryCondition(Subscriber.DataState states, String queryExpression, List<String> queryParameters) {
		// TODO: Create query condition. Possible cases:
		//  [states]
		//  [states, query]
		//  [query]
		throw new UnsupportedOperationException();
	}

	@Override
	public SampleRejectedStatus getSampleRejectedStatus() {
		// TODO: Track this info
		//  - Total samples rejected
		//  - Samples rejected since last query
		//  - Most recent rejection reason
		throw new UnsupportedOperationException();
		// return new SampleRejectedStatusImpl(getEnvironment(),
		// 		rejectedTotal, rejectedDx, lastRejectionReason, getInstanceHandle());
	}

	@Override
	public LivelinessChangedStatus getLivelinessChangedStatus() {
		// TODO: Track this info
		//  - Current number of alive writers on the topic this reads from
		//  - Current number of dead writers on the topic this reads from
		//  - differences in these numbers from the last query
		throw new UnsupportedOperationException();
		// return new LivelinessChangedStatusImpl(getEnvironment(), aliveCount, deadCount,
		// 		aliveCountDx, deadCountDx, getInstanceHandle());
	}

	@Override
	public RequestedDeadlineMissedStatus getRequestedDeadlineMissedStatus() {
		// TODO: Track this info
		//  - Total number of missed deadlines
		//  - Difference from last query
		throw new UnsupportedOperationException();
		// return new RequestedDeadlineMissedStatusImpl(getEnvironment(),
		// 		missedDeadlines, missedDeadlinesDx, getInstanceHandle());
	}

	@Override
	public RequestedIncompatibleQosStatus getRequestedIncompatibleQosStatus() {
		// TODO: Track this info
		//  - Total number of incompatibile writers on the same topic
		//  - Difference in number from last query
		//  - Last policy that was incompatible
		//  - Set of policy counts to summarize all total-incompatibilities for all policies
		throw new UnsupportedOperationException();
		// return new RequestedIncompatibleQosStatusImpl(getEnvironment(),
		// 		incompatibleCount, incompatibleDx, lastIncompatiblePolicy, incompatiblePoliciesCounts);
	}

	@Override
	public SubscriptionMatchedStatus getSubscriptionMatchedStatus() {
		// TODO: Track this info
		//  - Total number of reader/writer compatibility matches
		//  - Difference in number from last query
		//  - Current number of reader/writer compatibility matches
		//  - Difference in number from last query
		throw new UnsupportedOperationException();
		// return new SubscriptionMatchedStatusImpl(getEnvironment(),
		// 		compatibleCount, compatibleCountDx, compatibleCurrent, compatibleCurrentDx, getInstanceHandle());
	}

	@Override
	public SampleLostStatus getSampleLostStatus() {
		// TODO: Track this info
		//  - Lost samples across ALL instances (so it should be static?)
		//  - Difference from last query
		throw new UnsupportedOperationException();
		// return new SampleLostStatusImpl(getEnvironment(), lostCount, lostCountDx);
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
		// TODO: Unconstrained read operation
		throw new UnsupportedOperationException();
	}

	@Override
	public Sample.Iterator<T> read(int maxSamples) {
		// TODO: Unconstrained read operation, limiting to a maximum amount of samples
		throw new UnsupportedOperationException();
	}

	@Override
	public Sample.Iterator<T> read(Selector<T> query) {
		// TODO: Selector constrained read operation
		//  - https://www.javadoc.io/static/org.omg.dds/java5-psm/1.0/org/omg/dds/sub/DataReader.html#read(org.omg.dds.sub.DataReader.Selector)
		throw new UnsupportedOperationException();
	}

	@Override
	public List<Sample<T>> read(List<Sample<T>> samples) {
		// TODO: Unconstrained read operation
		//  - Samples are not "loaned" but deep copied into the list
		//  - if (samples read < list.size(), the list will be trimmed to fit the samples read
		//  - if (list == null), the list size will be unbounded
		//  - if (samples read == 0), the list will be empty
		throw new UnsupportedOperationException();
		// return samples;
	}

	@Override
	public List<Sample<T>> read(List<Sample<T>> samples, Selector<T> selector) {
		// TODO: See above, but constrained
		throw new UnsupportedOperationException();
		// return samples;
	}

	@Override
	public Sample.Iterator<T> take() {
		// TODO: Unconstrained take operation
		throw new UnsupportedOperationException();
	}

	@Override
	public Sample.Iterator<T> take(int maxSamples) {
		// TODO: Unconstrained take operation, limiting to a maximum amount of samples
		throw new UnsupportedOperationException();
	}

	@Override
	public Sample.Iterator<T> take(Selector<T> query) {
		// TODO: Constrained take operation
		//  - https://www.javadoc.io/static/org.omg.dds/java5-psm/1.0/org/omg/dds/sub/DataReader.html#take(org.omg.dds.sub.DataReader.Selector)
		throw new UnsupportedOperationException();
	}

	@Override
	public List<Sample<T>> take(List<Sample<T>> samples) {
		// TODO: Unconstrained take operation
		//  - Samples are not "loaned" but deep copied into the list
		//  - if (samples read < list.size(), the list will be trimmed to fit the samples read
		//  - if (list == null), the list size will be unbounded
		//  - if (samples read == 0), the list will be empty
		throw new UnsupportedOperationException();
		// return samples;
	}

	@Override
	public List<Sample<T>> take(List<Sample<T>> samples, Selector<T> query) {
		// TODO: See above, but constrained
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
		// TODO: https://www.javadoc.io/static/org.omg.dds/java5-psm/1.0/org/omg/dds/sub/DataReader.html#lookupInstance(org.omg.dds.core.ModifiableInstanceHandle,%20TYPE)
		//  - key-to-handle basically
		return handle;
	}

	@Override
	public StatusCondition<DataReader<T>> getStatusCondition() {
		// TODO: Get triggered conditions
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<Class<? extends Status>> getStatusChanges() {
		// TODO: Status changes
		throw new UnsupportedOperationException();
	}

	@Override
	public InstanceHandle lookupInstance(T keyHolder) {
		// TODO: map to handle
		throw new UnsupportedOperationException();
	}

	@Override
	public DataReaderListener<T> getListener() {
		return listener;
	}

	@Override
	public void setListener(DataReaderListener<T> listener) {
		setListener(listener, null);
	}

	@Override
	public void setListener(DataReaderListener<T> listener, Collection<Class<? extends Status>> statuses) {
		this.listener = listener;
		this.listenerStatuses = statuses;
	}

	@Override
	public TopicDescription<T> getTopicDescription() {
		return topic;
	}

	@Override
	public DataReaderQos getQos() {
		return qos;
	}

	@Override
	public void setQos(DataReaderQos qos) {
		this.qos = qos;
	}

	@Override
	public void setQos(String qosLibraryName, String qosProfileName) {
		QosProvider provider = getEnvironment().getSPI().newQosProvider(qosLibraryName, qosProfileName);
		if (provider != null)
			setQos(provider.getDataReaderQos());
	}

	@Override
	public InstanceHandle getInstanceHandle() {
		return handle;
	}

	@Override
	public Subscriber getParent() {
		return parent;
	}

	@Override
	public ServiceEnvironment getEnvironment() {
		return environment;
	}
}

package me.coley.puredds.sub;

import me.coley.puredds.core.event.DataAvailableEventImpl;
import me.coley.puredds.core.handle.InstanceHandleImpl;
import me.coley.puredds.core.status.DataAvailableStatusImpl;
import me.coley.puredds.domain.DomainParticipantImpl;
import me.coley.puredds.util.struct.HashMultiMap;
import me.coley.puredds.util.struct.MultiMap;
import org.omg.dds.core.InstanceHandle;
import org.omg.dds.core.QosProvider;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.StatusCondition;
import org.omg.dds.core.status.Status;
import org.omg.dds.domain.DomainParticipant;
import org.omg.dds.sub.DataReader;
import org.omg.dds.sub.DataReaderListener;
import org.omg.dds.sub.DataReaderQos;
import org.omg.dds.sub.Subscriber;
import org.omg.dds.sub.SubscriberListener;
import org.omg.dds.sub.SubscriberQos;
import org.omg.dds.topic.TopicDescription;
import org.omg.dds.topic.TopicQos;

import java.util.Collection;
import java.util.Set;

/**
 * Subscriber impl.
 *
 * @author Matt Coley
 */
public class SubscriberImpl implements Subscriber {
	private final InstanceHandle handle = new InstanceHandleImpl(getEnvironment());
	private final ServiceEnvironment environment;
	private final DomainParticipantImpl parent;
	private final MultiMap<String, DataReader<?>> topicReaders = new HashMultiMap<>();
	private SubscriberListener listener; // TODO: Use listener
	private Collection<Class<? extends Status>> listenerStatuses;
	private DataReaderQos defaultDataReaderQos;
	private SubscriberQos qos;
	private boolean enabled; // TODO: Check with this
	private boolean closed; // TODO: Check with this

	/**
	 * @param environment
	 * 		Environment context.
	 * @param parent
	 * 		Domain participant that this topic belongs to.
	 * @param qos
	 * 		Quality of service for the subscriber.
	 * @param listener
	 * 		TODO: Describe
	 * @param listenerStatuses
	 * 		TODO: Describe
	 */
	public SubscriberImpl(ServiceEnvironment environment, DomainParticipantImpl parent, SubscriberQos qos, SubscriberListener listener,
						  Collection<Class<? extends Status>> listenerStatuses) {
		this.environment = environment;
		this.parent = parent;
		this.qos = qos;
		this.listener = listener;
		this.listenerStatuses = listenerStatuses;
	}

	@Override
	public void beginAccess() {
		// TODO: If using Presentation.AccessScopeKind.GROUP ...
		//  - Call before any sample-accessing operations (getDataReaders(Collection), DataReader.take, etc)
		//    - If not satisfied, throw PreconditionException.
		//  - Call endAccess() when done
		//
		//  So there must be some state recorded here for that.
	}

	@Override
	public void endAccess() {
		// See "beginAccess()"
	}

	@Override
	public void closeContainedEntities() {
		topicReaders.forAll((topic, reader) -> {
			reader.closeContainedEntities();
			reader.close();
		});
	}

	@Override
	public void notifyDataReaders() {
		topicReaders.forAll((topic, reader) -> reader.getListener().onDataAvailable(
				new DataAvailableEventImpl(getEnvironment(), reader, new DataAvailableStatusImpl(getEnvironment()))));
	}

	@Override
	public void enable() {
		this.enabled = true;
	}

	@Override
	public void close() {
		this.closed = true;
		this.enabled = false;
	}

	@Override
	public void retain() {
		// TODO: What to do here?
	}

	@Override
	public DataState createDataState() {
		return new DataStateImpl(getEnvironment());
	}

	@Override
	public <T> DataReader<T> createDataReader(TopicDescription<T> topic) {
		return createDataReader(topic, getDefaultDataReaderQos());
	}

	@Override
	public <T> DataReader<T> createDataReader(TopicDescription<T> topic, DataReaderQos qos) {
		return createDataReader(topic, qos, null, null);
	}

	@Override
	public <T> DataReader<T> createDataReader(TopicDescription<T> topic, DataReaderQos qos, DataReaderListener<T> listener, Collection<Class<? extends Status>> statuses) {
		return new DataReaderImpl<>(getEnvironment(), this, topic, qos, listener, statuses);
	}

	@Override
	public <T> DataReader<T> lookupDataReader(TopicDescription<T> topicName) {
		return lookupDataReader(topicName.getName());
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> DataReader<T> lookupDataReader(String topicName) {
		Collection<DataReader<?>> set = topicReaders.get(topicName);
		if (set == null || set.isEmpty()) {
			return null;
		}
		return (DataReader<T>) set.iterator().next();
	}

	@Override
	public Collection<DataReader<?>> getDataReaders(Collection<DataReader<?>> readers) {
		return getDataReaders(readers,
				createDataState().withAnyInstanceState().withAnySampleState().withAnyViewState());
	}

	@Override
	public Collection<DataReader<?>> getDataReaders(Collection<DataReader<?>> readers, DataState dataState) {
		// TODO: Add matched readers into "readers"
		return readers;
	}

	@Override
	public DataReaderQos getDefaultDataReaderQos() {
		return defaultDataReaderQos;
	}

	@Override
	public void setDefaultDataReaderQos(DataReaderQos qos) {
		this.defaultDataReaderQos = qos;
	}

	@Override
	public DataReaderQos copyFromTopicQos(DataReaderQos drQos, TopicQos tQos) {
		return drQos.withPolicies(
				tQos.getDeadline(),
				tQos.getDestinationOrder(),
				tQos.getDurability(),
				tQos.getHistory(),
				tQos.getLatencyBudget(),
				tQos.getLiveliness(),
				tQos.getOwnership(),
				tQos.getResourceLimits()
		);
	}

	@Override
	public SubscriberListener getListener() {
		return listener;
	}

	@Override
	public void setListener(SubscriberListener listener) {
		this.listener = listener;
	}

	@Override
	public void setListener(SubscriberListener listener, Collection<Class<? extends Status>> statuses) {
		this.listener = listener;
		this.listenerStatuses = statuses;
	}

	@Override
	public SubscriberQos getQos() {
		return qos;
	}

	@Override
	public void setQos(SubscriberQos qos) {
		this.qos = qos;
	}

	@Override
	public void setQos(String qosLibraryName, String qosProfileName) {
		QosProvider provider = getEnvironment().getSPI().newQosProvider(qosLibraryName, qosProfileName);
		if (provider != null) {
			setQos(provider.getSubscriberQos());
		}
	}

	@Override
	public StatusCondition<Subscriber> getStatusCondition() {
		// TODO: What to do here?
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<Class<? extends Status>> getStatusChanges() {
		// TODO: What to do here?
		throw new UnsupportedOperationException();
	}

	@Override
	public InstanceHandle getInstanceHandle() {
		return handle;
	}

	@Override
	public DomainParticipant getParent() {
		return parent;
	}

	@Override
	public ServiceEnvironment getEnvironment() {
		return environment;
	}
}

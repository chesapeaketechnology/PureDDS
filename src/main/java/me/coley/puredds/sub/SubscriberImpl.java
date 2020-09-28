package me.coley.puredds.sub;

import me.coley.puredds.core.EntityBase;
import me.coley.puredds.core.event.DataAvailableEventImpl;
import me.coley.puredds.core.status.DataAvailableStatusImpl;
import me.coley.puredds.domain.DomainParticipantImpl;
import me.coley.puredds.util.struct.HashMultiMap;
import me.coley.puredds.util.struct.MultiMap;
import org.omg.dds.core.QosProvider;
import org.omg.dds.core.ServiceEnvironment;
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

/**
 * Subscriber impl.
 *
 * @author Matt Coley
 */
public class SubscriberImpl extends EntityBase<Subscriber, SubscriberListener, SubscriberQos> implements Subscriber {
	private final DomainParticipantImpl parent;
	private final MultiMap<String, DataReader<?>> topicReaders = new HashMultiMap<>();
	private DataReaderQos defaultDataReaderQos;

	/**
	 * @param environment
	 * 		Environment context.
	 * @param parent
	 * 		Domain participant that this topic belongs to.
	 * @param qos
	 * 		Quality of service for the subscriber.
	 * @param listener
	 * 		Optional subscriber listener.
	 * @param listenerStatuses
	 * 		Status filter mask for the listener.
	 */
	public SubscriberImpl(ServiceEnvironment environment, DomainParticipantImpl parent, SubscriberQos qos,
						  SubscriberListener listener, Collection<Class<? extends Status>> listenerStatuses) {
		super(environment);
		this.parent = parent;
		setQos(qos);
		setListener(listener, listenerStatuses);
	}

	@Override
	protected void addInitialStatuses() {
		// No status for this type
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
	@SuppressWarnings({"rawtypes", "unchecked"})
	public void notifyDataReaders() {
		topicReaders.forAll((topic, reader) -> reader.getListener().onDataAvailable(
				new DataAvailableEventImpl(getEnvironment(), reader, new DataAvailableStatusImpl(getEnvironment()))));
	}

	@Override
	public void close() {
		super.close();
		// TODO: What else needs to be done here?
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
	public <T> DataReader<T> createDataReader(TopicDescription<T> topic, DataReaderQos qos,
											  DataReaderListener<T> listener,
											  Collection<Class<? extends Status>> statuses) {
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
	public DomainParticipant getParent() {
		return parent;
	}

	@Override
	protected SubscriberQos fetchProviderQos(QosProvider provider) {
		return provider.getSubscriberQos();
	}
}

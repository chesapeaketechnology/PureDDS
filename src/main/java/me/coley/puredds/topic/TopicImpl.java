package me.coley.puredds.topic;

import me.coley.puredds.core.handle.InstanceHandleImpl;
import org.omg.dds.core.InstanceHandle;
import org.omg.dds.core.QosProvider;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.StatusCondition;
import org.omg.dds.core.status.InconsistentTopicStatus;
import org.omg.dds.core.status.Status;
import org.omg.dds.domain.DomainParticipant;
import org.omg.dds.topic.Topic;
import org.omg.dds.topic.TopicDescription;
import org.omg.dds.topic.TopicListener;
import org.omg.dds.topic.TopicQos;
import org.omg.dds.type.TypeSupport;

import java.util.Collection;
import java.util.Set;

/**
 * Topic impl.
 *
 * @param <T>
 * 		Topic data type.
 *
 * @author Matt Coley
 */
public class TopicImpl<T> implements Topic<T> {
	private final ServiceEnvironment environment;
	private final DomainParticipant parent;
	private final InstanceHandle handle;
	private final String name;
	private final TypeSupport<T> type;
	private TopicListener<T> listener; // TODO: Use listener
	private Collection<Class<? extends Status>> listenerStatuses;
	private boolean enabled; // TODO: Check with this
	private TopicQos qos;

	/**
	 * @param environment
	 * 		Environment context.
	 * @param parent
	 * 		Domain participant that this topic belongs to.
	 * @param name
	 * 		Topic name.
	 * @param type
	 * 		Class of data type of the topic.
	 * @param qos
	 * 		Quality of service for the topic.
	 * @param listener
	 * 		Topic listener.
	 * @param statuses
	 * 		Status filter mask for the listener.
	 */
	public TopicImpl(ServiceEnvironment environment, DomainParticipant parent, String name, Class<T> type, TopicQos qos,
					 TopicListener<T> listener, Collection<Class<? extends Status>> statuses) {
		this(environment, parent, name, TypeSupport.newTypeSupport(type, environment), qos, listener, statuses);
	}

	/**
	 * @param environment
	 * 		Environment context.
	 * @param parent
	 * 		Domain participant that this topic belongs to.
	 * @param name
	 * 		Topic name.
	 * @param type
	 *        {@link TypeSupport} for the data type of the topic.
	 * @param qos
	 * 		Quality of service for the topic.
	 * @param listener
	 * 		Topic listener.
	 * @param statuses
	 * 		Status filter mask for the listener.
	 */
	public TopicImpl(ServiceEnvironment environment, DomainParticipant parent, String name, TypeSupport<T> type, TopicQos qos,
					 TopicListener<T> listener, Collection<Class<? extends Status>> statuses) {
		this.environment = environment;
		this.parent = parent;
		this.name = name;
		this.type = type;
		this.qos = qos;
		this.handle = new InstanceHandleImpl(environment);
		setListener(listener, statuses);
	}

	@Override
	public InconsistentTopicStatus getInconsistentTopicStatus() {
		// TODO: What to do here?
		throw new UnsupportedOperationException();
	}

	@Override
	public TopicListener<T> getListener() {
		return listener;
	}

	@Override
	public void setListener(TopicListener<T> listener) {
		setListener(listener, null);
	}

	@Override
	public void setListener(TopicListener<T> listener, Collection<Class<? extends Status>> statuses) {
		this.listener = listener;
		this.listenerStatuses = statuses;
	}

	@Override
	public TopicQos getQos() {
		return qos;
	}

	@Override
	public void setQos(TopicQos qos) {
		this.qos = qos;
	}

	@Override
	public void setQos(String qosLibraryName, String qosProfileName) {
		QosProvider provider = getEnvironment().getSPI().newQosProvider(qosLibraryName, qosProfileName);
		if (provider != null)
			setQos(provider.getTopicQos());
	}

	@Override
	public StatusCondition<Topic<T>> getStatusCondition() {
		// TODO: Use as a getter for the "org.omg.dds.core.StatusCondition"
		//  - Maybe have a sort of Environment-level "ConditionCache" that has a map of handles to conditions statuses
		//    - This will allow easily adding all condition tracking logic to an interface and adding to all those that need it
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<Class<? extends Status>> getStatusChanges() {
		// TODO: track statuses and see which change between each call of this method
		throw new UnsupportedOperationException();
	}

	@Override
	public InstanceHandle getInstanceHandle() {
		return this.handle;
	}

	@Override
	public TypeSupport<T> getTypeSupport() {
		return type;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <X> TopicDescription<X> cast() {
		// DOCS: There's no way to know what X is... so I can't think of what else they would expect aside from this...
		return (TopicDescription<X>) this;
	}

	@Override
	public String getTypeName() {
		return type.getTypeName();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public DomainParticipant getParent() {
		return parent;
	}

	@Override
	public void enable() {
		enabled = true;
	}

	@Override
	public void retain() {
		// TODO: What to do here?
	}

	@Override
	public void close() {
		enabled = false;
		// TODO: What to do here?
		//  - Need to mark as closed, then check in other methods if actions are legal
		//  - If action done on closed topic, must throw "AlreadyClosedException"
		//  - When there are readers/writers
		//    - Fail if they are still active
	}

	@Override
	public ServiceEnvironment getEnvironment() {
		return environment;
	}
}

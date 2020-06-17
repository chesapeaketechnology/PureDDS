package me.coley.puredds.topic;

import me.coley.puredds.core.EntityBase;
import org.omg.dds.core.QosProvider;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.status.InconsistentTopicStatus;
import org.omg.dds.core.status.Status;
import org.omg.dds.domain.DomainParticipant;
import org.omg.dds.topic.Topic;
import org.omg.dds.topic.TopicDescription;
import org.omg.dds.topic.TopicListener;
import org.omg.dds.topic.TopicQos;
import org.omg.dds.type.TypeSupport;

import java.util.Collection;

/**
 * Topic impl.
 *
 * @param <T>
 * 		Topic data type.
 *
 * @author Matt Coley
 */
public class TopicImpl<T> extends EntityBase<Topic<T>, TopicListener<T>, TopicQos> implements Topic<T> {
	private final DomainParticipant parent;
	private final String name;
	private final TypeSupport<T> type;

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
	public TopicImpl(ServiceEnvironment environment, DomainParticipant parent, String name, TypeSupport<T> type,
					 TopicQos qos, TopicListener<T> listener, Collection<Class<? extends Status>> statuses) {
		super(environment);
		this.parent = parent;
		this.name = name;
		this.type = type;
		setQos(qos);
		setListener(listener, statuses);
	}

	@Override
	public void retain() {
		// TODO: What to do here?
	}

	@Override
	public void close() {
		super.close();

		// "A Topic cannot be closed if it is still in use by any ContentFilteredTopics or MultiTopics."
		// "A Topic cannot be closed if any DataWriters or DataReader is still using it."

		// TODO: What to do here?
		//  - Need to mark as closed, then check in other methods if actions are legal
		//  - If action done on closed topic, must throw "AlreadyClosedException"
		//  - When there are readers/writers
		//    - Fail if they are still active
	}

	@Override
	public InconsistentTopicStatus getInconsistentTopicStatus() {
		// TODO: What to do here?
		throw new UnsupportedOperationException();
	}

	@Override
	public TypeSupport<T> getTypeSupport() {
		return type;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <X> TopicDescription<X> cast() {
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
	protected TopicQos fetchProviderQos(QosProvider provider) {
		return provider.getTopicQos();
	}
}

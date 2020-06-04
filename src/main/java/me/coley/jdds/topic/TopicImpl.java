package me.coley.jdds.topic;

import me.coley.jdds.core.handle.InstanceHandleImpl;
import org.omg.dds.core.InstanceHandle;
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
	private final InstanceHandle handle;

	/**
	 * @param environment
	 * 		Environment context.
	 */
	public TopicImpl(ServiceEnvironment environment) {
		this.environment = environment;
		this.handle = new InstanceHandleImpl(environment);
	}

	@Override
	public InconsistentTopicStatus getInconsistentTopicStatus() {
		return null;
	}

	@Override
	public TopicListener<T> getListener() {
		return null;
	}

	@Override
	public void setListener(TopicListener<T> listener) {

	}

	@Override
	public void setListener(TopicListener<T> listener, Collection<Class<? extends Status>> statuses) {

	}

	@Override
	public TopicQos getQos() {
		return null;
	}

	@Override
	public void setQos(TopicQos qos) {

	}

	@Override
	public void setQos(String qosLibraryName, String qosProfileName) {

	}

	@Override
	public void enable() {

	}

	@Override
	public StatusCondition<Topic<T>> getStatusCondition() {
		return null;
	}

	@Override
	public Set<Class<? extends Status>> getStatusChanges() {
		return null;
	}

	@Override
	public InstanceHandle getInstanceHandle() {
		return this.handle;
	}

	@Override
	public void retain() {

	}

	@Override
	public TypeSupport<T> getTypeSupport() {
		return null;
	}

	@Override
	public <X> TopicDescription<X> cast() {
		return null;
	}

	@Override
	public String getTypeName() {
		return null;
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public DomainParticipant getParent() {
		return null;
	}

	@Override
	public void close() {

	}

	@Override
	public ServiceEnvironment getEnvironment() {
		return environment;
	}
}

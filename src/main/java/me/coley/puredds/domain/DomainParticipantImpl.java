package me.coley.puredds.domain;

import me.coley.puredds.core.EntityBase;
import me.coley.puredds.core.datatype.ModifiableTimeImpl;
import me.coley.puredds.core.datatype.TimeImpl;
import me.coley.puredds.sub.SubscriberImpl;
import me.coley.puredds.topic.TopicImpl;
import org.omg.dds.core.*;
import org.omg.dds.core.status.Status;
import org.omg.dds.domain.DomainParticipant;
import org.omg.dds.domain.DomainParticipantListener;
import org.omg.dds.domain.DomainParticipantQos;
import org.omg.dds.pub.Publisher;
import org.omg.dds.pub.PublisherListener;
import org.omg.dds.pub.PublisherQos;
import org.omg.dds.sub.Subscriber;
import org.omg.dds.sub.SubscriberListener;
import org.omg.dds.sub.SubscriberQos;
import org.omg.dds.topic.ContentFilteredTopic;
import org.omg.dds.topic.MultiTopic;
import org.omg.dds.topic.ParticipantBuiltinTopicData;
import org.omg.dds.topic.Topic;
import org.omg.dds.topic.TopicBuiltinTopicData;
import org.omg.dds.topic.TopicDescription;
import org.omg.dds.topic.TopicListener;
import org.omg.dds.topic.TopicQos;
import org.omg.dds.type.TypeSupport;
import org.omg.dds.type.dynamic.DynamicType;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Domain participant implementation.
 *
 * @author Matt Coley
 */
public class DomainParticipantImpl
		extends EntityBase<DomainParticipant, DomainParticipantListener, DomainParticipantQos>
		implements DomainParticipant {
	private final int domainId;
	// TODO: Use these ignore items
	private final Collection<InstanceHandle> ignoredParticipants = new HashSet<>();
	private final Collection<InstanceHandle> ignoredTopics = new HashSet<>();
	private final Collection<InstanceHandle> ignoredPublications = new HashSet<>();
	private final Collection<InstanceHandle> ignoredSubscriptions = new HashSet<>();
	private Set<InstanceHandle> discoveredTopics;
	private Set<InstanceHandle> discoveredParticipants;
	private Set<Subscriber> subscribers;
	private PublisherQos defaultPublisherQos;
	private SubscriberQos defaultSubscriberQos;
	private TopicQos defaultTopicQos;


	/**
	 * Create the participant.
	 *
	 * @param environment
	 * 		Spawning environment that created this participant.
	 * @param domainId
	 * 		Domain to participate in.
	 * @param qos
	 * 		Quality of service to use.
	 * @param listener
	 * 		TODO: Describe
	 * @param statuses
	 * 		TODO: Describe
	 */
	public DomainParticipantImpl(ServiceEnvironment environment, int domainId, DomainParticipantQos qos,
								 DomainParticipantListener listener, Collection<Class<? extends Status>> statuses) {
		super(environment);
		this.domainId = domainId;
		setQos(qos);
		setListener(listener, statuses);
	}

	@Override
	public void assertLiveliness() {
		// Essentially a keep-alive that is used when data is not being sent regularly enough to
		// automatically be kept alive.
		// ...
		// or if the Liveliness-kind is MANUAL_BY_PARTICIPANT
		// TODO: Assert
	}

	@Override
	public void retain() {
		// TODO: What do here?
	}

	@Override
	public void close() {
		// TODO: What do here?
	}

	@Override
	public void closeContainedEntities() {
		// TODO: Close entities
	}

	@Override
	public Publisher createPublisher() {
		return createPublisher(getDefaultPublisherQos());
	}

	@Override
	public Publisher createPublisher(PublisherQos qos) {
		return createPublisher(qos, null, null);
	}

	@Override
	public Publisher createPublisher(PublisherQos qos, PublisherListener listener,
									 Collection<Class<? extends Status>> statuses) {
		// TODO: Publisher
		//  - And record with entity handle

		// Participant must not be closed
		// - If closed, throw exception

		// Participant must be enabled

		// What is the difference between "closed" and "!enabled"?

		throw new UnsupportedOperationException();
	}

	@Override
	public Subscriber createSubscriber() {
		return createSubscriber(getDefaultSubscriberQos());
	}

	@Override
	public Subscriber createSubscriber(SubscriberQos qos) {
		return createSubscriber(qos, null, null);
	}

	@Override
	public Subscriber createSubscriber(SubscriberQos qos, SubscriberListener listener,
									   Collection<Class<? extends Status>> statuses) {
		Subscriber subscriber = new SubscriberImpl(getEnvironment(), this, qos, listener, statuses);
		subscribers.add(subscriber);
		return subscriber;
	}

	@Override
	public Subscriber getBuiltinSubscriber() {
		// TODO: Build-in subscriber
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> Topic<T> createTopic(String topicName, Class<T> type) {
		return createTopic(topicName, type, getDefaultTopicQos(), null, null);
	}

	@Override
	public <T> Topic<T> createTopic(String topicName, Class<T> type, TopicQos qos, TopicListener<T> listener,
									Collection<Class<? extends Status>> statuses) {
		Topic<T> topic = new TopicImpl<>(getEnvironment(), this, topicName, type, qos, listener, statuses);
		discoveredTopics.add(topic.getInstanceHandle());
		return topic;
	}

	@Override
	public <T> Topic<T> createTopic(String topicName, TypeSupport<T> type) {
		return createTopic(topicName, type, getDefaultTopicQos(), null, null);
	}

	@Override
	public <T> Topic<T> createTopic(String topicName, TypeSupport<T> type, TopicQos qos, TopicListener<T> listener,
									Collection<Class<? extends Status>> statuses) {
		Topic<T> topic = new TopicImpl<>(getEnvironment(), this, topicName, type, qos, listener, statuses);
		discoveredTopics.add(topic.getInstanceHandle());
		return topic;
	}

	@Override
	public Topic<DynamicType> createTopic(String topicName, DynamicType type) {
		return createTopic(topicName, type, getDefaultTopicQos(), null, null);
	}

	@Override
	public Topic<DynamicType> createTopic(String topicName, DynamicType type, TopicQos qos,
										  TopicListener<DynamicType> listener,
										  Collection<Class<? extends Status>> statuses) {
		// TODO: Create topic dynamic
		//  - And record with entity handle
		throw new UnsupportedOperationException();
	}

	@Override
	public Topic<DynamicType> createTopic(String topicName, DynamicType type, TypeSupport<DynamicType> typeSupport) {
		return createTopic(topicName, type, typeSupport, getDefaultTopicQos(), null, null);
	}

	@Override
	public Topic<DynamicType> createTopic(String topicName, DynamicType type, TypeSupport<DynamicType> typeSupport,
										  TopicQos qos, TopicListener<DynamicType> listener,
										  Collection<Class<? extends Status>> statuses) {
		// TODO: Create topic dynamic
		//  - And record with entity handle
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> ContentFilteredTopic<T> createContentFilteredTopic(String name, Topic<? extends T> relatedTopic,
																  String filterExpression,
																  List<String> expressionParameters) {
		// TODO: Create topic: filtered-content
		//  - And record with entity handle
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> MultiTopic<T> createMultiTopic(String name, Class<T> type, String subscriptionExpression,
											  List<String> expressionParameters) {
		// TODO Create topic: multi
		//  - And record with entity handle
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> MultiTopic<T> createMultiTopic(String name, TypeSupport<T> type, String subscriptionExpression,
											  List<String> expressionParameters) {
		// TODO Create topic: multi
		//  - And record with entity handle
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> Topic<T> findTopic(String topicName, Duration timeout) throws TimeoutException {
		return findTopic(topicName, timeout.getDuration(TimeUnit.NANOSECONDS), TimeUnit.NANOSECONDS);
	}

	@Override
	public <T> Topic<T> findTopic(String topicName, long timeout, TimeUnit unit) throws TimeoutException {
		// TODO: Find topic
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> TopicDescription<T> lookupTopicDescription(String name) {
		// TODO: Find topic description
		throw new UnsupportedOperationException();
	}

	@Override
	public void ignoreParticipant(InstanceHandle handle) {
		ignoredParticipants.add(handle);
	}

	@Override
	public void ignoreTopic(InstanceHandle handle) {
		ignoredTopics.add(handle);
	}

	@Override
	public void ignorePublication(InstanceHandle handle) {
		ignoredPublications.add(handle);
	}

	@Override
	public void ignoreSubscription(InstanceHandle handle) {
		ignoredSubscriptions.add(handle);
	}

	@Override
	public int getDomainId() {
		return domainId;
	}

	@Override
	public PublisherQos getDefaultPublisherQos() {
		return defaultPublisherQos;
	}

	@Override
	public void setDefaultPublisherQos(PublisherQos qos) {
		defaultPublisherQos = qos;
	}

	@Override
	public SubscriberQos getDefaultSubscriberQos() {
		return defaultSubscriberQos;
	}

	@Override
	public void setDefaultSubscriberQos(SubscriberQos qos) {
		defaultSubscriberQos = qos;
	}

	@Override
	public TopicQos getDefaultTopicQos() {
		return defaultTopicQos;
	}

	@Override
	public void setDefaultTopicQos(TopicQos qos) {
		defaultTopicQos = qos;
	}

	@Override
	public Set<InstanceHandle> getDiscoveredParticipants() {
		return discoveredParticipants;
	}

	@Override
	public ParticipantBuiltinTopicData getDiscoveredParticipantData(InstanceHandle participantHandle) {
		// TODO: Record discovered participants
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<InstanceHandle> getDiscoveredTopics() {
		return discoveredTopics;
	}

	@Override
	public TopicBuiltinTopicData getDiscoveredTopicData(InstanceHandle topicHandle) {
		// TODO: Record discovered topics
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean containsEntity(InstanceHandle handle) {
		// TODO: Record discovered entities
		throw new UnsupportedOperationException();
	}

	@Override
	public ModifiableTime getCurrentTime(ModifiableTime currentTime) {
		return new ModifiableTimeImpl(getEnvironment());
	}

	@Override
	public Time getCurrentTime() {
		return new TimeImpl(getEnvironment());
	}

	@Override
	protected DomainParticipantQos fetchProviderQos(QosProvider provider) {
		return provider.getDomainParticipantQos();
	}
}

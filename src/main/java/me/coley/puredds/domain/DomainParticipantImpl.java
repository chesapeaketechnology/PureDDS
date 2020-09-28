package me.coley.puredds.domain;

import me.coley.puredds.core.EntityBase;
import me.coley.puredds.core.datatype.ModifiableTimeImpl;
import me.coley.puredds.core.datatype.TimeImpl;
import me.coley.puredds.sub.SubscriberImpl;
import me.coley.puredds.topic.TopicImpl;
import org.omg.dds.core.Duration;
import org.omg.dds.core.Entity;
import org.omg.dds.core.InstanceHandle;
import org.omg.dds.core.ModifiableTime;
import org.omg.dds.core.QosProvider;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.Time;
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
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
	private Map<InstanceHandle, Topic<?>> discoveredTopics;
	private Map<InstanceHandle, Entity<?, ?>> discoveredParticipants;
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
	protected void addInitialStatuses() {
		// No status for this type
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
		doClosedCheck("Cannot create publisher, domain participant is closed");
		// TODO: Publisher
		//  - And record with entity handle (discoveredParticipants)
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
		doClosedCheck("Cannot create subscriber, domain participant is closed");
		Subscriber subscriber = new SubscriberImpl(getEnvironment(), this, qos, listener, statuses);
		subscribers.add(subscriber);
		discoveredParticipants.put(subscriber.getInstanceHandle(), subscriber);
		if (qos.getEntityFactory().isAutoEnableCreatedEntities()) {
			subscriber.enable();
		}
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
		return createTopic(topicName, TypeSupport.newTypeSupport(type, getEnvironment()), qos, listener, statuses);
	}

	@Override
	public <T> Topic<T> createTopic(String topicName, TypeSupport<T> type) {
		return createTopic(topicName, type, getDefaultTopicQos(), null, null);
	}

	@Override
	public <T> Topic<T> createTopic(String topicName, TypeSupport<T> type, TopicQos qos, TopicListener<T> listener,
									Collection<Class<? extends Status>> statuses) {
		doClosedCheck("Cannot create topic, domain participant is closed");
		Topic<T> topic = new TopicImpl<>(getEnvironment(), this, topicName, type, qos, listener, statuses);
		if (qos.getPolicyFactory().EntityFactory().isAutoEnableCreatedEntities()) {
			topic.enable();
		}
		discoveredTopics.put(topic.getInstanceHandle(), topic);
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
		return createTopic(topicName, type, null, qos, listener, statuses);
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
		return createMultiTopic(name, TypeSupport.newTypeSupport(type, getEnvironment()),
				subscriptionExpression, expressionParameters);
	}

	@Override
	public <T> MultiTopic<T> createMultiTopic(String name, TypeSupport<T> type, String subscriptionExpression,
											  List<String> expressionParameters) {
		// TODO Create topic: multi
		//  - And record with entity handle
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> Topic<T> findTopic(String topicName, long timeout, TimeUnit unit) throws TimeoutException {
		return findTopic(topicName, getEnvironment().getSPI().newDuration(timeout, unit));
	}

	@Override
	public <T> Topic<T> findTopic(String topicName, Duration timeout) throws TimeoutException {
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
		return Collections.unmodifiableSet(discoveredParticipants.keySet());
	}

	@Override
	public ParticipantBuiltinTopicData getDiscoveredParticipantData(InstanceHandle participantHandle) {
		throw new UnsupportedOperationException();
		// if (discoveredParticipants.containsKey(participantHandle)) {
		//  TODO: Store this info somewhere after reading it in so we can fetch it here
		// 	int[] keyValue = new int[4];
		// 	int[] dataValue = new int[4];
		// 	return new ParticipantBuiltinTopicDataImpl(getEnvironment(),
		// 			new BuiltInTopicKeyImpl(getEnvironment(), keyValue),
		// 			new UserDataImpl(getEnvironment(), dataValue));
		// }
		// // DOCS: Return null? Throw exception?
		// return null;
	}

	@Override
	public Set<InstanceHandle> getDiscoveredTopics() {
		return Collections.unmodifiableSet(discoveredTopics.keySet());
	}

	@Override
	public TopicBuiltinTopicData getDiscoveredTopicData(InstanceHandle topicHandle) {
		// TODO: Record discovered topics
		//  - Store this info somewhere after reading it in so we can fetch it here
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean containsEntity(InstanceHandle handle) {
		return discoveredParticipants.containsKey(handle) || discoveredTopics.containsKey(handle);
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

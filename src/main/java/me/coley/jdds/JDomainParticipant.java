package me.coley.jdds;

import me.coley.jdds.datatype.JModifiableTime;
import me.coley.jdds.datatype.JTime;
import org.omg.dds.core.Duration;
import org.omg.dds.core.InstanceHandle;
import org.omg.dds.core.ModifiableTime;
import org.omg.dds.core.StatusCondition;
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
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Domain participant implementation.
 *
 * @author Matt Coley
 */
public class JDomainParticipant implements DomainParticipant {
	private final JServiceEnvironment environment;
	private final int domainId;
	private DomainParticipantQos qos;
	private DomainParticipantListener listener;

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
	public JDomainParticipant(JServiceEnvironment environment, int domainId, DomainParticipantQos qos,
							  DomainParticipantListener listener, Collection<Class<? extends Status>> statuses) {
		this.environment = environment;
		this.domainId = domainId;
		this.qos = qos;
		setListener(listener, statuses);
	}

	@Override
	public void enable() {

	}

	@Override
	public void close() {

	}

	@Override
	public Publisher createPublisher() {
		return null;
	}

	@Override
	public Publisher createPublisher(PublisherQos qos) {
		return null;
	}

	@Override
	public Publisher createPublisher(PublisherQos qos, PublisherListener listener,
									 Collection<Class<? extends Status>> statuses) {
		return null;
	}

	@Override
	public Subscriber createSubscriber() {
		return null;
	}

	@Override
	public Subscriber createSubscriber(SubscriberQos qos) {
		return null;
	}

	@Override
	public Subscriber createSubscriber(SubscriberQos qos, SubscriberListener listener,
									   Collection<Class<? extends Status>> statuses) {
		return null;
	}

	@Override
	public Subscriber getBuiltinSubscriber() {
		return null;
	}

	@Override
	public <T> Topic<T> createTopic(String topicName, Class<T> type) {
		return null;
	}

	@Override
	public <T> Topic<T> createTopic(String topicName, Class<T> type, TopicQos qos, TopicListener<T> listener,
									Collection<Class<? extends Status>> statuses) {
		return null;
	}

	@Override
	public <T> Topic<T> createTopic(String topicName, TypeSupport<T> type) {
		return null;
	}

	@Override
	public <T> Topic<T> createTopic(String topicName, TypeSupport<T> type, TopicQos qos, TopicListener<T> listener,
									Collection<Class<? extends Status>> statuses) {
		return null;
	}

	@Override
	public Topic<DynamicType> createTopic(String topicName, DynamicType type) {
		return null;
	}

	@Override
	public Topic<DynamicType> createTopic(String topicName, DynamicType type, TopicQos qos,
										  TopicListener<DynamicType> listener,
										  Collection<Class<? extends Status>> statuses) {
		return null;
	}

	@Override
	public Topic<DynamicType> createTopic(String topicName, DynamicType type, TypeSupport<DynamicType> typeSupport) {
		return null;
	}

	@Override
	public Topic<DynamicType> createTopic(String topicName, DynamicType type, TypeSupport<DynamicType> typeSupport,
										  TopicQos qos, TopicListener<DynamicType> listener,
										  Collection<Class<? extends Status>> statuses) {
		return null;
	}

	@Override
	public <T> Topic<T> findTopic(String topicName, Duration timeout) throws TimeoutException {
		return null;
	}

	@Override
	public <T> Topic<T> findTopic(String topicName, long timeout, TimeUnit unit) throws TimeoutException {
		return null;
	}

	@Override
	public <T> TopicDescription<T> lookupTopicDescription(String name) {
		return null;
	}

	@Override
	public <T> ContentFilteredTopic<T> createContentFilteredTopic(String name, Topic<? extends T> relatedTopic,
																  String filterExpression,
																  List<String> expressionParameters) {
		return null;
	}

	@Override
	public <T> MultiTopic<T> createMultiTopic(String name, Class<T> type, String subscriptionExpression,
											  List<String> expressionParameters) {
		return null;
	}

	@Override
	public <T> MultiTopic<T> createMultiTopic(String name, TypeSupport<T> type, String subscriptionExpression,
											  List<String> expressionParameters) {
		return null;
	}

	@Override
	public void closeContainedEntities() {

	}

	@Override
	public void ignoreParticipant(InstanceHandle handle) {

	}

	@Override
	public void ignoreTopic(InstanceHandle handle) {

	}

	@Override
	public void ignorePublication(InstanceHandle handle) {

	}

	@Override
	public void ignoreSubscription(InstanceHandle handle) {

	}

	@Override
	public int getDomainId() {
		return domainId;
	}

	@Override
	public void assertLiveliness() {

	}

	@Override
	public PublisherQos getDefaultPublisherQos() {
		return null;
	}

	@Override
	public void setDefaultPublisherQos(PublisherQos qos) {

	}

	@Override
	public SubscriberQos getDefaultSubscriberQos() {
		return null;
	}

	@Override
	public void setDefaultSubscriberQos(SubscriberQos qos) {

	}

	@Override
	public TopicQos getDefaultTopicQos() {
		return null;
	}

	@Override
	public void setDefaultTopicQos(TopicQos qos) {

	}

	@Override
	public Set<InstanceHandle> getDiscoveredParticipants() {
		return null;
	}

	@Override
	public ParticipantBuiltinTopicData getDiscoveredParticipantData(InstanceHandle participantHandle) {
		return null;
	}

	@Override
	public Set<InstanceHandle> getDiscoveredTopics() {
		return null;
	}

	@Override
	public TopicBuiltinTopicData getDiscoveredTopicData(InstanceHandle topicHandle) {
		return null;
	}

	@Override
	public boolean containsEntity(InstanceHandle handle) {
		return false;
	}

	@Override
	public ModifiableTime getCurrentTime(ModifiableTime currentTime) {
		return new JModifiableTime(getEnvironment());
	}

	@Override
	public Time getCurrentTime() {
		return new JTime(getEnvironment());
	}

	@Override
	public DomainParticipantListener getListener() {
		return listener;
	}

	@Override
	public void setListener(DomainParticipantListener listener) {
		this.listener = listener;
	}

	@Override
	public void setListener(DomainParticipantListener listener, Collection<Class<? extends Status>> statuses) {

	}

	@Override
	public DomainParticipantQos getQos() {
		return null;
	}

	@Override
	public void setQos(DomainParticipantQos qos) {
		this.qos = qos;
	}

	@Override
	public void setQos(String qosLibraryName, String qosProfileName) {
		// TODO: QOS lookup
	}


	@Override
	public StatusCondition<DomainParticipant> getStatusCondition() {
		return null;
	}

	@Override
	public Set<Class<? extends Status>> getStatusChanges() {
		return null;
	}

	@Override
	public InstanceHandle getInstanceHandle() {
		return null;
	}

	@Override
	public void retain() {

	}

	@Override
	public JServiceEnvironment getEnvironment() {
		return environment;
	}
}

package me.coley.puredds.rtps.structure;

import me.coley.puredds.rtps.EntityId;
import me.coley.puredds.rtps.GUID;
import me.coley.puredds.rtps.Locator;
import me.coley.puredds.rtps.ReliabilityKind;
import me.coley.puredds.rtps.TopicKind;
import me.coley.puredds.rtps.cache.HistoryCache;
import org.omg.dds.topic.Topic;

import java.util.ArrayList;
import java.util.List;

/**
 * Base communication entity for sources and destinations of messages.
 *
 * @param <C>
 * 		Cache type.
 *
 * @author Matt Coley
 */
public abstract class Endpoint<C extends HistoryCache> extends Entity {
	private final List<Locator> unicastLocators = new ArrayList<>();
	private final List<Locator> multicastLocators = new ArrayList<>();
	private final TopicKind topicKind;
	private final ReliabilityKind reliability;
	private final EntityId endpointId;
	private final C cache;

	protected Endpoint(GUID guid, TopicKind topicKind, ReliabilityKind reliability, EntityId endpointId) {
		super(guid);
		this.topicKind = topicKind;
		this.reliability = reliability;
		this.endpointId = endpointId;
		cache = createCache();
	}

	/**
	 * @return New cache type for the type of endpoint.
	 */
	protected abstract C createCache();

	/**
	 * @return Endpoint cache.
	 */
	protected C getCache() {
		return cache;
	}

	/**
	 * @return List of unicast locators <i>(transport, address, port combinations)</i>
	 * that can be used to send messages to the {@link Endpoint}. The list may be empty.
	 */
	public List<Locator> getUnicastLocators() {
		return unicastLocators;
	}

	/**
	 * @return List of multicast locators (transport, address, port combinations)
	 * that can be used to send messages to the {@link Endpoint}. The list may be empty.
	 */
	public List<Locator> getMulticastLocators() {
		return multicastLocators;
	}

	/**
	 * @return The levels of reliability supported by the {@link Endpoint}.
	 */
	public ReliabilityKind getReliability() {
		return reliability;
	}

	/**
	 * Defined by the data-type that is associated with the DDS {@link Topic} related to the RTPS {@link Endpoint}.
	 * Indicates whether the {@link Endpoint} is associated with a data-type that has defined some fields as containing the DDS key.
	 *
	 * @return Used to indicate whether the {@link Endpoint} supports instance lifecycle management operations.
	 */
	public TopicKind getTopicKind() {
		return topicKind;
	}
}

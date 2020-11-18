package me.coley.puredds.rtps.structure;

import me.coley.puredds.rtps.EntityId;
import me.coley.puredds.rtps.GUID;
import me.coley.puredds.rtps.ReliabilityKind;
import me.coley.puredds.rtps.TopicKind;
import me.coley.puredds.rtps.cache.CacheChange;
import me.coley.puredds.rtps.cache.ReaderCache;

/**
 * An endpoint that represents the objects that can be used to receive new messages.
 * <br>
 * Reader endpoints receive {@link CacheChange} and change-availability announcements
 * from {@link Writer Writer endpoints} and potentially acknowledge the changes and/or request missed changes.
 *
 * @author Matt Coley
 */
public class Reader extends Endpoint<ReaderCache> {
	protected Reader(GUID guid, TopicKind topicKind, ReliabilityKind reliability, EntityId endpointId) {
		super(guid, topicKind, reliability, endpointId);
	}

	@Override
	protected ReaderCache createCache() {
		return new ReaderCache(getGuid());
	}
}

package me.coley.puredds.rtps.structure;

import me.coley.puredds.rtps.EntityId;
import me.coley.puredds.rtps.GUID;
import me.coley.puredds.rtps.Handle;
import me.coley.puredds.rtps.ReliabilityKind;
import me.coley.puredds.rtps.TopicKind;
import me.coley.puredds.rtps.cache.CacheChange;
import me.coley.puredds.rtps.cache.ChangeKind;
import me.coley.puredds.rtps.cache.Data;
import me.coley.puredds.rtps.cache.WriterCache;

/**
 * An endpoint that represents the objects that can be used to send new messages.
 * <br>
 * Writer endpoints send {@link CacheChange} messages to {@link Reader Reader endpoints}
 * and potentially receive acknowledgments for the changes they send.
 *
 * @author Matt Coley
 */
public class Writer extends Endpoint<WriterCache> {
	private long lastChangeSequenceNumber;

	protected Writer(GUID guid, TopicKind topicKind, ReliabilityKind reliability, EntityId endpointId) {
		super(guid, topicKind, reliability, endpointId);
	}

	@Override
	protected WriterCache createCache() {
		return new WriterCache(getGuid());
	}

	/**
	 * This operation creates a new {@link CacheChange} to be appended to the {@link Writer#getCache()}.
	 * The sequence number of the {@link CacheChange} is automatically incremented from
	 * the previous change plus one.
	 *
	 * @param kind
	 * 		Kind of change.
	 * @param data
	 * 		Value associated with the change. May be {@code} depending on the kind.
	 * @param handle
	 * 		Value associated with the change. May be {@code} depending on the kind.
	 * @param inlineQos
	 * 		TODO: Describe and change type
	 *
	 * @return Newly created {@link CacheChange}.
	 */
	public CacheChange newChange(ChangeKind kind, Data data, Handle handle, Object inlineQos) {
		// TODO: Add inlineQos (ParameterList) parameter once implemented
		// TODO: Ensure lastChangeSequenceNumber is unique if this is called on multiple threads... will that happen?
		return new CacheChange(kind, getGuid(), handle, ++lastChangeSequenceNumber, data);
	}
}

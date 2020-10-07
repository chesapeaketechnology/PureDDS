package me.coley.puredds.rtps.cache;

import me.coley.puredds.rtps.GUID;
import me.coley.puredds.rtps.structure.Endpoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Container to store temporary sets of changes to data-objects.
 *
 * @author Matt Coley
 */
// TODO: Do these methods need to be synchronized?
public class HistoryCache {
	private final List<CacheChange> changes = new ArrayList<>();
	private final Map<GUID, CacheChange> changeMap = new HashMap<>();
	protected final GUID guid;

	/**
	 *
	 * @param guid Parent {@link Endpoint#getGuid()}.
	 */
	protected HistoryCache(GUID guid) {
		this.guid = guid;
	}

	/**
	 * Adds a change.
	 *
	 * @param change
	 * 		Change to add.
	 */
	public void addChange(CacheChange change) {
		// TODO: Check against RESOURCE_LIMITS QoS
		changes.add(change);
		changeMap.put(change.getWriterGuid(), change);
	}

	/**
	 * Removes a given change.
	 *
	 * @param change
	 * 		Change to remove.
	 *
	 * @return {@code true} on success. {@code false} if it didn't belong to the cache.
	 */
	public boolean removeChange(CacheChange change) {
		return changes.remove(change);
	}

	/**
	 * Get a cache change by its {@link CacheChange#getWriterGuid() writer GUID}.
	 *
	 * @param guid
	 * 		A writer GUID.
	 *
	 * @return The associated cache change.
	 * {@code null} when no association between the writer <i>(GUID)</i> and any change is found.
	 */
	public CacheChange getChange(GUID guid) {
		// DOCS: RTPS spec does not explain implementation, so I'm just guessing how it should be done
		return changeMap.get(guid);
	}

	/**
	 * @return Smallest value of the {@link CacheChange#getSequenceNumber() sequence number}.
	 */
	public long getSequenceNumMin() {
		return changes.stream().mapToLong(CacheChange::getSequenceNumber).min().getAsLong();
	}

	/**
	 * @return Largest value of the {@link CacheChange#getSequenceNumber() sequence number}.
	 */
	public long getSequenceNumMax() {
		return changes.stream().mapToLong(CacheChange::getSequenceNumber).max().getAsLong();
	}
}

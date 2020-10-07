package me.coley.puredds.rtps;

import me.coley.puredds.rtps.structure.Entity;
import me.coley.puredds.rtps.structure.Participant;

/**
 * Global unique identifier.
 * <br>
 * Represented in 16 octets.
 *
 * @author Matt Coley
 */
public class GUID {
	private final GUIDPrefix prefix;
	private final EntityId entityId;

	/**
	 * @param prefix
	 * 		Uniquely identifies the {@link Participant} within the Domain.
	 * @param entityId
	 * 		Uniquely identifies the {@link Entity} within the {@link Participant}.
	 */
	public GUID(GUIDPrefix prefix, EntityId entityId) {
		this.prefix = prefix;
		this.entityId = entityId;
	}

	/**
	 * @return Uniquely identifies the {@link Participant} within the Domain.
	 */
	public GUIDPrefix getPrefix() {
		return prefix;
	}

	/**
	 * @return Uniquely identifies the {@link Entity} within the {@link Participant}.
	 */
	public EntityId getEntityId() {
		return entityId;
	}
}

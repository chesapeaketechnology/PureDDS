package me.coley.puredds.rtps.structure;

import me.coley.puredds.rtps.GUID;

/**
 * Base class for all RTPS entities.
 *
 * @author Matt Coley
 */
public class Entity {
	private final GUID guid;

	/**
	 * @param guid
	 * 		Unique identifier of the entity.
	 */
	protected Entity(GUID guid) {
		this.guid = guid;
	}

	/**
	 * Maps to the value of the DDS BuiltinTopicKey_t used to describe the corresponding DDS Entity.
	 *
	 * @return Globally and uniquely identifies the RTPS {@link Entity} within the DDS domain.
	 */
	public GUID getGuid() {
		return guid;
	}
}

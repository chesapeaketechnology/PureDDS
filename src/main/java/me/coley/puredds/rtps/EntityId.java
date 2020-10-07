package me.coley.puredds.rtps;

import me.coley.puredds.rtps.structure.Entity;
import me.coley.puredds.rtps.structure.Participant;

/**
 * Suffix to {@link GUID}. Can uniquely identify all {@link Entity entities} within a {@link Participant}.
 * <br>
 * Represented in 4 octets
 *
 * @author Matt Coley
 */
public class EntityId {
	// TODO: Provide some constants for built-in cases
	private final int id;

	/**
	 * @param id
	 * 		Unique id within a {@link Participant}.
	 */
	public EntityId(int id) {
		this.id = id;
	}

	/**
	 * @return Unique id within a {@link Participant}.
	 */
	public int getId() {
		return id;
	}
}

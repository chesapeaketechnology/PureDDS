package me.coley.puredds.rtps.structure;

import me.coley.puredds.rtps.GUID;

import java.util.ArrayList;
import java.util.List;

public class Group extends Entity {
	private final List<Reader> readers = new ArrayList<>();
	private final List<Writer> writers = new ArrayList<>();

	/**
	 * @param guid
	 * 		Unique identifier of the group.
	 */
	public Group(GUID guid) {
		super(guid);
	}
}

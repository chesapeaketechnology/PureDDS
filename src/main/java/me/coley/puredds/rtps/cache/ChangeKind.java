package me.coley.puredds.rtps.cache;

/**
 * Enumeration used to distinguish the kind of change that was made to a data-object.
 * Includes changes to the data or the instance state of the data-object.
 *
 * @author Matt Coley
 */
public enum ChangeKind {
	ALIVE,
	ALIVE_FILTERED,
	NOT_ALIVE_DISPOSED,
	NOT_ALIVE_UNREGISTERED
}

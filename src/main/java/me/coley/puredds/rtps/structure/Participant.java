package me.coley.puredds.rtps.structure;

import me.coley.puredds.rtps.GUID;
import me.coley.puredds.rtps.GUIDPrefix;
import me.coley.puredds.rtps.Locator;
import me.coley.puredds.rtps.ProtocolVersion;
import me.coley.puredds.rtps.VendorId;

import java.util.ArrayList;
import java.util.List;

/**
 * Container for RTPS entities with common properties in a single address space.
 */
public class Participant extends Entity {
	private final ProtocolVersion version = ProtocolVersion.PROTOCOLVERSION;
	private final VendorId vendorId = null;
	private final List<Group> groups = new ArrayList<>();
	private final Locator defaultUnicastLocator = null;
	private final Locator defaultMulticastLocator = null;
	private final GUIDPrefix guidPrefix = null;

	/**
	 * @param guid
	 * 		Unique identifier of the participant.
	 */
	public Participant(GUID guid) {
		super(guid);
	}

	/**
	 * @return Identifies the version of the RTPS protocol that the {@link Participant} uses to communicate.
	 */
	public ProtocolVersion getVersion() {
		return version;
	}

	/**
	 * @return Identifies the vendor of the RTPS middleware that contains the {@link Participant}.
	 */
	public VendorId getVendorId() {
		return vendorId;
	}

	/**
	 * @return Unique participant identifier, used to prefix any generated {@link GUID} of child entities.
	 */
	public GUIDPrefix getGuidPrefix() {
		return guidPrefix;
	}
}

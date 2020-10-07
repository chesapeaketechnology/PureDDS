package me.coley.puredds.rtps;

public class ProtocolVersion {
	public static final ProtocolVersion PROTOCOLVERSION_1_0 = new ProtocolVersion(1.0);
	public static final ProtocolVersion PROTOCOLVERSION_1_1 = new ProtocolVersion(1.1);
	public static final ProtocolVersion PROTOCOLVERSION_2_0 = new ProtocolVersion(2.0);
	public static final ProtocolVersion PROTOCOLVERSION_2_1 = new ProtocolVersion(2.1);
	public static final ProtocolVersion PROTOCOLVERSION_2_2 = new ProtocolVersion(2.2);
	public static final ProtocolVersion PROTOCOLVERSION_2_4 = new ProtocolVersion(2.4);
	public static final ProtocolVersion PROTOCOLVERSION = PROTOCOLVERSION_2_4;

	private final double versionValue;

	public ProtocolVersion(double versionValue) {
		this.versionValue = versionValue;
	}
}

package me.coley.puredds.rtps;

/**
 * Vendors collected from:
 * <ul>
 * <li><a href="https://www.dds-foundation.org/dds-rtps-vendor-and-product-ids/">DDS-RTPS Vendor and Product IDs</a></li>
 * <li><a href="https://github.com/ros2/freertps/blob/7a0a0610c9204d486a0c3ef394cd076adcf56ec1/id.c">FreeRTPS/id.c</a></li>
 * </ul>
 *
 * @author Matt Coley
 */
public enum VendorId {
	UNKNOWN        (-1, -1, "UNKNOWN", "UNKNOWN"),
	CONNEXT          (1, 1, "Real-Time Innovations", "Connext"),
	OPENSPLICE       (1, 2, "ADLink", "OpenSplice"),
	OCI_OPENDDS      (1, 3, "Object Computing Inc", "OpenDDS"),
	MILSOFT          (1, 4, "MilSoft", "MilSoftDDS"),
	INTER_COM        (1, 5, "Kongsberg", "InterCOM"),
	COREDX           (1, 6, "TwinOaks Computing", "CoreDX"),
	LAKOTA           (1, 7, "Lakota Technical Systems", "Lakota DDS"),
	ICOUP            (1, 8, "ICOUP Consulting", "ICOUP DDS"),
	DIAMOND          (1, 9, "Electronics and Telecommunication Research Institute", "Diamond"),
	RTI_CONNEXT_MICRO(1, 10, "Real-Time Innovations", "Connext Micro"),
	VORTEX_CAFE      (1, 11, "ADLink", "Vortex Cafe"),
	VORTEX_GATEWAY   (1, 12, "ADLink", "Vortex Gateway"),
	VORTEX_LITE      (1, 13, "ADLink", "Vortex Lite"),
	QEO              (1, 14, "Technicolor", "Qeo"),
	FASTRTPS         (1, 15, "eProsima", "FastRTPS"),
	ECLIPSE_CYCLONE  (1, 16, "Eclipse", "Cyclone"),
	GURUM            (1, 17, "Gurum", "GurumDDS"),
	VORTEX_CLOUD     (1, 32, "PrismTech", "Vortex Cloud");

	private final int major;
	private final int minor;
	private final String company;
	private final String name;

	VendorId(int major, int minor, String company, String name) {
		this.major = major;
		this.minor = minor;
		this.company = company;
		this.name = name;
	}

	public int getMajor() {
		return major;
	}

	public int getMinor() {
		return minor;
	}

	public String getCompany() {
		return company;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "VendorId{" +
				"id=[" + major + "," + minor + "], name='" + name + "'}";
	}
}

package me.coley.puredds.rtps;

import java.util.Objects;

/**
 * Representation of the addressing information required for sending messages to endpoints.
 *
 * @author Matt Coley
 */
public class Locator {
	private final LocatorKind kind;
	private final String address;
	private final int port;

	public Locator(LocatorKind kind, String address, int port) {
		this.kind = kind;
		this.address = address;
		this.port = port;
	}

	public LocatorKind getKind() {
		return kind;
	}

	public String getAddress() {
		return address;
	}

	public int getPort() {
		return port;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Locator locator = (Locator) o;
		return port == locator.port &&
				kind == locator.kind &&
				Objects.equals(address, locator.address);
	}

	@Override
	public int hashCode() {
		return Objects.hash(kind, address, port);
	}

}

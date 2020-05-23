package me.coley.jdds.core.policy;

import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.policy.UserData;

import java.util.Arrays;

/**
 * UserData impl.
 *
 * @author Matt Coley
 * @see UserData
 */
public class UserDataImpl extends AbstractPolicy implements UserData {
	private final byte[] value;

	/**
	 * @param environment
	 * 		Environment context.
	 * @param value
	 * 		Additional data value.
	 */
	public UserDataImpl(ServiceEnvironment environment, byte[] value) {
		super(environment);
		this.value = value;
	}

	@Override
	public byte[] getValue() {
		return value;
	}

	@Override
	public UserData withValue(byte[] value, int offset, int length) {
		return new UserDataImpl(getEnvironment(), Arrays.copyOfRange(value, offset, length));
	}
}

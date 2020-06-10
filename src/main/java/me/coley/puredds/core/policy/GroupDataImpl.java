package me.coley.puredds.core.policy;

import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.policy.GroupData;

import java.util.Arrays;

/**
 * GroupData impl.
 *
 * @author Matt Coley
 * @see GroupData
 */
public class GroupDataImpl extends AbstractPolicy implements GroupData {
	private final byte[] value;

	/**
	 * @param environment
	 * 		Environment context.
	 * @param value
	 * 		Additional data value.	Default is empty/0-length.
	 */
	public GroupDataImpl(ServiceEnvironment environment, byte[] value) {
		super(environment);
		this.value = value;
	}

	@Override
	public byte[] getValue() {
		return value;
	}

	@Override
	public GroupData withValue(byte[] value, int offset, int length) {
		return new GroupDataImpl(getEnvironment(), Arrays.copyOfRange(value, offset, length));
	}
}

package me.coley.puredds.topic;

import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.topic.BuiltinTopicKey;

import java.util.Arrays;

/**
 * BuiltinTopicKey impl.
 *
 * @author Matt Coley
 */
public class BuiltInTopicKeyImpl implements BuiltinTopicKey {
	private final ServiceEnvironment environment;
	private int[] value;

	/**
	 * @param environment
	 * 		Environment context.
	 * @param value
	 * 		Key value.
	 */
	public BuiltInTopicKeyImpl(ServiceEnvironment environment, int[] value) {
		this.environment = environment;
		this.value = value;
	}

	/**
	 * Expected size: <code>int[4]</code><br>
	 * Only the first 3 values are relevant, the last is padding.
	 * <i>(<a href="https://community.rti.com/kb/accessing-guid-connext-dds-entities">source</a>)</i>
	 *
	 * @return Copy of key value.
	 */
	@Override
	public int[] getValue() {
		return Arrays.copyOf(value, 4);
	}

	@Override
	public void copyFrom(BuiltinTopicKey src) {
		this.value = src.getValue();
	}

	@Override
	public BuiltinTopicKey clone() {
		return new BuiltInTopicKeyImpl(getEnvironment(), getValue());
	}

	@Override
	public ServiceEnvironment getEnvironment() {
		return environment;
	}
}

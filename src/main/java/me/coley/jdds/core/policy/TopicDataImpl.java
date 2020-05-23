package me.coley.jdds.core.policy;

import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.policy.TopicData;

import java.util.Arrays;

/**
 * TopicData impl.
 *
 * @author Matt Coley
 * @see TopicData
 */
public class TopicDataImpl extends AbstractPolicy implements TopicData {
	private final byte[] value;

	/**
	 * @param environment
	 * 		Environment context.
	 * @param value
	 * 		Additional data value.
	 */
	public TopicDataImpl(ServiceEnvironment environment, byte[] value) {
		super(environment);
		this.value = value;
	}

	@Override
	public byte[] getValue() {
		return value;
	}

	@Override
	public TopicData withValue(byte[] value, int offset, int length) {
		return new TopicDataImpl(getEnvironment(), Arrays.copyOfRange(value, offset, length));
	}
}

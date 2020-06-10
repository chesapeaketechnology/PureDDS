package me.coley.puredds.core.policy;

import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.policy.TransportPriority;

/**
 * TransportPriority impl.
 *
 * @author Matt Coley
 * @see TransportPriority
 */
public class TransportPriorityImpl extends AbstractPolicy implements TransportPriority {
	private final int value;

	/**
	 * @param environment
	 * 		Environment context.
	 * @param value
	 * 		Priority value.
	 */
	public TransportPriorityImpl(ServiceEnvironment environment, int value) {
		super(environment);
		this.value = value;
	}

	@Override
	public int getValue() {
		return value;
	}

	@Override
	public TransportPriority withValue(int value) {
		return new TransportPriorityImpl(getEnvironment(), value);
	}
}

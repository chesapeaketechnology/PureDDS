package me.coley.jdds.core.policy;

import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.policy.OwnershipStrength;

/**
 * OwnershipStrength impl.
 *
 * @author Matt Coley
 * @see OwnershipStrength
 */
public class OwnershipStrengthImpl extends AbstractPolicy implements OwnershipStrength {
	private final int value;

	/**
	 * @param environment
	 * 		Environment context.
	 * @param value
	 * 		Strength value.
	 */
	public OwnershipStrengthImpl(ServiceEnvironment environment, int value) {
		super(environment);
		this.value = value;
	}

	@Override
	public int getValue() {
		return value;
	}

	@Override
	public OwnershipStrength withValue(int value) {
		return new OwnershipStrengthImpl(getEnvironment(), value);
	}
}

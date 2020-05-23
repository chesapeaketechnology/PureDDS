package me.coley.jdds.core.policy;

import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.policy.ResourceLimits;

/**
 * ResourceLimits impl.
 *
 * @author Matt Coley
 * @see ResourceLimits
 */
public class ResourceLimitsImpl extends AbstractPolicy implements ResourceLimits {
	private final int maxSamples;
	private final int maxInstances;
	private final int maxSamplesPerInstance;

	/**
	 * @param environment
	 * 		Environment context.
	 * @param maxSamples
	 * 		Maximum number of samples to keep across all instances.
	 * @param maxInstances
	 * 		Maximum number of instances.
	 * @param maxSamplesPerInstance
	 * 		Maxium number of samples to keep per single instance.
	 */
	public ResourceLimitsImpl(ServiceEnvironment environment, int maxSamples, int maxInstances,
							  int maxSamplesPerInstance) {
		super(environment);
		this.maxSamples = maxSamples;
		this.maxInstances = maxInstances;
		this.maxSamplesPerInstance = maxSamplesPerInstance;
	}

	@Override
	public int getMaxSamples() {
		return maxSamples;
	}

	@Override
	public int getMaxInstances() {
		return maxInstances;
	}

	@Override
	public int getMaxSamplesPerInstance() {
		return maxSamplesPerInstance;
	}

	@Override
	public ResourceLimits withMaxSamples(int maxSamples) {
		return new ResourceLimitsImpl(getEnvironment(), maxSamples, getMaxInstances(), getMaxSamplesPerInstance());
	}

	@Override
	public ResourceLimits withMaxInstances(int maxInstances) {
		return new ResourceLimitsImpl(getEnvironment(), getMaxSamples(), maxInstances, getMaxSamplesPerInstance());
	}

	@Override
	public ResourceLimits withMaxSamplesPerInstance(int maxSamplesPerInstance) {
		return new ResourceLimitsImpl(getEnvironment(), getMaxSamples(), getMaxInstances(), maxSamplesPerInstance);
	}
}

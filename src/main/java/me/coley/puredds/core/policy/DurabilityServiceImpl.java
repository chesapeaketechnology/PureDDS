package me.coley.puredds.core.policy;

import org.omg.dds.core.Duration;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.policy.DurabilityService;
import org.omg.dds.core.policy.History;
import org.omg.dds.core.policy.ResourceLimits;

import java.util.concurrent.TimeUnit;

/**
 * DurabilityService impl.
 *
 * @author Matt Coley
 * @see DurabilityService
 */
public class DurabilityServiceImpl extends AbstractPolicy implements DurabilityService {
	private final Duration serviceCleanupDelay;
	private final History.Kind historyKind;
	private final int historyDepth;
	private final int maxSamples;
	private final int maxInstances;
	private final int maxSamplesPerInstance;

	/**
	 * @param environment
	 * 		Environment context.
	 * @param serviceCleanupDelay
	 * 		Delay before service cleanup.
	 * @param historyKind
	 * 		Type of history to keep.
	 * @param historyDepth
	 * 		Depth of history.
	 * @param maxSamples
	 * 		Maximum number of samples to keep across all instances.
	 * @param maxInstances
	 * 		Maximum number of instances.
	 * @param maxSamplesPerInstance
	 * 		Maxium number of samples to keep per single instance.
	 */
	public DurabilityServiceImpl(ServiceEnvironment environment, Duration serviceCleanupDelay,
								 History.Kind historyKind, int historyDepth, int maxSamples, int maxInstances,
								 int maxSamplesPerInstance) {
		super(environment);
		this.serviceCleanupDelay = serviceCleanupDelay;
		this.historyKind = historyKind;
		this.historyDepth = historyDepth;
		this.maxSamples = maxSamples;
		this.maxInstances = maxInstances;
		this.maxSamplesPerInstance = maxSamplesPerInstance;
	}

	/**
	 * @param environment
	 * 		Environment context.
	 * @param serviceCleanupDelay
	 * 		Delay before service cleanup.
	 * @param history
	 * 		History policy.
	 * @param resourceLimits
	 * 		Resource limitation policy.
	 */
	public DurabilityServiceImpl(ServiceEnvironment environment, Duration serviceCleanupDelay, History history,
								 ResourceLimits resourceLimits) {
		this(environment, serviceCleanupDelay, history.getKind(), history.getDepth(),
				resourceLimits.getMaxSamples(), resourceLimits.getMaxInstances(),
				resourceLimits.getMaxSamplesPerInstance());
	}

	@Override
	public Duration getServiceCleanupDelay() {
		return serviceCleanupDelay;
	}

	@Override
	public History.Kind getHistoryKind() {
		return historyKind;
	}

	@Override
	public int getHistoryDepth() {
		return historyDepth;
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
	public DurabilityService withServiceCleanupDelay(Duration serviceCleanupDelay) {
		return new DurabilityServiceImpl(getEnvironment(), serviceCleanupDelay,
				getHistoryKind(), getHistoryDepth(), getMaxSamples(), getMaxInstances(), getMaxSamplesPerInstance());
	}

	@Override
	public DurabilityService withServiceCleanupDelay(long serviceCleanupDelay, TimeUnit unit) {
		return withServiceCleanupDelay(getEnvironment().getSPI().newDuration(serviceCleanupDelay, unit));
	}

	@Override
	public DurabilityService withHistoryKind(History.Kind historyKind) {
		return new DurabilityServiceImpl(getEnvironment(), getServiceCleanupDelay(),
				historyKind, getHistoryDepth(), getMaxSamples(), getMaxInstances(), getMaxSamplesPerInstance());
	}

	@Override
	public DurabilityService withHistoryDepth(int historyDepth) {
		return new DurabilityServiceImpl(getEnvironment(), getServiceCleanupDelay(),
				getHistoryKind(), historyDepth, getMaxSamples(), getMaxInstances(), getMaxSamplesPerInstance());
	}

	@Override
	public DurabilityService withMaxSamples(int maxSamples) {
		return new DurabilityServiceImpl(getEnvironment(), getServiceCleanupDelay(),
				getHistoryKind(), getHistoryDepth(), maxSamples, getMaxInstances(), getMaxSamplesPerInstance());
	}

	@Override
	public DurabilityService withMaxInstances(int maxInstances) {
		return new DurabilityServiceImpl(getEnvironment(), getServiceCleanupDelay(),
				getHistoryKind(), getHistoryDepth(), getMaxSamples(), maxSamples, getMaxSamplesPerInstance());
	}

	@Override
	public DurabilityService withMaxSamplesPerInstance(int maxSamplesPerInstance) {
		return new DurabilityServiceImpl(getEnvironment(), getServiceCleanupDelay(),
				getHistoryKind(), getHistoryDepth(), getMaxSamples(), getMaxInstances(), maxSamplesPerInstance);
	}
}

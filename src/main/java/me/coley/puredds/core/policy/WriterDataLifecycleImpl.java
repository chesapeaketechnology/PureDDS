package me.coley.puredds.core.policy;

import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.policy.WriterDataLifecycle;

/**
 * WriterDataLifecycle impl.
 *
 * @author Matt Coley
 * @see WriterDataLifecycle
 */
public class WriterDataLifecycleImpl extends AbstractPolicy implements WriterDataLifecycle {
	private final boolean autoDisposeUnregisteredInstances;

	/**
	 * @param environment
	 * 		Environment context.
	 * @param autoDisposeUnregisteredInstances
	 * 		Dispose condition value.
	 */
	public WriterDataLifecycleImpl(ServiceEnvironment environment, boolean autoDisposeUnregisteredInstances) {
		super(environment);
		this.autoDisposeUnregisteredInstances = autoDisposeUnregisteredInstances;
	}

	@Override
	public boolean isAutDisposeUnregisteredInstances() {
		return autoDisposeUnregisteredInstances;
	}

	@Override
	public WriterDataLifecycle withAutDisposeUnregisteredInstances(boolean autDisposeUnregisteredInstances) {
		return new WriterDataLifecycleImpl(getEnvironment(), autoDisposeUnregisteredInstances);
	}
}

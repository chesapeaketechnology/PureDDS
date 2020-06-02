package me.coley.jdds.core.status;

import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.status.SampleLostStatus;

/**
 * SampleLostStatus impl.
 *
 * @author Matt Coley
 * @see SampleLostStatus
 */
public class SampleLostStatusImpl extends SampleLostStatus {
	private final ServiceEnvironment environment;
	private final int total;
	private final int dt;

	/**
	 * @param status
	 * 		Status to copy.
	 */
	public SampleLostStatusImpl(SampleLostStatus status) {
		this(status.getEnvironment(), status.getTotalCount(), status.getTotalCountChange());
	}

	/**
	 * @param environment
	 * 		Environment context.
	 * @param total
	 * 		Cumulative number of all samples lost across all instances.
	 * @param dt
	 * 		Difference from last status read.
	 */
	public SampleLostStatusImpl(ServiceEnvironment environment, int total, int dt) {
		this.environment = environment;
		this.total = total;
		this.dt = dt;
	}

	@Override
	public int getTotalCount() {
		return total;
	}

	@Override
	public int getTotalCountChange() {
		return dt;
	}

	@Override
	public ServiceEnvironment getEnvironment() {
		return environment;
	}
}

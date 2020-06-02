package me.coley.jdds.core.status;

import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.status.LivelinessLostStatus;

/**
 * LivelinessLostStatus impl.
 *
 * @author Matt Coley
 * @see LivelinessLostStatus
 */
public class LivelinessLostStatusImpl extends LivelinessLostStatus {
	private final ServiceEnvironment environment;
	private final int total;
	private final int dt;

	/**
	 * @param status
	 * 		Status to copy.
	 */
	public LivelinessLostStatusImpl(LivelinessLostStatus status) {
		this(status.getEnvironment(), status.getTotalCount(), status.getTotalCountChange());
	}

	/**
	 * @param environment
	 * 		Environment context.
	 * @param total
	 * 		Cumulative number of {@link org.omg.dds.pub.DataWriter}s that have died due to
	 * 		the failure to signal its liveliness within its liveliness period
	 * @param dt
	 * 		Difference from last status read.
	 */
	public LivelinessLostStatusImpl(ServiceEnvironment environment, int total, int dt) {
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

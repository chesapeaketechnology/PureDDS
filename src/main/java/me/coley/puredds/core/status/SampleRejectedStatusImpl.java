package me.coley.puredds.core.status;

import org.omg.dds.core.InstanceHandle;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.status.SampleRejectedStatus;

/**
 * SampleRejectedStatus impl.
 *
 * @author Matt Coley
 * @see SampleRejectedStatus
 */
public class SampleRejectedStatusImpl extends SampleRejectedStatus {
	private final ServiceEnvironment environment;
	private final int total;
	private final int dt;
	private final Kind lastReason;
	private final InstanceHandle lastInstanceHandle;

	/**
	 * @param status
	 * 		Status to copy.
	 */
	public SampleRejectedStatusImpl(SampleRejectedStatus status) {
		this(status.getEnvironment(), status.getTotalCount(), status.getTotalCountChange(),
				status.getLastReason(), status.getLastInstanceHandle());
	}

	/**
	 * @param environment
	 * 		Environment context.
	 * @param total
	 * 		Cumulative number of rejected samples by the  {@link org.omg.dds.sub.DataReader}.
	 * @param dt
	 * 		Difference from last status read.
	 * @param lastReason
	 * 		Reason for rejecting the last sample.
	 * @param lastInstanceHandle
	 * 		Handle to the {@link org.omg.dds.sub.DataReader} that caused the status change.
	 */
	public SampleRejectedStatusImpl(ServiceEnvironment environment, int total, int dt, Kind lastReason,
									InstanceHandle lastInstanceHandle) {
		this.environment = environment;
		this.total = total;
		this.dt = dt;
		this.lastReason = lastReason;
		this.lastInstanceHandle = lastInstanceHandle;
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
	public Kind getLastReason() {
		return lastReason;
	}

	@Override
	public InstanceHandle getLastInstanceHandle() {
		return lastInstanceHandle;
	}

	@Override
	public ServiceEnvironment getEnvironment() {
		return environment;
	}
}

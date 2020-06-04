package me.coley.jdds.core.status;

import org.omg.dds.core.InstanceHandle;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.status.OfferedDeadlineMissedStatus;

/**
 * OfferedDeadlineMissedStatus impl.
 *
 * @author Matt Coley
 * @see OfferedDeadlineMissedStatus
 */
public class OfferedDeadlineMissedStatusImpl extends OfferedDeadlineMissedStatus {
	private final ServiceEnvironment environment;
	private final int total;
	private final int dt;
	private final InstanceHandle lastInstanceHandle;

	/**
	 * @param status
	 * 		Status to copy.
	 */
	public OfferedDeadlineMissedStatusImpl(OfferedDeadlineMissedStatus status) {
		this(status.getEnvironment(), status.getTotalCount(), status.getTotalCountChange(),
				status.getLastInstanceHandle());
	}

	/**
	 * @param environment
	 * 		Environment context.
	 * @param total
	 * 		Cumulative number of missed deadlines where the {@link org.omg.dds.pub.DataWriter}
	 * 		failed to provide data.
	 * @param dt
	 * 		Difference from last status read.
	 * @param lastInstanceHandle
	 * 		Handle to the {@link org.omg.dds.pub.DataWriter} that missed the deadline.
	 */
	public OfferedDeadlineMissedStatusImpl(ServiceEnvironment environment, int total, int dt,
										   InstanceHandle lastInstanceHandle) {
		this.environment = environment;
		this.total = total;
		this.dt = dt;
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
	public InstanceHandle getLastInstanceHandle() {
		return lastInstanceHandle;
	}

	@Override
	public ServiceEnvironment getEnvironment() {
		return environment;
	}
}

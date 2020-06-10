package me.coley.puredds.core.status;

import org.omg.dds.core.InstanceHandle;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.status.RequestedDeadlineMissedStatus;

/**
 * RequestedDeadlineMissedStatus impl.
 *
 * @author Matt Coley
 * @see RequestedDeadlineMissedStatus
 */
public class RequestedDeadlineMissedStatusImpl extends RequestedDeadlineMissedStatus {
	private final ServiceEnvironment environment;
	private final int total;
	private final int dt;
	private final InstanceHandle lastInstanceHandle;

	/**
	 * @param status
	 * 		Status to copy.
	 */
	public RequestedDeadlineMissedStatusImpl(RequestedDeadlineMissedStatus status) {
		this(status.getEnvironment(), status.getTotalCount(), status.getTotalCountChange(),
				status.getLastInstanceHandle());
	}

	/**
	 * @param environment
	 * 		Environment context.
	 * @param total
	 * 		Cumulative number of missed deadlines detected for any
	 * 		instance read by the {@link org.omg.dds.sub.DataReader}.
	 * @param dt
	 * 		Difference from last status read.
	 * @param lastInstanceHandle
	 * 		Handle to the {@link org.omg.dds.sub.DataReader} that caused the status change.
	 */
	public RequestedDeadlineMissedStatusImpl(ServiceEnvironment environment, int total, int dt,
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

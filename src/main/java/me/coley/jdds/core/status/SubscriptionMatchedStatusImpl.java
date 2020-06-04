package me.coley.jdds.core.status;

import org.omg.dds.core.InstanceHandle;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.status.SubscriptionMatchedStatus;

/**
 * SubscriptionMatchedStatus impl.
 *
 * @author Matt Coley
 * @see SubscriptionMatchedStatus
 */
public class SubscriptionMatchedStatusImpl extends SubscriptionMatchedStatus {
	private final ServiceEnvironment environment;
	private final int total;
	private final int dt;
	private final int current;
	private final int dc;
	private final InstanceHandle lastPublicationHandle;

	/**
	 * @param status
	 * 		Status to copy.
	 */
	public SubscriptionMatchedStatusImpl(SubscriptionMatchedStatus status) {
		this(status.getEnvironment(), status.getTotalCount(), status.getTotalCountChange(),
				status.getCurrentCount(), status.getCurrentCountChange(), status.getLastPublicationHandle());
	}

	/**
	 * @param environment
	 * 		Environment context.
	 * @param total
	 * 		Cumulative number of times when a {@link org.omg.dds.sub.DataReader} discovered
	 * 		a {@link org.omg.dds.pub.DataWriter} that matched successfully.
	 * @param dt
	 * 		Difference in total from last status read.
	 * @param current
	 * 		Number of current matched {@link org.omg.dds.pub.DataWriter}s to {@link org.omg.dds.sub.DataReader}s.
	 * @param dc
	 * 		Difference in current from last status read.
	 * @param lastPublicationHandle
	 * 		Handle to the {@link org.omg.dds.pub.DataWriter} that caused the status change.
	 */
	public SubscriptionMatchedStatusImpl(ServiceEnvironment environment, int total, int dt, int current, int dc,
										 InstanceHandle lastPublicationHandle) {
		this.environment = environment;
		this.total = total;
		this.dt = dt;
		this.current = current;
		this.dc = dc;
		this.lastPublicationHandle = lastPublicationHandle;
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
	public int getCurrentCount() {
		return current;
	}

	@Override
	public int getCurrentCountChange() {
		return dc;
	}

	@Override
	public InstanceHandle getLastPublicationHandle() {
		return lastPublicationHandle;
	}

	@Override
	public ServiceEnvironment getEnvironment() {
		return environment;
	}
}

package me.coley.jdds.core.status;

import org.omg.dds.core.InstanceHandle;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.status.PublicationMatchedStatus;

/**
 * PublicationMatchedStatus impl.
 *
 * @author Matt Coley
 * @see PublicationMatchedStatus
 */
public class PublicationMatchedStatusImpl extends PublicationMatchedStatus {
	private final ServiceEnvironment environment;
	private final int total;
	private final int dt;
	private final int current;
	private final int dc;
	private final InstanceHandle lastSubscriptionHandle;

	/**
	 * @param status
	 * 		Status to copy.
	 */
	public PublicationMatchedStatusImpl(PublicationMatchedStatus status) {
		this(status.getEnvironment(), status.getTotalCount(), status.getTotalCountChange(),
				status.getCurrentCount(), status.getCurrentCountChange(), status.getLastSubscriptionHandle());
	}

	/**
	 * @param environment
	 * 		Environment context.
	 * @param total
	 * 		Cumulative number of times when a {@link org.omg.dds.pub.DataWriter} discovered
	 * 		a {@link org.omg.dds.sub.DataReader} that matched successfully.
	 * @param dt
	 * 		Difference in total from last status read.
	 * @param current
	 * 		Number of current matched {@link org.omg.dds.sub.DataReader}s to {@link org.omg.dds.pub.DataWriter}s.
	 * @param dc
	 * 		Difference in current from last status read.
	 * @param lastSubscriptionHandle
	 * 		Handle to the {@link org.omg.dds.pub.DataWriter} that caused the status change.
	 */
	public PublicationMatchedStatusImpl(ServiceEnvironment environment, int total, int dt, int current, int dc,
										InstanceHandle lastSubscriptionHandle) {
		this.environment = environment;
		this.total = total;
		this.dt = dt;
		this.current = current;
		this.dc = dc;
		this.lastSubscriptionHandle = lastSubscriptionHandle;
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
	public InstanceHandle getLastSubscriptionHandle() {
		return lastSubscriptionHandle;
	}

	@Override
	public ServiceEnvironment getEnvironment() {
		return environment;
	}
}

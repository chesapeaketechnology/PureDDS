package me.coley.jdds.core.status;

import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.status.InconsistentTopicStatus;

/**
 * InconsistentTopicStatus impl.
 *
 * @author Matt Coley
 * @see InconsistentTopicStatus
 */
public class InconsistentTopicStatusImpl extends InconsistentTopicStatus {
	private final ServiceEnvironment environment;
	private final int total;
	private final int dt;

	/**
	 * @param status
	 * 		Status to copy.
	 */
	public InconsistentTopicStatusImpl(InconsistentTopicStatus status) {
		this(status.getEnvironment(), status.getTotalCount(), status.getTotalCountChange());
	}

	/**
	 * @param environment
	 * 		Environment context.
	 * @param total
	 * 		Cumulative number of {@link org.omg.dds.topic.Topic}s discovered with names matching the topic this status is attached to, but with an unexpected type.
	 * @param dt
	 * 		Difference in inconsistent topics discovered from last status read.
	 */
	public InconsistentTopicStatusImpl(ServiceEnvironment environment, int total, int dt) {
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

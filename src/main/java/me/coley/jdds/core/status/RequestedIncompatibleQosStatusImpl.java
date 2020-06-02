package me.coley.jdds.core.status;

import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.policy.QosPolicy;
import org.omg.dds.core.policy.QosPolicyCount;
import org.omg.dds.core.status.RequestedIncompatibleQosStatus;

import java.util.Set;

/**
 * RequestedIncompatibleQosStatus impl.
 *
 * @author Matt Coley
 * @see RequestedIncompatibleQosStatus
 */
public class RequestedIncompatibleQosStatusImpl extends RequestedIncompatibleQosStatus {
	private final ServiceEnvironment environment;
	private final int total;
	private final int dt;
	private final Class<? extends QosPolicy> lastPolicyClass;
	private final Set<QosPolicyCount> policies;

	/**
	 * @param status
	 * 		Status to copy.
	 */
	public RequestedIncompatibleQosStatusImpl(RequestedIncompatibleQosStatus status) {
		this(status.getEnvironment(), status.getTotalCount(), status.getTotalCountChange(), status.getLastPolicyClass(), status.getPolicies());
	}

	/**
	 * @param environment
	 * 		Environment context.
	 * @param total
	 * 		Cumulative number of times when a {@link org.omg.dds.sub.DataReader} discovered a {@link org.omg.dds.pub.DataWriter} on the same topic that requested a QoS that is incompatible with the writer.
	 * @param dt
	 * 		Difference from last status read.
	 * @param lastPolicyClass
	 * 		Class of policy that was incompatible with a prior incompatibility detection.
	 * @param policies
	 * 		Set of policy counts for each incompatibility.
	 */
	public RequestedIncompatibleQosStatusImpl(ServiceEnvironment environment,
											  int total, int dt,
											  Class<? extends QosPolicy> lastPolicyClass,
											  Set<QosPolicyCount> policies) {
		this.environment = environment;
		this.total = total;
		this.dt = dt;
		this.lastPolicyClass = lastPolicyClass;
		this.policies = policies;
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
	public Class<? extends QosPolicy> getLastPolicyClass() {
		return lastPolicyClass;
	}

	@Override
	public Set<QosPolicyCount> getPolicies() {
		return policies;
	}

	@Override
	public ServiceEnvironment getEnvironment() {
		return environment;
	}
}

package me.coley.puredds.core.policy;

import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.policy.QosPolicy;
import org.omg.dds.core.policy.QosPolicyCount;

/**
 * QosPolicyCount impl.
 *
 * @author Matt Coley
 * @see QosPolicyCount
 */
public class QosPolicyCountImpl extends AbstractPolicy implements QosPolicyCount {
	private final Class<? extends QosPolicy> policyClass;
	private final int count;

	/**
	 * @param environment
	 * 		Environment context.
	 * @param policyClass
	 * 		Class of the polcy.
	 * @param count
	 * 		Number of instances.
	 */
	public QosPolicyCountImpl(ServiceEnvironment environment, Class<? extends QosPolicy> policyClass, int count) {
		super(environment);
		this.policyClass = policyClass;
		this.count = count;
	}

	@Override
	public Class<? extends QosPolicy> getPolicyClass() {
		return policyClass;
	}

	@Override
	public int getCount() {
		return count;
	}
}

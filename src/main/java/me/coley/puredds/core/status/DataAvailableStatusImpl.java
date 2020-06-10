package me.coley.puredds.core.status;

import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.status.DataAvailableStatus;

/**
 * DataAvailableStatus impl.
 *
 * @author Matt Coley
 * @see DataAvailableStatus
 */
public class DataAvailableStatusImpl extends DataAvailableStatus {
	private final ServiceEnvironment environment;

	/**
	 * @param status
	 * 		Status to copy.
	 */
	public DataAvailableStatusImpl(DataAvailableStatus status) {
		this(status.getEnvironment());
	}

	/**
	 * @param environment
	 * 		Environment context.
	 */
	public DataAvailableStatusImpl(ServiceEnvironment environment) {
		this.environment = environment;
	}


	@Override
	public ServiceEnvironment getEnvironment() {
		return environment;
	}
}

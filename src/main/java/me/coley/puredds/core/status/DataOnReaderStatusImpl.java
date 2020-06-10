package me.coley.puredds.core.status;

import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.status.DataOnReadersStatus;

/**
 * DataOnReadersStatus impl.
 *
 * @author Matt coley
 * @see DataOnReadersStatus
 */
public class DataOnReaderStatusImpl extends DataOnReadersStatus {
	private final ServiceEnvironment environment;

	/**
	 * @param status
	 * 		Status to copy.
	 */
	public DataOnReaderStatusImpl(DataOnReadersStatus status) {
		this(status.getEnvironment());
	}

	/**
	 * @param environment
	 * 		Environment context.
	 */
	public DataOnReaderStatusImpl(ServiceEnvironment environment) {
		this.environment = environment;
	}


	@Override
	public ServiceEnvironment getEnvironment() {
		return environment;
	}
}

package me.coley.jdds.core.event;

import me.coley.jdds.core.status.RequestedIncompatibleQosStatusImpl;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.event.RequestedIncompatibleQosEvent;
import org.omg.dds.core.status.RequestedIncompatibleQosStatus;
import org.omg.dds.sub.DataReader;

/**
 * RequestedIncompatibleQosEvent impl.
 *
 * @param <T>
 *        {@link DataReader} data type.
 *
 * @author Matt Coley
 * @see RequestedIncompatibleQosEvent
 */
public class RequestedIncompatibleQosEventImpl<T> extends RequestedIncompatibleQosEvent<T> {
	private final ServiceEnvironment environment;
	private final RequestedIncompatibleQosStatus status;

	/**
	 * @param environment
	 * 		Environment context.
	 * @param source
	 * 		Source value.
	 * @param status
	 * 		The status of the event.
	 */
	public RequestedIncompatibleQosEventImpl(ServiceEnvironment environment, DataReader<T> source,
											 RequestedIncompatibleQosStatus status) {
		super(source);
		this.environment = environment;
		this.status = status;
	}

	@Override
	public RequestedIncompatibleQosStatus getStatus() {
		return status;
	}

	@Override
	@SuppressWarnings("unchecked")
	public DataReader<T> getSource() {
		return (DataReader<T>) source;
	}

	@Override
	public RequestedIncompatibleQosEvent<T> clone() {
		return new RequestedIncompatibleQosEventImpl<>(getEnvironment(), getSource(),
				new RequestedIncompatibleQosStatusImpl(getStatus()));
	}

	@Override
	public ServiceEnvironment getEnvironment() {
		return environment;
	}
}

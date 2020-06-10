package me.coley.puredds.core.event;

import me.coley.puredds.core.status.RequestedDeadlineMissedStatusImpl;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.event.RequestedDeadlineMissedEvent;
import org.omg.dds.core.status.RequestedDeadlineMissedStatus;
import org.omg.dds.sub.DataReader;

/**
 * RequestedDeadlineMissedEvent
 *
 * @param <T>
 *        {@link DataReader} data type.
 *
 * @author Matt Coley
 * @see RequestedDeadlineMissedEvent
 */
public class RequestedDeadlineMissedEventImpl<T> extends RequestedDeadlineMissedEvent<T> {
	private final ServiceEnvironment environment;
	private final RequestedDeadlineMissedStatus status;

	/**
	 * @param environment
	 * 		Environment context.
	 * @param source
	 * 		Source value.
	 * @param status
	 * 		The status of the event.
	 */
	public RequestedDeadlineMissedEventImpl(ServiceEnvironment environment, DataReader<T> source,
											RequestedDeadlineMissedStatus status) {
		super(source);
		this.environment = environment;
		this.status = status;
	}

	@Override
	public RequestedDeadlineMissedStatus getStatus() {
		return status;
	}

	@Override
	@SuppressWarnings("unchecked")
	public DataReader<T> getSource() {
		return (DataReader<T>) source;
	}

	@Override
	public RequestedDeadlineMissedEvent<T> clone() {
		return new RequestedDeadlineMissedEventImpl<>(getEnvironment(), getSource(),
				new RequestedDeadlineMissedStatusImpl(getStatus()));
	}

	@Override
	public ServiceEnvironment getEnvironment() {
		return environment;
	}
}

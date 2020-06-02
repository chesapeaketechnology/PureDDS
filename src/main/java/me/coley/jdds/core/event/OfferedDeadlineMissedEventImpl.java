package me.coley.jdds.core.event;

import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.event.OfferedDeadlineMissedEvent;
import org.omg.dds.core.status.OfferedDeadlineMissedStatus;
import org.omg.dds.pub.DataWriter;

/**
 * OfferedDeadlineMissedEvent impl.
 *
 * @param <T>
 *        {@link DataWriter} data type.
 *
 * @author Matt Coley
 * @see OfferedDeadlineMissedEvent
 */
public class OfferedDeadlineMissedEventImpl<T> extends OfferedDeadlineMissedEvent<T> {
	private final ServiceEnvironment environment;
	private final OfferedDeadlineMissedStatus status;

	/**
	 * @param environment
	 * 		Environment context.
	 * @param source
	 * 		Source value.
	 * @param status
	 * 		The status of the event.
	 */
	public OfferedDeadlineMissedEventImpl(ServiceEnvironment environment, DataWriter<T> source, OfferedDeadlineMissedStatus status) {
		super(source);
		this.environment = environment;
		this.status = status;
	}

	@Override
	public OfferedDeadlineMissedStatus getStatus() {
		return status;
	}

	@Override
	@SuppressWarnings("unchecked")
	public DataWriter<T> getSource() {
		return (DataWriter<T>) source;
	}

	@Override
	public OfferedDeadlineMissedEvent<T> clone() {
		return null;
	}

	@Override
	public ServiceEnvironment getEnvironment() {
		return environment;
	}
}

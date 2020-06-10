package me.coley.puredds.core.event;

import me.coley.puredds.core.status.SampleRejectedStatusImpl;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.event.SampleRejectedEvent;
import org.omg.dds.core.status.SampleRejectedStatus;
import org.omg.dds.sub.DataReader;

/**
 * SampleRejectedEvent impl.
 *
 * @param <T>
 *        {@link DataReader} data type.
 *
 * @author Matt Coley
 * @see SampleRejectedEvent
 */
public class SampleRejectedEventImpl<T> extends SampleRejectedEvent<T> {
	private final ServiceEnvironment environment;
	private final SampleRejectedStatus status;

	/**
	 * @param environment
	 * 		Environment context.
	 * @param source
	 * 		Source value.
	 * @param status
	 * 		The status of the event.
	 */
	public SampleRejectedEventImpl(ServiceEnvironment environment, DataReader<T> source, SampleRejectedStatus status) {
		super(source);
		this.environment = environment;
		this.status = status;
	}

	@Override
	public SampleRejectedStatus getStatus() {
		return status;
	}

	@Override
	@SuppressWarnings("unchecked")
	public DataReader<T> getSource() {
		return (DataReader<T>) source;
	}

	@Override
	public SampleRejectedEvent<T> clone() {
		return new SampleRejectedEventImpl<>(getEnvironment(), getSource(), new SampleRejectedStatusImpl(getStatus()));
	}

	@Override
	public ServiceEnvironment getEnvironment() {
		return environment;
	}
}

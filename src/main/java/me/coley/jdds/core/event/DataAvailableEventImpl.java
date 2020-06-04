package me.coley.jdds.core.event;

import me.coley.jdds.core.status.DataAvailableStatusImpl;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.event.DataAvailableEvent;
import org.omg.dds.core.status.DataAvailableStatus;
import org.omg.dds.sub.DataReader;

/**
 * DataAvailableEvent impl.
 *
 * @param <T> {@link DataReader} data type.
 *
 * @author Matt Coley
 * @see DataAvailableEvent
 */
public class DataAvailableEventImpl<T> extends DataAvailableEvent<T> {
	private final ServiceEnvironment environment;
	private final DataAvailableStatusImpl status;

	/**
	 * @param environment
	 * 		Environment context.
	 * @param source
	 * 		Source value.
	 * @param status
	 * 		The status of the event.
	 */
	public DataAvailableEventImpl(ServiceEnvironment environment, DataReader<T> source,
								  DataAvailableStatusImpl status) {
		super(source);
		this.environment = environment;
		this.status = status;
	}

	@Override
	public DataAvailableStatus getStatus() {
		return status;
	}

	@Override
	@SuppressWarnings("unchecked")
	public DataReader<T> getSource() {
		return (DataReader<T>) source;
	}

	@Override
	public DataAvailableEvent<T> clone() {
		return new DataAvailableEventImpl<>(getEnvironment(), getSource(), new DataAvailableStatusImpl(getStatus()));
	}

	@Override
	public ServiceEnvironment getEnvironment() {
		return environment;
	}
}

package me.coley.jdds.core.event;

import me.coley.jdds.core.status.LivelinessChangedStatusImpl;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.event.LivelinessChangedEvent;
import org.omg.dds.core.status.LivelinessChangedStatus;
import org.omg.dds.sub.DataReader;

/**
 * LivelinessChangedEvent impl.
 *
 * @param <T>
 *        {@link DataReader} data type.
 *
 * @author Matt Coley
 * @see LivelinessChangedEvent
 */
public class LivelinessChangedEventImpl<T> extends LivelinessChangedEvent<T> {
	private final ServiceEnvironment environment;
	private final LivelinessChangedStatus status;

	/**
	 * @param environment
	 * 		Environment context.
	 * @param source
	 * 		Source value.
	 * @param status
	 * 		The status of the event.
	 */
	public LivelinessChangedEventImpl(ServiceEnvironment environment, DataReader<T> source, LivelinessChangedStatus status) {
		super(source);
		this.environment = environment;
		this.status = status;
	}

	@Override
	public LivelinessChangedStatus getStatus() {
		return status;
	}

	@Override
	@SuppressWarnings("unchecked")
	public DataReader<T> getSource() {
		return (DataReader<T>) source;
	}

	@Override
	public LivelinessChangedEvent<T> clone() {
		return new LivelinessChangedEventImpl<>(getEnvironment(), getSource(), new LivelinessChangedStatusImpl(getStatus()));
	}

	@Override
	public ServiceEnvironment getEnvironment() {
		return environment;
	}
}

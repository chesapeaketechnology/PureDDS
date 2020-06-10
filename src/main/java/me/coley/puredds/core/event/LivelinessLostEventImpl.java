package me.coley.puredds.core.event;

import me.coley.puredds.core.status.LivelinessLostStatusImpl;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.event.LivelinessLostEvent;
import org.omg.dds.core.status.LivelinessLostStatus;
import org.omg.dds.pub.DataWriter;

/**
 * LivelinessLostEvent impl.
 *
 * @param <T>
 *        {@link DataWriter} data type.
 *
 * @author Matt Coley
 * @see LivelinessLostEvent
 */
public class LivelinessLostEventImpl<T> extends LivelinessLostEvent<T> {
	private final ServiceEnvironment environment;
	private final LivelinessLostStatus status;

	/**
	 * @param environment
	 * 		Environment context.
	 * @param source
	 * 		Source value.
	 * @param status
	 * 		The status of the event.
	 */
	public LivelinessLostEventImpl(ServiceEnvironment environment, DataWriter<T> source, LivelinessLostStatus status) {
		super(source);
		this.environment = environment;
		this.status = status;
	}

	@Override
	public LivelinessLostStatus getStatus() {
		return status;
	}

	@Override
	@SuppressWarnings("unchecked")
	public DataWriter<T> getSource() {
		return (DataWriter<T>) source;
	}

	@Override
	public LivelinessLostEvent<T> clone() {
		return new LivelinessLostEventImpl<>(getEnvironment(), getSource(), new LivelinessLostStatusImpl(getStatus()));
	}

	@Override
	public ServiceEnvironment getEnvironment() {
		return environment;
	}
}

package me.coley.jdds.core.event;

import me.coley.jdds.core.status.SampleLostStatusImpl;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.event.SampleLostEvent;
import org.omg.dds.core.status.SampleLostStatus;
import org.omg.dds.sub.DataReader;

/**
 * SampleLostEvent impl.
 *
 * @param <T>
 *        {@link DataReader} data type.
 *
 * @author Matt Coley
 * @see SampleLostEvent
 */
public class SampleLostEventImpl<T> extends SampleLostEvent<T> {
	private final ServiceEnvironment environment;
	private final SampleLostStatus status;

	/**
	 * @param environment
	 * 		Environment context.
	 * @param source
	 * 		Source value.
	 * @param status
	 * 		The status of the event.
	 */
	public SampleLostEventImpl(ServiceEnvironment environment, DataReader<T> source, SampleLostStatus status) {
		super(source);
		this.environment = environment;
		this.status = status;
	}

	@Override
	public SampleLostStatus getStatus() {
		return status;
	}

	@Override
	@SuppressWarnings("unchecked")
	public DataReader<T> getSource() {
		return (DataReader<T>) source;
	}

	@Override
	public SampleLostEvent<T> clone() {
		return new SampleLostEventImpl<>(getEnvironment(), getSource(), new SampleLostStatusImpl(getStatus()));
	}

	@Override
	public ServiceEnvironment getEnvironment() {
		return environment;
	}
}

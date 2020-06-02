package me.coley.jdds.core.event;

import me.coley.jdds.core.status.DataOnReaderStatusImpl;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.event.DataOnReadersEvent;
import org.omg.dds.core.status.DataOnReadersStatus;
import org.omg.dds.sub.Subscriber;

/**
 * DataOnReadersEvent impl.
 *
 * @author Matt Coley
 * @see DataOnReadersEvent
 */
public class DataOnReadersEventImpl extends DataOnReadersEvent {
	private final ServiceEnvironment environment;
	private final DataOnReadersStatus status;

	/**
	 * @param environment
	 * 		Environment context.
	 * @param source
	 * 		Source value.
	 * @param status
	 * 		The status of the event.
	 */
	public DataOnReadersEventImpl(ServiceEnvironment environment, Subscriber source, DataOnReadersStatus status) {
		super(source);
		this.environment = environment;
		this.status = status;
	}

	@Override
	public DataOnReadersStatus getStatus() {
		return status;
	}

	@Override
	public Subscriber getSource() {
		return (Subscriber) source;
	}

	@Override
	public DataOnReadersEvent clone() {
		return new DataOnReadersEventImpl(getEnvironment(), getSource(), new DataOnReaderStatusImpl(getStatus()));
	}

	@Override
	public ServiceEnvironment getEnvironment() {
		return environment;
	}
}

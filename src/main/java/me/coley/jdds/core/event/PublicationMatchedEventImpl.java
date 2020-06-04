package me.coley.jdds.core.event;

import me.coley.jdds.core.status.PublicationMatchedStatusImpl;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.event.PublicationMatchedEvent;
import org.omg.dds.core.status.PublicationMatchedStatus;
import org.omg.dds.pub.DataWriter;

/**
 * PublicationMatchedEvent impl.
 *
 * @param <T>
 *        {@link DataWriter} data type.
 *
 * @author Matt Coley
 * @see PublicationMatchedEvent
 */
public class PublicationMatchedEventImpl<T> extends PublicationMatchedEvent<T> {
	private final ServiceEnvironment environment;
	private final PublicationMatchedStatus status;

	/**
	 * @param environment
	 * 		Environment context.
	 * @param source
	 * 		Source value.
	 * @param status
	 * 		The status of the event.
	 */
	public PublicationMatchedEventImpl(ServiceEnvironment environment, DataWriter<T> source,
									   PublicationMatchedStatus status) {
		super(source);
		this.environment = environment;
		this.status = status;
	}

	@Override
	public PublicationMatchedStatus getStatus() {
		return status;
	}

	@Override
	@SuppressWarnings("unchecked")
	public DataWriter<T> getSource() {
		return (DataWriter<T>) source;
	}

	@Override
	public PublicationMatchedEvent<T> clone() {
		return new PublicationMatchedEventImpl<>(getEnvironment(), getSource(),
				new PublicationMatchedStatusImpl(getStatus()));
	}

	@Override
	public ServiceEnvironment getEnvironment() {
		return environment;
	}
}
